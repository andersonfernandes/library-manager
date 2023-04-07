package dev.andersonfernandes.dao;

import dev.andersonfernandes.config.DatabaseQueries;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.dao.utils.ResultSetMapper;
import dev.andersonfernandes.models.User;
import dev.andersonfernandes.models.UserType;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {
    @Override
    public Optional<User> get(Long id) {
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
        return DatabaseQueries.get(User.TABLE_NAME, id, mapper);
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Optional<Long> create(User user) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }
}
