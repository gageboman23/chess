package dataaccess;

import model.UserData;
import java.sql.*;

public class SQLUserDAO implements UserDAOBase{

    public SQLUserDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        }
        catch (Exception e){
            throw new DataAccessException(e.getMessage());
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS `userData` (
              `username` varchar(255) UNIQUE NOT NULL,
              `password` varchar(255) NOT NULL,
              `email` varchar(255)
            )
            """
    };


    @Override
    public void insertUser(UserData userData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
                String update = "INSERT into userData (username, password, email) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(update);
                stmt.setString(1, userData.username());
                stmt.setString(2, userData.password());
                stmt.setString(3, userData.email());
                stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * from userData WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()){
                return null;
            }
            String password = rs.getString("password");
            String email = rs.getString("email");
            return new UserData(username, password, email);
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            String update = "TRUNCATE userData";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.executeUpdate();
        }
        catch (Exception e) {

        }
    }
}
