package dataaccess;

import chess.ChessGame;
import model.GameData;
import java.util.Collection;
import java.util.HashSet;
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

    SQLconfig NewConfig = new SQLconfig();

    private void configureDatabase() throws DataAccessException {
        NewConfig.config(createStatements);
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
        deleteGame(gameData.gameID());
        try (Connection conn = DatabaseManager.getConnection()) {
            String update = "INSERT into gameData (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setInt(1, gameData.gameID());
            stmt.setString(2, gameData.whiteUsername());
            stmt.setString(3, gameData.blackUsername());
            stmt.setString(4, gameData.gameName());
            String gameDataJSON = serializer.toJson(gameData.game());
            stmt.setString(5, gameDataJSON);
            stmt.executeUpdate();
        }
        catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {
        Gson serializer = new Gson();
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
            ChessGame game = serializer.fromJson(gameJSON, ChessGame.class);

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

    public void deleteGame(Integer gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String query = "DELETE from gameData WHERE gameID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, gameID);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
