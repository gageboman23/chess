package dataaccess;


import model.GameData;
import chess.ChessGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class SQLGameDAOTest {

    private SQLGameDAO gameDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        gameDAO = new SQLGameDAO();
        gameDAO.clear(); // Clear the database before each test
    }

    @Test
    public void testInsertGamePositive() {
        GameData gameData = new GameData(1, "whitePlayer", "blackPlayer", "GameName", new ChessGame());
        assertDoesNotThrow(() -> gameDAO.insertGame(gameData));
    }

    @Test
    public void testInsertGameNegative() {
        GameData invalidGameData = new GameData(1, null, null, null, null); // Invalid game data
        assertThrows(DataAccessException.class, () -> gameDAO.insertGame(invalidGameData));
    }

    @Test
    public void testGetGamePositive() throws DataAccessException {
        GameData gameData = new GameData(1, "whitePlayer", "blackPlayer", "GameName", new ChessGame());
        gameDAO.insertGame(gameData);
        assertDoesNotThrow(() -> {
            GameData retrievedGame = gameDAO.getGame(1);
            assertNotNull(retrievedGame);
            assertEquals(gameData.gameID(), retrievedGame.gameID());
            assertEquals(gameData.gameName(), retrievedGame.gameName());
        });
    }

    @Test
    public void testGetGameNegative() {
        // Attempt to retrieve a non-existent game should throw DataAccessException
        assertThrows(DataAccessException.class, () -> gameDAO.getGame(999));
    }

    @Test
    public void testListGamesPositive() throws DataAccessException {
        GameData gameData1 = new GameData(1, "whitePlayer1", "blackPlayer1", "GameName1", new ChessGame());
        GameData gameData2 = new GameData(2, "whitePlayer2", "blackPlayer2", "GameName2", new ChessGame());
        gameDAO.insertGame(gameData1);
        gameDAO.insertGame(gameData2);

        assertDoesNotThrow(() -> {
            Collection<GameData> games = gameDAO.listGames("authToken");
            assertNotNull(games);
            assertEquals(2, games.size());
        });
    }

    @Test
    public void testListGamesNegative() throws DataAccessException {
        // Invalid authToken should return an empty list or some error response
        Collection<GameData> games = gameDAO.listGames("invalidAuthToken");
        assertTrue(games.isEmpty()); // Assuming it returns an empty collection for invalid auth tokens
    }


    @Test
    public void testClearPositive() throws DataAccessException {
        GameData gameData = new GameData(1, "whitePlayer", "blackPlayer", "GameName", new ChessGame());
        gameDAO.insertGame(gameData);

        assertDoesNotThrow(() -> {
            gameDAO.clear();
            assertThrows(DataAccessException.class, () -> gameDAO.getGame(1));
        });
    }

    @Test
    public void testDeleteGamePositive() throws DataAccessException {
        GameData gameData = new GameData(1, "whitePlayer", "blackPlayer", "GameName", new ChessGame());
        gameDAO.insertGame(gameData);
        assertDoesNotThrow(() -> {
            gameDAO.deleteGame(1);
            assertThrows(DataAccessException.class, () -> gameDAO.getGame(1));
        });
    }

    @Test
    public void testDeleteGameNegative() {
        // Attempt to delete a non-existent game should not throw an exception
        assertDoesNotThrow(() -> gameDAO.deleteGame(999));
    }
}
