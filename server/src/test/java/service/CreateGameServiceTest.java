package service;

import static org.junit.jupiter.api.Assertions.*;

import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import responses.ErrorResponse;

public class CreateGameServiceTest {

    private CreateGameService createGameService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        createGameService = new CreateGameService();
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void testCreateGameSuccess() throws DataAccessException {
        // Arrange
        String authToken = "validAuthToken";
        CreateGameRequest createGameRequest = new CreateGameRequest("newGame");

        // Insert a valid auth token into the AuthDAO
        AuthDAOBase authDAO = new LocalAuthDAO();
        authDAO.insertAuth(new AuthData(authToken, "username"));

        // Act
        Object response = null;
        try {
            response = createGameService.createGame(createGameRequest, authToken);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(CreateGameResponse.class, response);
        CreateGameResponse createGameResponse = (CreateGameResponse) response;
    }

    @Test
    public void testCreateGameMissingFields() {
        // Arrange
        String authToken = "validAuthToken";
        CreateGameRequest createGameRequest = new CreateGameRequest(null);

        // Act
        Object response = null;
        try {
            response = createGameService.createGame(createGameRequest, authToken);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(ErrorResponse.class, response);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertEquals("Error: bad request", errorResponse.message());
    }
}