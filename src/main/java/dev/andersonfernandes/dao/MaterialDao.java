package dev.andersonfernandes.dao;

import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.dao.utils.ResultSetMapper;
import dev.andersonfernandes.models.*;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MaterialDao implements Dao<Material> {
    @Override
    public Optional<Material> get(Long id) {
        return DatabaseQueries.get(Material.TABLE_NAME, id, getResultSetMapper());
    }

    @Override
    public Optional<Long> create(Material material) {
        Connection connection = Database.getInstance().getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(
                        getInsertSQL(material),
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
    public List<Material> findBy(Map<String, String> args, Map<String, String> ilikeArgs) {
        return DatabaseQueries.findBy(Material.TABLE_NAME, args, ilikeArgs, getResultSetMapper());
    }

    private ResultSetMapper getResultSetMapper() {
        ResultSetMapper mapper = (ResultSet rs) -> {
            MaterialType type = MaterialType.valueOf(rs.getString("material_type"));
            Optional<Material> material;

            switch (type) {
                case BOOK -> {
                    material = Optional.of(
                            new Book(
                                    rs.getLong("id"),
                                    rs.getString("title"),
                                    rs.getString("publisher"),
                                    rs.getInt("year"),
                                    rs.getInt("quantity"),
                                    BookType.valueOf(rs.getString("book_type")),
                                    rs.getString("subject"),
                                    rs.getString("genre")
                            )
                    );
                }
                case MAGAZINE -> {
                    material = Optional.of(
                            new Magazine(
                                    rs.getLong("id"),
                                    rs.getString("title"),
                                    rs.getString("publisher"),
                                    rs.getInt("year"),
                                    rs.getInt("quantity"),
                                    rs.getString("isbn"),
                                    rs.getString("volume"),
                                    rs.getString("edition")
                            )
                    );
                }
                default -> material = Optional.empty();

            }

            return material;
        };

        return mapper;
    }

    private String getInsertSQL(Material material) {
        String sql = "";

        switch (material.getMaterialType()) {
            case BOOK -> {
                String baseSql = "INSERT INTO %1$s (title, publisher, year, quantity, book_type, subject, genre, material_type)" +
                        "VALUES ('%2$s', '%3$s', %4$s, %5$s, '%6$s', '%7$s', '%8$s', '%9$s')";
                sql = String.format(
                        baseSql,
                        Material.TABLE_NAME,
                        material.getTitle(),
                        material.getPublisher(),
                        material.getYear(),
                        material.getQuantity(),
                        ((Book) material).getBookType(),
                        ((Book) material).getSubject(),
                        ((Book) material).getGenre(),
                        material.getMaterialType()
                );
            }
            case MAGAZINE -> {
                String baseSql = "INSERT INTO %1$s (title, publisher, year, quantity, isbn, volume, edition, , material_type)" +
                        "VALUES ('%2$s', '%3$s', %4$s, %5$s, '%6$s', '%7$s', '%8$s', '%9$s')";
                sql = String.format(
                        baseSql,
                        Material.TABLE_NAME,
                        material.getTitle(),
                        material.getPublisher(),
                        material.getYear(),
                        material.getQuantity(),
                        ((Magazine) material).getIsbn(),
                        ((Magazine) material).getVolume(),
                        ((Magazine) material).getEdition(),
                        material.getMaterialType()
                );
            }
        }

        return sql;
    }
}
