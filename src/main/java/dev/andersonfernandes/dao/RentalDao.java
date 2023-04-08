package dev.andersonfernandes.dao;

import dev.andersonfernandes.config.Database;
import dev.andersonfernandes.dao.utils.Dao;
import dev.andersonfernandes.models.Material;
import dev.andersonfernandes.models.Rental;
import dev.andersonfernandes.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return null;
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
}
