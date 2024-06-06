package service;

import dataaccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;

import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTest {

    static final UserService userService = new UserService();

    @BeforeEach
    void clear(){
        userService.clear();
    }

    @Test
    void register() throws Exception{
        assertDoesNotThrow(() -> {
            var user1 = new UserData("user1", "pass1", "email1");
            var user2 = new UserData("user2", "pass2", "email2");
            var user3 = new UserData("user3", "pass3", "email3");
            userService.register(user1);
            userService.register(user2);
            userService.register(user3);

            userService.verifyUser(user1);
            userService.verifyUser(user2);
            userService.verifyUser(user3);
        });
    }


    @Test
    void registerFail() throws DataAccessException{
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            var user1 = new UserData("user1", null, "email1");
            userService.register(user1);
        });
        assertTrue(exception.getMessage().contains("bad request"));
    }

    @Test
    void verifyUserTest() throws DataAccessException {
        var user1 = new UserData("user1", "pass1", "email1");
        userService.register(user1);
        assertTrue(userService.verifyUser(user1));
    }

    @Test
    void verifyUserTestFail() throws DataAccessException {
        var user2 = new UserData("user2", "pass2", "email2");
        assertFalse(userService.verifyUser(user2));
    }
}