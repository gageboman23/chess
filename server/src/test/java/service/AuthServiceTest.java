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
    void newTokenTest() throws Exception {
        AuthData authData = authService.newToken("user");
        AuthData authData2 = authService.getAuthData(authData.authToken());
        assertEquals(authData.authToken(), authData2.authToken());
    }
}