package app.persistence;

import app.exception.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class OrderlinjeMapperTest {

    private static ConnectionPool connectionPool;

    @BeforeAll
    static void beforeAll() {

        String USER = "postgres";
        String PASSWORD = "postgres";
        String URL = "jdbc:postgresql://localhost:5432/Fog?currentSchema=test";
        String DB = "Fog";

        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

        try (Connection connection = connectionPool.getConnection();
             Statement stmt = connection.createStatement()) {

            // =========================
            // DROP TABLES
            // =========================
            stmt.execute("DROP TABLE IF EXISTS test.ordrelinjer CASCADE");
            stmt.execute("DROP TABLE IF EXISTS test.stykliste CASCADE");
            stmt.execute("DROP TABLE IF EXISTS test.materialer CASCADE");

            // =========================
            // CREATE TABLES
            // =========================

            stmt.execute("""
                CREATE TABLE test.materialer
                (LIKE public.materialer INCLUDING ALL)
            """);

            stmt.execute("""
                CREATE TABLE test.stykliste
                (LIKE public.stykliste INCLUDING ALL)
            """);

            stmt.execute("""
                CREATE TABLE test.ordrelinjer
                (LIKE public.ordrelinjer INCLUDING ALL)
            """);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to setup test database");
        }
    }

    @BeforeEach
    void beforeEach() {

        try (Connection connection = connectionPool.getConnection();
             Statement stmt = connection.createStatement()) {

            // =========================
            // CLEAN TABLES
            // =========================
            stmt.execute("DELETE FROM test.ordrelinjer");
            stmt.execute("DELETE FROM test.stykliste");
            stmt.execute("DELETE FROM test.materialer");

            // =========================
            // INSERT MATERIALER
            // =========================
            stmt.execute("""
                INSERT INTO test.materialer
                (vare_nummer, navn, vare_beskrivelse,
                 hjaelpe_tekst, hoejde,
                 laengde, bredde,
                 kost_pris, salgs_pris)
                VALUES
                (100,
                 'Bræt',
                 'Trykimprægneret træ',
                 'Til carport',
                 50,
                 2400,
                 100,
                 25,
                 50)
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
            // INSERT ORDRELINJE
            // =========================
            stmt.execute("""
                INSERT INTO test.ordrelinjer
                (ordrelinje_id, stykliste_id,
                 vare_nummer, antal)
                VALUES
                (1, 1, 100, 10)
            """);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to insert test data");
        }
    }

    @Test
    void createOrderlinje() throws DatabaseException {
        OrderlinjeMapper.createOrderlinje(12,12,12,connectionPool);
        int expectedSize = 2;
        int actualSize = OrderlinjeMapper.getAllOrdrelinjer(connectionPool).size();
        assertEquals(expectedSize, actualSize);


    }

    @Test
    void getStykliste() {
        int expectedAntal = 10;
        int actualAntal = OrderlinjeMapper.getStykliste
                (1,connectionPool).get(0).getAntal();
        assertEquals(expectedAntal,actualAntal);
    }

    @Test
    void getAllOrdrelinjer() {
        int expectedSize = 1;
        int actualSize = OrderlinjeMapper.getAllOrdrelinjer(connectionPool).size();
        assertEquals(expectedSize, actualSize);
    }
}