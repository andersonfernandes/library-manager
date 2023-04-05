package dev.andersonfernandes.dao.utils;

public interface FormattedQuery {
    String build(String tableName, String[] args);
}
