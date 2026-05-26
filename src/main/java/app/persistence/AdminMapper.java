package app.persistence;

import app.entities.Carport;
import app.entities.Materiale;
import app.entities.Users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminMapper {
    ConnectionPool connectionPool;

    public AdminMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static List<Carport> getAllCarports(ConnectionPool connectionPool) {
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

    public static List<Carport> getAllCarportsWithUnpaidStatus(ConnectionPool connectionPool) {
        List<Carport> unpaidCarports = new ArrayList<>();
        String sql = "SELECT * FROM public.carport WHERE status IS DISTINCT FROM 'BETALT'";

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
                unpaidCarports.add(carport);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unpaidCarports;
    }

    public List<Users> getAllCustomers(ConnectionPool connectionPool) {
        List<Users> allCustomers = new ArrayList<>();
        String sql = "SELECT * FROM public.brugere WHERE er_admin IS DISTINCT FROM true";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int bruger_id = rs.getInt("bruger_id");
                String fornavn = rs.getString("fornavn");
                String efternavn = rs.getString("efternavn");
                String email = rs.getString("email");
                double saldo = rs.getDouble("saldo");

                Users customers = new Users(bruger_id, fornavn, efternavn, email, saldo);
                allCustomers.add(customers);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    public void updateCustomerBalance(double saldo, int brugerId, ConnectionPool connectionPool) {
        String sql = "UPDATE brugere SET saldo = ? WHERE bruger_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, saldo);
            ps.setInt(2, brugerId);

            int rs = ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getCustomerBalance(int brugerId, ConnectionPool connectionPool) {
        String sql = "SELECT saldo FROM brugere WHERE bruger_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, brugerId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("saldo");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

    public List seAlleMaterialer(ConnectionPool connectionPool) {
        List<Materiale> alleMaterialer = new ArrayList<>();
        String sql = "SELECT * FROM public.materialer";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String navn = rs.getString("navn");
                String beskrivelse = rs.getString("vare_beskrivelse");
                String hjaelpeTekst = rs.getString("hjaelpe_tekst");
                int laengde = rs.getInt("laengde");
                int bredde = rs.getInt("bredde");
                int hoejde = rs.getInt("hoejde");
                double kostpris = rs.getInt("kost_pris");
                double salgspris = rs.getInt("salgs_pris");

                Materiale materiale = new Materiale(navn, beskrivelse, hjaelpeTekst, laengde, bredde, hoejde, kostpris, salgspris);
                alleMaterialer.add(materiale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alleMaterialer;
    }

    public Materiale tilfoejNyMateriale(String navn, String beskrivelse, String hjaelpeTekst, int laengde, int bredde, int hoejde, double kostpris, double salgspris, ConnectionPool connectionPool) {
        Materiale materialeListe = new Materiale(navn, beskrivelse, hjaelpeTekst, laengde, bredde, hoejde, kostpris, salgspris);
        String sql = "INSERT INTO materialer(navn, vare_beskrivelse, hjaelpe_tekst, laengde, bredde, hoejde, kost_pris, salgs_pris) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, navn);
            ps.setString(2, beskrivelse);
            ps.setString(3, hjaelpeTekst);
            ps.setInt(4, laengde);
            ps.setInt(5, bredde);
            ps.setInt(6, hoejde);
            ps.setDouble(7, kostpris);
            ps.setDouble(8, salgspris);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Materiale(navn, beskrivelse, hjaelpeTekst, laengde, bredde, hoejde, kostpris, salgspris);
    }

}
