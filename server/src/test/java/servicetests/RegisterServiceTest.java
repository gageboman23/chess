package servicetests;

import static org.junit.jupiter.api.Assertions.*;
import dataaccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import responses.ErrorResponse;
import responses.RegisterResponse;
import service.RegisterService;
import service.ClearService;

public class RegisterServiceTest {

    private RegisterService registerService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        registerService = new RegisterService();
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void testRegisterSuccess() {
        // Arrange
        UserData userData = new UserData("username", "password", "email@example.com");

        // Act
        Object response = null;
        try {
            response = registerService.register(userData);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(RegisterResponse.class, response);
        RegisterResponse registerResponse = (RegisterResponse) response;
        assertEquals("username", registerResponse.username());
        assertNotNull(registerResponse.authToken());
    }

    @Test
    public void testRegisterMissingFields() {
        // Arrange
        UserData userData = new UserData(null, "password", "email@example.com");

        // Act
        Object response = null;
        try {
            response = registerService.register(userData);
        } catch (DataAccessException e) {
            fail("Exception thrown: " + e.getMessage());
        }

        // Assert
        assertInstanceOf(ErrorResponse.class, response);
        ErrorResponse errorResponse = (ErrorResponse) response;
        assertEquals("Error: bad request", errorResponse.message());
    }
}