package app.persistence;

import app.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderlinjeMapper {

    public static void createOrderlinje(int stykliste_id, int vare_nummer, int antal, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO ordrelinjer (stykliste_id, vare_nummer, antal) VALUES (?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, stykliste_id);
            ps.setInt(2, vare_nummer);
            ps.setDouble(3, antal);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af forspørglse");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke oprette forspørglse", e.getMessage());
        }
    }
}
