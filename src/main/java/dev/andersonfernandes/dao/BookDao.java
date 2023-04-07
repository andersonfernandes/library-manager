package dev.andersonfernandes.dao;

import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.config.DatabaseQueries;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.dao.utils.ResultSetMapper;
import dev.andersonfernandes.models.Book;
import dev.andersonfernandes.models.BookType;
import dev.andersonfernandes.models.Magazine;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BookDao implements Dao<Book> {
    @Override
    public Optional<Book> get(Long id) {
        ResultSetMapper mapper = (ResultSet rs) -> {
            return Optional.of(
                    new Book(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("publisher"),
                            rs.getInt("year"),
                            rs.getInt("quantity"),
                            BookType.valueOf(rs.getString("type")),
                            rs.getString("subject"),
                            rs.getString("genre")
                    )
            );
        };
        return DatabaseQueries.get(Book.TABLE_NAME, id, mapper);
    }

    @Override
    public Optional<Long> create(Book book) {
        Connection connection = Database.getInstance().getConnection();
        String sql = "INSERT INTO %1$s (title, publisher, year, quantity, type, subject, genre)" +
                        "VALUES ('%2$s', '%3$s', %4$s, %5$s, '%6$s', '%7$s', '%8$s')";

        try (
                PreparedStatement statement = connection.prepareStatement(
                        String.format(
                                sql,
                                Book.TABLE_NAME,
                                book.getTitle(),
                                book.getPublisher(),
                                book.getYear(),
                                book.getQuantity(),
                                book.getType(),
                                book.getSubject(),
                                book.getGenre()
                        ),
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.execute();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    Long createdId = rs.getLong(1);
                    return Optional.of(createdId);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findBy(Map<String, String> args) {
        ResultSetMapper mapper = (ResultSet rs) -> {
            return Optional.of(
                    new Book(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("publisher"),
                            rs.getInt("year"),
                            rs.getInt("quantity"),
                            BookType.valueOf(rs.getString("type")),
                            rs.getString("subject"),
                            rs.getString("genre")
                    )
            );
        };
        return DatabaseQueries.findBy(Book.TABLE_NAME, args, mapper);
    }
}
