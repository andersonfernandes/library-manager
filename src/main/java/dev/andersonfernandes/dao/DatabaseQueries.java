package dev.andersonfernandes.dao;

import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.dao.utils.FormattedQuery;
import dev.andersonfernandes.dao.utils.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DatabaseQueries {
    public static final FormattedQuery GET_QUERY = (String tableName, String[] args) -> {
        return String.format("SELECT * FROM %1$s WHERE id = %2$s", tableName, args[0]);
    };

    public static Optional get(String tableName, Long id, ResultSetMapper mapper) {
        Database database = Database.getInstance();
        try (
                Statement statement = database.getConnection().createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY
                );
                ResultSet resultSet = statement.executeQuery(GET_QUERY.build(tableName, new String[]{id.toString()}));
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

    public static List findBy(String tableName, Map<String, String> args, Map<String, String> ilikeArgs, ResultSetMapper mapper) {
        Database database = Database.getInstance();
        String sql = String.format(
                "SELECT * FROM %1$s WHERE %2$s %3$s",
                tableName,
                args.keySet().stream()
                        .map(key -> String.format("%1$s='%2$s'", key, args.get(key)))
                        .collect(Collectors.joining(" AND ")),
                ilikeArgs.keySet().stream()
                        .map(key -> String.format("%1$s ilike '%%%2$s%%'", key, ilikeArgs.get(key)))
                        .collect(Collectors.joining(" AND "))
        );
        try (
                Statement statement = database.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            List list = new ArrayList();
            while (resultSet.next()) {
                list.add(mapper.call(resultSet).get());
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.closeConnection();
        }

        return List.of();
    }
}
