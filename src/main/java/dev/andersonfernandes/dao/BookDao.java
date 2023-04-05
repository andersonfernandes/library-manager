package dev.andersonfernandes.dao;

import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.config.DatabaseQueries;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.dao.utils.ResultSetMapper;
import dev.andersonfernandes.library.models.Book;
import dev.andersonfernandes.library.models.BookType;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class BookDao implements Dao<Book> {
    private static BookDao instance;

    private BookDao() { }

    public static BookDao getInstance() {
        if (instance == null)
            instance = new BookDao();

        return instance;
    }

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
        return DatabaseQueries.get(id, mapper);
    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public void save(Book book) {

    }

    @Override
    public void delete(Book book) {

    }
}
