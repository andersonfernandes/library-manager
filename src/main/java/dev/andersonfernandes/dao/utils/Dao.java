package dev.andersonfernandes.dao.utils;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(Long id);
    List<T> getAll();
    Optional<Long> create(T t);
    void update(T t);
    void delete(T t);
}