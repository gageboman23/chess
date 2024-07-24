package servicetests;

import static org.junit.jupiter.api.Assertions.*;

import dataaccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import responses.ErrorResponse;
import responses.LoginResponse;
import service.LoginService;
import service.ClearService;

public class LoginServiceTest {

    private LoginService loginService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        loginService = new LoginService();
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void testLoginSuccess() throws DataAccessException {
        // Arrange
        String username = "username";
        String password = "password";
        String email = "email@example.com";
        UserDAOBase userDAO = new LocalUserDAO();
        userDAO.insertUser(new UserData(username, password, email));

        LoginRequest loginRequest = new LoginRequest(username, password);

        // Act
        Object response = null;
        try {
            response = loginService.login(loginRequest);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(LoginResponse.class, response);
        LoginResponse loginResponse = (LoginResponse) response;
        assertEquals(username, loginResponse.username());
        assertNotNull(loginResponse.authToken());
    }

    @Test
    public void testLoginUnauthorized() throws DataAccessException {
        // Arrange
        String username = "username";
        String password = "password";
        String email = "email@example.com";
        UserDAOBase userDAO = new LocalUserDAO();
        userDAO.insertUser(new UserData(username, password, email));

        LoginRequest loginRequest = new LoginRequest(username, "wrongPassword");

        // Act
        Object response = null;
        try {
            response = loginService.login(loginRequest);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(ErrorResponse.class, response);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertEquals("Error: unauthorized", errorResponse.message());
    }
}