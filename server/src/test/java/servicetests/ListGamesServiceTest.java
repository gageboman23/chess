package servicetests;

import static org.junit.jupiter.api.Assertions.*;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import responses.ErrorResponse;
import responses.ListGamesResponse;
import service.ListGamesService;
import service.ClearService;


public class ListGamesServiceTest {

    private ListGamesService listGamesService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        listGamesService = new ListGamesService();
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void testListGamesSuccess() throws DataAccessException {
        // Arrange
        String authToken = "validAuthToken";

        // Insert a valid auth token into the AuthDAO
        AuthDAOBase authDAO = new LocalAuthDAO();
        authDAO.insertAuth(new AuthData(authToken, "username"));

        // Insert some games into the GameDAO
        GameDAOBase gameDAO = new LocalGameDAO();
        gameDAO.insertGame(new GameData(1, "player1", null, "testGame1", null));
        gameDAO.insertGame(new GameData(2, null, "player2", "testGame2", null));

        // Act
        Object response = null;
        try {
            response = listGamesService.listGames(authToken);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(ListGamesResponse.class, response);
        ListGamesResponse listGamesResponse = (ListGamesResponse) response;
        assertEquals(2, listGamesResponse.games().size());
    }

    @Test
    public void testListGamesUnauthorized() {
        // Arrange
        String authToken = "invalidAuthToken";

        // Act
        Object response = null;
        try {
            response = listGamesService.listGames(authToken);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(ErrorResponse.class, response);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertEquals("Error: unauthorized", errorResponse.message());
    }
}