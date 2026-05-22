package app.persistence;

import app.entities.Users;
import app.exception.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/Fog?currentSchema=test";
    private static final String DB = "Fog";
    private static ConnectionPool connectionPool
            = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
    private static UserMapper userMapper;

    @BeforeAll
    static void setupClass()
    {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement())
            {
                // The test schema is already created, so we only need to delete/create test tables
                stmt.execute("DROP TABLE IF EXISTS test.brugere");
                stmt.execute("DROP SEQUENCE IF EXISTS test.brugere_bruger_id_seq CASCADE;");
                // Create tables as copy of original public schema structure
                stmt.execute("CREATE TABLE test.brugere AS (SELECT * from public.brugere) WITH NO DATA");
                // Create sequences for auto generating id's for users and orders
                stmt.execute("CREATE SEQUENCE test.brugere_bruger_id_seq");
                stmt.execute("ALTER TABLE test.brugere ALTER COLUMN bruger_id SET DEFAULT nextval('test.brugere_bruger_id_seq')");

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Database connection failed");
        }
        userMapper = new UserMapper(connectionPool);
    }
    @BeforeEach
    void setUp()
    {
        try (Connection connection = connectionPool.getConnection())
        {
            try (Statement stmt = connection.createStatement())
            {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM test.brugere");

                stmt.execute("INSERT INTO test.brugere (bruger_id, fornavn, efternavn, email, kodeord, saldo, er_admin) " +
                        "VALUES  (1, 'jon','Bobson', 'Bobson@gmail.com',  'Kode123!!', 300, false), (2, 'benny','Bennyson', 'Bennyson@gmail.com', 'Kode123!!2', 0, true)");

                stmt.execute("SELECT setval('test.brugere_bruger_id_seq', COALESCE((SELECT MAX(bruger_id) + 1 FROM test.brugere), 1), false)");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            fail("Database connection failed");
        }
    }
    @Test
    void testConnection() throws SQLException {
        assertNotNull(connectionPool.getConnection());

    }
    @Test
    @DisplayName("Tester at man kan logge ind")
    void login() throws DatabaseException {

        Users user = userMapper.login("Bobson@gmail.com", "Kode123!!", connectionPool);
        assertNotNull(user);
        assertEquals("Bobson@gmail.com", user.getEmail());
    }

    @Test
    @DisplayName("Tester at bruger skal logg ind med korrekt information")
    void wrongLogin() throws DatabaseException {

        Users user = userMapper.login("Kane@gmail.com", "Kode123¤¤", connectionPool);
        assertNull(user);
    }

    @Test
    void createUser() throws DatabaseException {

        userMapper.createUser("Harry", "Kane",
                "Kane@gmail.com", "Kode123¤¤",
                "ved grænsen",connectionPool);

        Users user = userMapper.login("Kane@gmail.com", "Kode123¤¤", connectionPool);
        assertNotNull(user);
        assertEquals("Kane@gmail.com", user.getEmail());

    }
}