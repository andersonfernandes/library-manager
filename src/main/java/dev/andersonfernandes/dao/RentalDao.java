package dev.andersonfernandes.dao;

import com.google.gson.*;

import com.google.gson.internal.LinkedTreeMap;
import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.models.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class RentalDao implements Dao<Rental> {
    @Override
    public Optional<Rental> get(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Long> create(Rental rental) {
        Connection connection = Database.getInstance().getConnection();
        String sql = String.format(
                "INSERT INTO %1$s (user_id, return_at, status) VALUES (%2$s, '%3$s', '%4$s') RETURNING id",
                Rental.TABLE_NAME,
                rental.getUser().getId().toString(),
                rental.getReturnAt().toString(),
                rental.getStatus().toString()
        );

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.execute();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    Long createdId = rs.getLong(1);
                    rental.setId(createdId);
                    createRentalMaterials(connection, rental);
                    return Optional.of(createdId);
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Rental> findBy(Map<String, String> args, Map<String, String> ilikeArgs) {
        Database database = Database.getInstance();
        String rawSql = """
                SELECT rentals.*,
                       json_agg(row_to_json(users.*)) AS user,
                       json_agg(row_to_json(materials_sub.*)) AS materials
                FROM rentals
                         LEFT JOIN users on rentals.user_id = users.id
                         LEFT JOIN (SELECT materials.*, rental_materials.rental_id
                                    FROM materials
                                             INNER JOIN rental_materials ON materials.id = rental_materials.material_id
                                    ) materials_sub ON materials_sub.rental_id = rentals.id
                WHERE %1$s %2$s
                GROUP BY rentals.id
                """;
        String sql = String.format(
                rawSql,
                args.keySet().stream()
                        .map(key -> String.format("%1$s=%2$s", key, args.get(key)))
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
                Rental rental = new Rental();
                rental.setId(resultSet.getLong("id"));
                rental.setReturnAt(LocalDate.parse(resultSet.getString("return_at")));
                rental.setStatus(RentalStatus.valueOf(resultSet.getString("status")));

                Gson gson = new Gson();
                User user = buildUser(gson.fromJson(resultSet.getString("user"), ArrayList.class));
                List<Material> materials = buildMaterials(gson.fromJson(resultSet.getString("materials"), ArrayList.class));

                rental.setUser(user);
                rental.setMaterials(materials);

                list.add(rental);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();
    }

    private void createRentalMaterials(Connection connection, Rental rental) {
        String sql = String.format(
                "INSERT INTO %1$s (rental_id, material_id) VALUES (%2$s, ?)",
                Rental.MATERIALS_JOIN_TABLE_NAME,
                rental.getId()
        );

        try (
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            for (Material material : rental.getMaterials()) {
                statement.setLong(1, material.getId());
                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User buildUser(List<LinkedTreeMap> usersJson) {
        String subjects = usersJson.get(0).get("subjects") != null ? usersJson.get(0).get("subjects").toString() : "";
        return new User(
                Double.valueOf(usersJson.get(0).get("id").toString()).longValue(),
                usersJson.get(0).get("name").toString(),
                usersJson.get(0).get("address").toString(),
                usersJson.get(0).get("email").toString(),
                UserType.valueOf(usersJson.get(0).get("type").toString()),
                usersJson.get(0).get("registration").toString(),
                subjects.split(",")
        );
    }

    private List<Material> buildMaterials(List<LinkedTreeMap> materialsJson) {
        List<Material> materials = new ArrayList<Material>();

        materialsJson.forEach(materialJson -> {
            MaterialType type = MaterialType.valueOf(materialJson.get("material_type").toString());
            String subject = materialJson.get("subject") != null ? materialJson.get("subject").toString() : "";
            String genre = materialJson.get("genre") != null ? materialJson.get("genre").toString() : "";

            materials.add(
                    switch (type) {
                        case BOOK -> new Book(
                                Double.valueOf(materialJson.get("id").toString()).longValue(),
                                materialJson.get("title").toString(),
                                materialJson.get("publisher").toString(),
                                Double.valueOf(materialJson.get("year").toString()).intValue(),
                                Double.valueOf(materialJson.get("quantity").toString()).intValue(),
                                BookType.valueOf(materialJson.get("book_type").toString()),
                                subject,
                                genre
                        );
                        case MAGAZINE -> new Magazine(
                                Double.valueOf(materialJson.get("id").toString()).longValue(),
                                materialJson.get("title").toString(),
                                materialJson.get("publisher").toString(),
                                Double.valueOf(materialJson.get("year").toString()).intValue(),
                                Double.valueOf(materialJson.get("quantity").toString()).intValue(),
                                materialJson.get("isbn").toString(),
                                materialJson.get("volume").toString(),
                                materialJson.get("edition").toString()
                        );
                    }
            );
        });

        return materials;
    }
}
