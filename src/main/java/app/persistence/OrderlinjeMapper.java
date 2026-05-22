package app.persistence;

import app.entities.Carport;
import app.entities.Materiale;
import app.entities.Orderlinje;
import app.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderlinjeMapper {

    public static List<Orderlinje> getAllOrdrelinjer(ConnectionPool connectionPool) {
        List<Orderlinje> orderlinjeList = new ArrayList<>();
        String sql = "SELECT * FROM ordrelinjer";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ordrelinjeId = rs.getInt("ordrelinje_id");
                int stykliste_id = rs.getInt("stykliste_id");
                int vare_nummer = rs.getInt("vare_nummer");
                int antal = rs.getInt("antal");


                Orderlinje orderlinje = new Orderlinje(ordrelinjeId, stykliste_id, vare_nummer, antal);
                orderlinjeList.add(orderlinje);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderlinjeList;
    }


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

    public static List<Orderlinje> getStykliste(int stykliste_id, ConnectionPool connectionPool) {

        List<Orderlinje> list = new ArrayList<>();

        String sql = """
        SELECT 
            o.ordrelinje_id,
            o.stykliste_id,
            o.vare_nummer,
            o.antal,

            m.navn,
            m.vare_beskrivelse,
            m.laengde
        FROM ordrelinjer o
        INNER JOIN materialer m ON o.vare_nummer = m.vare_nummer
        WHERE o.stykliste_id = ?
    """;

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, stykliste_id);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Materiale m = new Materiale(
                            rs.getInt("vare_nummer"),
                            rs.getString("navn"),
                            rs.getString("vare_beskrivelse"),
                            null,
                            rs.getInt("laengde"),
                            0, 0,
                            0, 0,
                            rs.getInt("antal")
                    );

                    Orderlinje ol = new Orderlinje(
                            rs.getInt("ordrelinje_id"),
                            rs.getInt("stykliste_id"),
                            rs.getInt("vare_nummer"),
                            rs.getInt("antal"),
                            m
                    );

                    list.add(ol);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
