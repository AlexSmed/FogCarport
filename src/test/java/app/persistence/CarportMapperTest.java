package app.persistence;

import app.entities.Carport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarportMapperTest {
    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/Fog?currentSchema=test";
    private static final String DB = "Fog";
    private static ConnectionPool connectionPool
            = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private static CarportMapper carportMapper;

    @BeforeAll
    static void setupClass() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS test.carport CASCADE");
                stmt.execute("DROP TABLE IF EXISTS test.brugere CASCADE");
                stmt.execute("DROP TABLE IF EXISTS test.stykliste CASCADE");

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
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Could not setup test database");
        }
        carportMapper = new CarportMapper(connectionPool);
    }

    @BeforeEach
    void setUp() {

        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM test.carport");
                stmt.execute("DELETE FROM test.stykliste");
                stmt.execute("DELETE FROM test.brugere");

                // TEST BRUGER
                stmt.execute("""
                        INSERT INTO test.brugere
                        (bruger_id, fornavn, efternavn,
                         email, kodeord, saldo,adresse, er_admin)
                        VALUES
                        (1, 'Jon', 'Bobson',
                         'jon@test.dk', '1234', 500,'ved Grænsen', false)
                        """);



                // TEST CARPORT
                stmt.execute("""
                        INSERT INTO test.carport
                        (carport_id, tagtype,
                         carport_bredde, carport_laengde,
                         stykliste_id, bruger_id,
                         pris, status)
                        VALUES
                        (1, 'Fladt tag',
                         600, 780,
                         1, 1,
                         25000, 'ikkeBetalt')
                        """);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Could not insert test data");
        }
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(connectionPool.getConnection());
    }

    @Test
    void createCarport() throws Exception {

        try (Connection connection = connectionPool.getConnection();
             Statement stmt = connection.createStatement()) {

            // Antal før
            var rs = stmt.executeQuery("SELECT COUNT(*) FROM test.carport");
            rs.next();
            int before = rs.getInt(1);

            // Opret ekstra stykliste til den nye carport
            stmt.execute("""
                INSERT INTO test.stykliste (stykliste_id)
                VALUES (2)
                """);

            // Kald metoden der testes
            CarportMapper.createCarport(
                    500,
                    700,
                    20000.0,
                    "ikkeBetalt",
                    1,
                    2,
                    connectionPool
            );

            // Antal efter
            rs = stmt.executeQuery("SELECT COUNT(*) FROM test.carport");
            rs.next();
            int after = rs.getInt(1);

            assertEquals(before + 1, after);
        }
    }

    @Test
    void getAllOrdersByUserId() {

        // Arrange
        int brugerId = 1;

        // Act
        List<Carport> result = CarportMapper.getAllOrdersByUserId(brugerId, connectionPool);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        Carport carport = result.get(0);

        assertEquals(1, carport.getCarport_id());
        assertEquals(600, carport.getCarport_bredde());
        assertEquals(780, carport.getCarport_laengde());
        assertEquals(25000.0, carport.getPris());
        assertEquals("ikkeBetalt", carport.getStatus());
        assertEquals(1, carport.getStykliste_id());
    }

    @Test
    void updateStatus() {

        // Arrange
        int carportId = 1;

        // Act
        CarportMapper.updateStatus(carportId, connectionPool);

        // Assert
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT status FROM test.carport WHERE carport_id = ?")) {

            ps.setInt(1, carportId);
            ResultSet rs = ps.executeQuery();

            assertTrue(rs.next());
            assertEquals("BETALT", rs.getString("status"));
        } catch (SQLException e) {
            fail("Test fejlede pga SQL-fejl: " + e.getMessage());
        }
    }


}