package dev.andersonfernandes.dao;

import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.dao.utils.ResultSetMapper;
import dev.andersonfernandes.models.Book;
import dev.andersonfernandes.models.Material;
import dev.andersonfernandes.models.User;
import dev.andersonfernandes.models.UserType;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserDao implements Dao<User> {
    @Override
    public Optional<User> get(Long id) {
        return DatabaseQueries.get(User.TABLE_NAME, id, getResultSetMapper());
    }

    @Override
    public Optional<Long> create(User user) {
        Connection connection = Database.getInstance().getConnection();
        String sql = "INSERT INTO %1$s (name, address, email, registration, subjects, type)" +
                "VALUES ('%2$s', '%3$s', '%4$s', '%5$s', '%6$s', '%7$s')";

        try (
                PreparedStatement statement = connection.prepareStatement(
                        String.format(
                                sql,
                                User.TABLE_NAME,
                                user.getName(),
                                user.getAddress(),
                                user.getEmail(),
                                user.getRegistration(),
                                String.join(",", user.getSubjects()),
                                user.getType()
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
    public List<User> findBy(Map<String, String> args, Map<String, String> ilikeArgs) {
        return DatabaseQueries.findBy(User.TABLE_NAME, args, ilikeArgs, getResultSetMapper());
    }

    private ResultSetMapper getResultSetMapper() {
        ResultSetMapper mapper = (ResultSet rs) -> {
            return Optional.of(
                    new User(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("email"),
                            UserType.valueOf(rs.getString("type")),
                            rs.getString("registration"),
                            rs.getString("subjects").split(",")
                    )
            );
        };

        return mapper;
    }
}
