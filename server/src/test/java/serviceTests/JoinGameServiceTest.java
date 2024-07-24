package serviceTests;

import static org.junit.jupiter.api.Assertions.*;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.JoinGameRequest;
import responses.ErrorResponse;
import service.JoinGameService;
import service.ClearService;

public class JoinGameServiceTest {

    private JoinGameService joinGameService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        joinGameService = new JoinGameService();
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void testJoinGameSuccess() throws DataAccessException {
        // Arrange
        String authToken = "validAuthToken";
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", 1);

        // Insert a valid auth token into the AuthDAO
        AuthDAOBase authDAO = new LocalAuthDAO();
        authDAO.insertAuth(new AuthData(authToken, "username"));

        // Insert a game into the GameDAO
        GameDAOBase gameDAO = new LocalGameDAO();
        gameDAO.insertGame(new GameData(1, null, null, "testGame", null));

        // Act
        Object response = null;
        try {
            response = joinGameService.joinGame(joinGameRequest, authToken);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertNull(response);  // JoinGameService returns null on success
        GameData gameData = gameDAO.getGame(1);
        assertNotNull(gameData);
        assertEquals("username", gameData.whiteUsername());
    }

    @Test
    public void testJoinGameMissingFields() {
        // Arrange
        String authToken = "validAuthToken";
        JoinGameRequest joinGameRequest = new JoinGameRequest("WHITE", null);

        // Act
        Object response = null;
        try {
            response = joinGameService.joinGame(joinGameRequest, authToken);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(ErrorResponse.class, response);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertEquals("Error: bad request", errorResponse.message());
    }
}