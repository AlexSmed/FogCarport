package app.persistence;

import app.entities.Carport;
import org.postgresql.jdbc2.optional.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarportMapper {
    public List<Carport> getAllCarports(ConnectionPool connectionPool) {
        List<Carport> carports = new ArrayList<>();
        String sql = "SELECT * FROM carports";

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

}
