package app.persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class AdminMapperTest {

    private static ConnectionPool connectionPool;
    private AdminMapper adminMapper;

    @BeforeAll
    static void beforeAll() {

        String USER = "postgres";
        String PASSWORD = "postgres";
        String URL = "jdbc:postgresql://localhost:5432/Fog?currentSchema=test";
        String DB = "Fog";

        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

        try (Connection connection = connectionPool.getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS test.carport CASCADE");
            stmt.execute("DROP TABLE IF EXISTS test.stykliste CASCADE");
            stmt.execute("DROP TABLE IF EXISTS test.brugere CASCADE");

            stmt.execute("""
                CREATE TABLE test.brugere
                (LIKE public.brugere INCLUDING ALL)
            """);

            stmt.execute("""
                CREATE TABLE test.stykliste
                (LIKE public.stykliste INCLUDING ALL)
            """);

            stmt.execute("""
                CREATE TABLE test.carport
                (LIKE public.carport INCLUDING ALL)
            """);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to setup test database");
        }
    }

    @BeforeEach
    void beforeEach() {

        adminMapper = new AdminMapper(connectionPool);

        try (Connection connection = connectionPool.getConnection();
             Statement stmt = connection.createStatement()) {

            // =========================
            // CLEAN TABLES
            // =========================
            stmt.execute("DELETE FROM test.carport");
            stmt.execute("DELETE FROM test.stykliste");
            stmt.execute("DELETE FROM test.brugere");

            // =========================
            // INSERT USERS
            // =========================

            // Customer
            stmt.execute("""
                INSERT INTO test.brugere
                (bruger_id, fornavn, efternavn,
                 email, kodeord, saldo,
                 adresse, er_admin)
                VALUES
                (1, 'Jon', 'Bobson',
                 'jon@test.dk', '1234',
                 500,
                 'ved grænsen',
                 false)
            """);

            // Admin
            stmt.execute("""
                INSERT INTO test.brugere
                (bruger_id, fornavn, efternavn,
                 email, kodeord, saldo,
                 adresse, er_admin)
                VALUES
                (2, 'Admin', 'Boss',
                 'admin@test.dk', '1234',
                 1000,
                 'kontoret',
                 true)
            """);

            // =========================
            // INSERT STYKLISTE
            // =========================
            stmt.execute("""
                INSERT INTO test.stykliste
                (stykliste_id, bruger_id)
                VALUES
                (1, 1)
            """);

            // =========================
            // INSERT CARPORTS
            // =========================

            // Ikke betalt
            stmt.execute("""
                INSERT INTO test.carport
                (carport_id, carport_bredde,
                 carport_laengde, pris,
                 status, bruger_id, stykliste_id)
                VALUES
                (1, 600, 780,
                 25000,
                 'IKKE_BETALT',
                 1,
                 1)
            """);

            // Betalt
            stmt.execute("""
                INSERT INTO test.carport
                (carport_id, carport_bredde,
                 carport_laengde, pris,
                 status, bruger_id, stykliste_id)
                VALUES
                (2, 500, 700,
                 20000,
                 'BETALT',
                 1,
                 2)
            """);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to insert test data");
        }
    }
    @Test
    void testConnection() throws SQLException
    {
        assertNotNull(connectionPool.getConnection());
    }

    @Test
    void getAllCarportsWithUnpaidStatus() {
       int expectedSize = 1;
       int actualSize = AdminMapper.getAllCarportsWithUnpaidStatus(connectionPool).size();
       assertEquals(expectedSize,actualSize);

    }

    @Test
    void getAllCustomers() {
        AdminMapper adminMapper1 = new AdminMapper(connectionPool);
        int expectedAmountOfCustomers = 1;
        int actualAmountOfCustomers = adminMapper1.getAllCustomers(connectionPool).size();
        assertEquals(expectedAmountOfCustomers,actualAmountOfCustomers);

    }

    @Test
    void updateCustomerBalance() {
        AdminMapper adminMapper1 = new AdminMapper(connectionPool);
        int expectedBalance = 400;
        double actualBalance = adminMapper1.updateCustomerBalance(400,1,connectionPool);
        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    void getCustomerBalance() {
        AdminMapper adminMapper1 = new AdminMapper(connectionPool);
        int expectedBalance = 500;
        double actualBalance = adminMapper1.getCustomerBalance(1,connectionPool);
        assertEquals(expectedBalance, actualBalance);
    }
}