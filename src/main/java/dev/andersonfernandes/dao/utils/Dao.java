package dev.andersonfernandes.dao.utils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(Long id);
    Optional<Long> create(T t);
    List<T> findBy(Map<String, String> args, Map<String, String> ilikeArgs);
}