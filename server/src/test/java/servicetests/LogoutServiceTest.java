package servicetests;

import static org.junit.jupiter.api.Assertions.*;

import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import responses.ErrorResponse;
import service.LogoutService;
import service.ClearService;

public class LogoutServiceTest {

    private LogoutService logoutService;
    private AuthDAOBase authDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        logoutService = new LogoutService();
        ClearService clearService = new ClearService();
        clearService.clear();
        authDAO = new LocalAuthDAO();
    }

    @Test
    public void testLogoutSuccess() throws DataAccessException {
        // Arrange
        String authToken = "validToken";
        authDAO.insertAuth(new AuthData(authToken, "username"));

        // Act
        Object response = null;
        try {
            response = logoutService.logout(authToken);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertNull(response);
        assertNull(authDAO.getAuth(authToken));
    }

    @Test
    public void testLogoutUnauthorized() {
        // Arrange
        String authToken = "invalidToken";

        // Act
        Object response = null;
        try {
            response = logoutService.logout(authToken);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(ErrorResponse.class, response);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertEquals("Error: unauthorized", errorResponse.message());
    }
}