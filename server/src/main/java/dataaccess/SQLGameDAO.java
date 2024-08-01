package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.sql.*;
import com.google.gson.Gson;

public class SQLGameDAO implements GameDAOBase{

    public SQLGameDAO() {
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
            CREATE TABLE IF NOT EXISTS `gameData` (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(255) DEFAULT NULL,
              `blackUsername` varchar(255) DEFAULT NULL,
              `gameName` varchar(255) NOT NULL,
              `game` JSON NOT NULL,
              PRIMARY KEY (`gameID`)
            )
            """
    };

    @Override
    public void insertGame(GameData gameData) throws DataAccessException {
        Gson serializer = new Gson();
        try (Connection conn = DatabaseManager.getConnection()) {
            String update = "INSERT into gameData (gameID, whiteUsername, blackUsername, gameName, game) VALUES (int, default, default, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setInt(1, gameData.gameID());
            stmt.setString(2, serializer.toJson(gameData.game()));
            stmt.executeUpdate();
        }
        catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String query = "SELECT * from gameData WHERE gameID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, gameID);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()){
                throw new DataAccessException("Error: bad request");
            }
            int id = rs.getInt("gameID");
            String whiteUsername = rs.getString("whiteUsername");
            String blackUsername = rs.getString("blackUsername");
            String gameName = rs.getString("gameName");
            String gameJSON = rs.getString("game");
            ChessGame game = new Gson().fromJson(gameJSON, ChessGame.class);

            return new GameData(id, whiteUsername, blackUsername, gameName, game);
        }
        catch (Exception e) {
            throw new DataAccessException("Error: bad request");
        }
    }

    @Override
    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String query = "SELECT * from gameData";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            Collection<GameData> gameList = new HashSet<>();
            while (rs.next()) {
                int gameID = rs.getInt("gameID");
                String whiteUsername = rs.getString("whiteUsername");
                String blackUsername = rs.getString("blackUsername");
                String gameName = rs.getString("gameName");
                String gameJSON = rs.getString("game");
                ChessGame game = new Gson().fromJson(gameJSON, ChessGame.class);

                GameData gameData = new GameData(gameID, whiteUsername, blackUsername, gameName, game);
                gameList.add(gameData);
            }
            return gameList;
        }
        catch (Exception e) {
            throw new DataAccessException("Error: Could not get all games");
        }

    }

    @Override
    public void clear() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String update = "TRUNCATE gameData";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
