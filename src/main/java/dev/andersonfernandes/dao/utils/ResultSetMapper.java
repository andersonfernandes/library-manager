package dev.andersonfernandes.dao.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public interface ResultSetMapper {
    Optional call(ResultSet resultSet) throws SQLException;
}
