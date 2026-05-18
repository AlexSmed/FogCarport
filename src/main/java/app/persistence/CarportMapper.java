package app.persistence;

import app.entities.Carport;
import app.exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarportMapper {

    public List<Carport> getAllCarports(ConnectionPool connectionPool) {
        List<Carport> carports = new ArrayList<>();
        String sql = "SELECT * FROM carport";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int carport_id = rs.getInt("carport_id");
                int bredde = rs.getInt("carport_bredde");
                int laengde = rs.getInt("carport_laengde");
                double pris = rs.getDouble("pris");
                String status = rs.getString("status");
                int stykliste_id = rs.getInt("stykliste_id");
                int bruger_id = rs.getInt("bruger_id");

                Carport carport = new Carport(carport_id, bredde, laengde, pris, status, bruger_id, stykliste_id);
                carports.add(carport);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carports;
    }

    public static void createCarport(int carport_bredde, int carport_laengde, double pris, String status, int bruger_id, int stykliste_id, app.persistence.ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO carport (carport_bredde, carport_laengde, pris, status, bruger_id, stykliste_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, carport_bredde);
            ps.setInt(2, carport_laengde);
            ps.setDouble(3, pris);
            ps.setString(4, status);
            ps.setInt(5, bruger_id);
            ps.setInt(6, stykliste_id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af forspørglse");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke oprette forspørglse", e.getMessage());
        }
    }

    public static List<Carport> getAllOrdersByUserId(int brugerId, app.persistence.ConnectionPool connectionPool) {
        List<Carport> customerOrders = new ArrayList<>();
        String sql = "SELECT * FROM carport WHERE bruger_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, brugerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int carport_id = rs.getInt("carport_id");
                int bredde = rs.getInt("carport_bredde");
                int laengde = rs.getInt("carport_laengde");
                double pris = rs.getDouble("pris");
                String status = rs.getString("status");
                int stykliste_id = rs.getInt("stykliste_id");

                Carport carport = new Carport(carport_id, bredde, laengde, pris, status, stykliste_id);
                customerOrders.add(carport);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerOrders;
    }

    public static void updateStatus(int carportId, app.persistence.ConnectionPool connectionPool) {
        String sql = "UPDATE carport SET status = 'BETALT' WHERE carport_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, carportId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

