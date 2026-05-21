package app.persistence;

import app.entities.Carport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
    static void setupClass()
    {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement())
            {
                stmt.execute("DROP TABLE IF EXISTS test.carports CASCADE");
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
                        CREATE TABLE test.carports
                        (LIKE public.carports INCLUDING ALL)
                        """);
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Could not setup test database");
        }
        carportMapper = new CarportMapper(connectionPool);
    }
    @BeforeEach
    void setUp()
    {

        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement())
            {
                stmt.execute("DELETE FROM test.carports");
                stmt.execute("DELETE FROM test.stykliste");
                stmt.execute("DELETE FROM test.brugere");

                // TEST BRUGER
                stmt.execute("""
                        INSERT INTO test.brugere
                        (bruger_id, fornavn, efternavn,
                         email, kodeord, saldo, er_admin)
                        VALUES
                        (1, 'Jon', 'Bobson',
                         'jon@test.dk', '1234', 500, false)
                        """);

                // TEST STYKLISTE
                stmt.execute("""
                        INSERT INTO test.stykliste
                        (stykliste_id)
                        VALUES (1)
                        """);

                // TEST CARPORT
                stmt.execute("""
                        INSERT INTO test.carports
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
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Could not insert test data");
        }
    }
    @Test
    void testConnection() throws SQLException
    {
        assertNotNull(connectionPool.getConnection());
    }

    @Test
    void getAllCarports() {
        List<Carport> carports = new ArrayList<>();

        carports = AdminMapper.getAllCarports(connectionPool);

        assertEquals(1, carports.size());

        assertEquals(600, carports.get(0).getCarport_bredde());
    }

    @Test
    void getAllCarportsWithUpaidStatus() {
        List<Carport> unPaidCarports = new ArrayList<>();
        unPaidCarports = AdminMapper.getAllCarportsWithUnpaidStatus(connectionPool);

        assertEquals(1, unPaidCarports.size());

        assertEquals("ikkeBetalt", unPaidCarports.get(0).getStatus());

    }
}