package app.persistence;
import app.entities.Users;
import app.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserMapper {

    private static ConnectionPool connectionPool;

    public UserMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static Users login(String email, String password) throws DatabaseException {
        String sql = "SELECT * FROM brugere WHERE email = ? AND kodeord = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String fornavn = resultSet.getString("fornavn");
                    String efternavn = resultSet.getString("efternavn");
                    int id = resultSet.getInt("bruger_id");
                    int balance = resultSet.getInt("saldo");
                    boolean is_admin = resultSet.getBoolean("er_admin");
                    return new Users(fornavn,efternavn, email, password, id, balance, is_admin);
                } else {
                    return null;
                }

            }
        } catch (SQLException e) {
            throw new DatabaseException("login fejlede", e.getMessage());
        }
    }

    public static void createUser(String firstname,String lastname, String email, String password, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO brugere (fornavn, efternavn, email, kodeord) VALUES (?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, email);
            ps.setString(4, password);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Kunne ikke oprette bruger", e.getMessage());
        }
    }
}