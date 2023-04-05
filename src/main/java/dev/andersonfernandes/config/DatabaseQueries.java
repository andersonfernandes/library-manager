package dev.andersonfernandes.config;

import dev.andersonfernandes.dao.utils.FormattedQuery;
import dev.andersonfernandes.dao.utils.ResultSetMapper;
import dev.andersonfernandes.library.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class DatabaseQueries {
    public static final FormattedQuery GET_QUERY = (String tableName, String[] args) -> {
        return String.format("SELECT * FROM %1$s WHERE id = %2$s", tableName, args[0]);
    };

    public static Optional get(Long id, ResultSetMapper mapper) {
        Database database = Database.getInstance();
        try (
                Statement statement = database.getConnection().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                );
                ResultSet resultSet = statement.executeQuery(GET_QUERY.build(Book.TABLE_NAME, new String[]{id.toString()}));
        ) {
            if (resultSet.first())
                return mapper.call(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeConnection();
        }

        return Optional.empty();
    }
}
