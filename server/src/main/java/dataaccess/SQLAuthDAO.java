package dataaccess;

import model.AuthData;
import java.sql.*;

public class SQLAuthDAO implements AuthDAOBase{

    public SQLAuthDAO() {
        try {
            System.out.print("SQLAuthDAO Call");
            configureDatabase();
        } catch (DataAccessException e) {
            System.out.print(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS `authData` (
              `authToken` varchar(255) PRIMARY KEY NOT NULL,
              `username` varchar(255) NOT NULL
            )
            """
    };

    SQLconfig NewConfig = new SQLconfig();

    private void configureDatabase() throws DataAccessException {
        NewConfig.config(createStatements);
    }


    @Override
    public void insertAuth(AuthData authData) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "INSERT into authData (authToken, username) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, authData.authToken());
            stmt.setString(2, authData.username());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * from authData WHERE authToken = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, authToken);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
            String username = rs.getString("username");
            return new AuthData(authToken, username);
        }
        catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        if (getAuth(authToken) == null){
            throw new DataAccessException("Error: unauthorized");
        }
        try (Connection conn = DatabaseManager.getConnection()) {
            String update = "DELETE FROM authData WHERE authToken = ?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, authToken);
            stmt.executeUpdate();
        }
        catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            String update = "TRUNCATE authData";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.executeUpdate();
        }
        catch (Exception e) {

        }
    }
}
