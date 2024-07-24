package service;

import static org.junit.jupiter.api.Assertions.*;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClearServiceTest {

    private ClearService clearService;
    private UserDAOBase userDAO;
    private AuthDAOBase authDAO;
    private GameDAOBase gameDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        clearService = new ClearService();
        userDAO = new LocalUserDAO();
        authDAO = new LocalAuthDAO();
        gameDAO = new LocalGameDAO();
    }

    @Test
    public void testClear() {
        // Arrange
        try {
            userDAO.insertUser(new UserData("username", "password", "email@example.com"));
            authDAO.insertAuth(new AuthData("authToken", "username"));
            gameDAO.insertGame(new GameData(1, "whitePlayer", "blackPlayer", "gameName", null));

            // Act
            clearService.clear();

            // Assert
            assertNull(userDAO.getUser("username"));
            assertNull(authDAO.getAuth("authToken"));
            assertNull(gameDAO.getGame(1));
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}