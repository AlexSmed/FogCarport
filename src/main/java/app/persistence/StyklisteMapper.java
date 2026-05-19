package app.persistence;
import app.entities.Materiale;
import app.entities.Stykliste;
import app.entities.Users;
import app.exception.DatabaseException;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StyklisteMapper {
    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/Fog?currentSchema=public";
    private static final String DB = "Fog";
    private static ConnectionPool connectionPool
            = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

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

    public static int getKost_pris(int vareNummer, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT kost_pris FROM materialer WHERE vare_nummer = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, vareNummer);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int kost_pris = resultSet.getInt("kost_pris");
                    return kost_pris;

                } else {
                    return 0;
                }

            }
        } catch (SQLException e) {
            throw new DatabaseException("login fejlede", e.getMessage());
        }
    }

    public static Materiale getMaterialeFromVareNummer(int vareNummer, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM materialer WHERE vare_nummer = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, vareNummer);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int vare_nummer = resultSet.getInt("vare_nummer");
                    String navn = resultSet.getString("navn");
                    String vareBeskrivelse = resultSet.getString("vare_beskrivelse");
                    String hjælpeTekst = resultSet.getString("hjaelpe_tekst");
                    int højde = resultSet.getInt("hoejde");
                    int længde = resultSet.getInt("laengde");
                    int bredde = resultSet.getInt("bredde");
                    int kost_pris = resultSet.getInt("kost_pris");
                    int salgs_pris = resultSet.getInt("salgs_pris");

                    Materiale materiale = new Materiale(vare_nummer, navn, vareBeskrivelse, hjælpeTekst, længde, bredde, højde, kost_pris, salgs_pris, 1);
                    return materiale;
                } else {
                    Materiale materiale2 = new Materiale();
                    return materiale2;
                }

            }
        } catch (SQLException e) {
            throw new DatabaseException("login fejlede", e.getMessage());
        }
    }

    public static int getSalgs_pris(int vareNummer, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT salgs_pris FROM Materialer WHERE vare_nummer = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setInt(1, vareNummer);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int salgs_pris = resultSet.getInt("salgs_pris");
                    return salgs_pris;

                } else {
                    return 0;
                }

            }
        } catch (SQLException e) {
            throw new DatabaseException("login fejlede", e.getMessage());
        }
    }

    public static void createStykliste(int stykliste_id, int brugerId, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO stykliste (stykliste_id, bruger_id) VALUES (?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, stykliste_id);
            ps.setInt(2, brugerId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny stykliste");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke oprette stykliste", e.getMessage());
        }
    }


    public static int getHighestStyklistId() throws DatabaseException {

        String sql = "SELECT Max(stykliste_id) AS stykliste_id FROM stykliste";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int højesteStyklisteId = resultSet.getInt("stykliste_id");
                    return højesteStyklisteId;
                } else {
                    return 1;
                }

            }
        } catch (SQLException e) {
            throw new DatabaseException("Det at få den højeste stykliste fejlede", e.getMessage());
        }
    }

}