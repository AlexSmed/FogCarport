package app.persistence;

import app.entities.Carport;
import app.entities.Stykliste;
import app.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static void createStykliste(int bruger_id, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO carport (bruger_id) VALUES (?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, bruger_id);


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af stykliste");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke oprette stykliste", e.getMessage());
        }
    }

}
