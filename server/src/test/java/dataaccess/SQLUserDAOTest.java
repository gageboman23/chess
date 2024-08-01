package dataaccess;


import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SQLUserDAOTest {

    private SQLUserDAO userDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        userDAO = new SQLUserDAO();
        userDAO.clear(); // Clear the database before each test
    }

    @Test
    public void testInsertUser_Positive() {
        UserData userData = new UserData("username1", "password1", "email1@example.com");
        assertDoesNotThrow(() -> userDAO.insertUser(userData));
    }

    @Test
    public void testInsertUser_Negative() throws DataAccessException {
        UserData userData = new UserData("username1", "password1", "email1@example.com");
        userDAO.insertUser(userData);

        // Trying to insert a user with the same username should throw DataAccessException
        UserData duplicateUserData = new UserData("username1", "password2", "email2@example.com");
        assertThrows(RuntimeException.class, () -> userDAO.insertUser(duplicateUserData));
    }

    @Test
    public void testGetUser_Positive() throws DataAccessException {
        UserData userData = new UserData("username1", "password1", "email1@example.com");
        userDAO.insertUser(userData);
        assertDoesNotThrow(() -> {
            UserData retrievedUser = userDAO.getUser("username1");
            assertNotNull(retrievedUser);
            assertEquals(userData.username(), retrievedUser.username());
            assertEquals(userData.password(), retrievedUser.password());
            assertEquals(userData.email(), retrievedUser.email());
        });
    }

    @Test
    public void testGetUser_Negative() throws DataAccessException {
        // Attempt to retrieve a non-existent user should return null
        assertNull(userDAO.getUser("nonExistentUser"));
    }

    @Test
    public void testClear_Positive() throws DataAccessException {
        UserData userData = new UserData("username1", "password1", "email1@example.com");
        userDAO.insertUser(userData);

        assertDoesNotThrow(() -> {
            userDAO.clear();
            assertNull(userDAO.getUser("username1"));
        });
    }
}
