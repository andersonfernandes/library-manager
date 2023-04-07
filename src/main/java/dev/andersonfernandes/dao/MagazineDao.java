package dev.andersonfernandes.dao;

import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.models.Book;
import dev.andersonfernandes.models.Magazine;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MagazineDao implements Dao<Magazine> {
    @Override
    public Optional<Magazine> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Magazine> getAll() {
        return null;
    }

    @Override
    public Optional<Long> create(Magazine magazine) {
        Connection connection = Database.getInstance().getConnection();
        String sql = "INSERT INTO %1$s (title, publisher, year, quantity, isbn, volume, edition)" +
                        "VALUES ('%2$s', '%3$s', %4$s, %5$s, '%6$s', '%7$s', '%8$s')";

        try (
                PreparedStatement statement = connection.prepareStatement(
                        String.format(
                                sql,
                                Magazine.TABLE_NAME,
                                magazine.getTitle(),
                                magazine.getPublisher(),
                                magazine.getYear(),
                                magazine.getQuantity(),
                                magazine.getIsbn(),
                                magazine.getVolume(),
                                magazine.getEdition()
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
    public void update(Magazine magazine) {

    }

    @Override
    public void delete(Magazine magazine) {

    }
}
