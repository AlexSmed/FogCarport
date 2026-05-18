package app.persistence;
import app.entities.Stykliste;
import app.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StyklisteMapper {

    public List<Stykliste> getStyklist(ConnectionPool connectionPool) {
        List<Stykliste> styklists = new ArrayList<>();
        String sql = "SELECT * FROM stykliste WHERE bruger_id = ? VALUES (?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int stykliste_id = rs.getInt("stykliste_id");
                int bruger_id = rs.getInt("bruger_id");

                Stykliste stykliste = new Stykliste(stykliste_id, bruger_id, null);
                styklists.add(stykliste);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return styklists;
    }

    public static int createStykliste(int brugerId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO stykliste (bruger_id) VALUES (?)";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, brugerId);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new DatabaseException("Indsætning mislykkedes, ingen rækker påvirket");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new DatabaseException("Kunne ikke oprette stykliste");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Fejl ved oprettelse af stykliste", e.getMessage());
        }
    }

}