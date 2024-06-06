package service;

import dataaccess.*;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;


public class AuthServiceTest {

    AuthService authService = new AuthService();
    UserService userService = new UserService();

    public AuthServiceTest() throws DataAccessException {
    }

    @BeforeEach
    void clear() {
        authService.clear();
        userService.clear();
    }

    @Test
    void newToken() throws Exception {
        AuthData authData = authService.newToken("user");
        AuthData authData2 = authService.getAuthData(authData.authToken());
        assertEquals(authData.authToken(), authData2.authToken());
    }

    @Test
    void newTokenFail() throws Exception {
        AuthData authData = authService.newToken("user");
        String token = authData.authToken();
        AuthData authData2 = authService.newToken("user2");
        String token2 = authData2.authToken();
        assertNotEquals(token, token2);
    }

    @Test
    void getAuthData() throws DataAccessException {
        AuthData authData = authService.newToken("user");
        AuthData authData2 = authService.getAuthData(authData.authToken());
        assertEquals(authData, authData2);
    }

    @Test
    void getAuthDataFail() throws DataAccessException {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            authService.getAuthData("bad token");
        });
        assertTrue(exception.getMessage().contains("unauthorized"));
    }

    @Test
    void delete() throws Exception {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            AuthData authData = authService.newToken("user");
            String token = authData.authToken();
            authService.delete(token);
            authService.getAuthData(token);
        });
        assertTrue(exception.getMessage().contains("unauthorized"));
    }

    @Test
    void deleteFail() throws Exception {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            authService.delete("not an auth token");
        });
        assertTrue(exception.getMessage().contains("unauthorized"));
    }

    @Test
    void clearTest() throws Exception {
        assertDoesNotThrow(() -> {
            authService.clear();
        });
    }

}