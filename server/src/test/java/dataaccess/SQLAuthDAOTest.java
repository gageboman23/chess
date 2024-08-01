package dataaccess;

import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SQLAuthDAOTest {

    private SQLAuthDAO authDAO;

    @BeforeEach
    public void setUp() throws DataAccessException {
        authDAO = new SQLAuthDAO();
        authDAO.clear(); // Clear the database before each test
    }

    @Test
    public void testInsertAuth_Positive() {
        AuthData authData = new AuthData("authToken1", "username1");
        assertDoesNotThrow(() -> authDAO.insertAuth(authData));
    }

    @Test
    public void testInsertAuth_Negative() {
        AuthData invalidAuthData = new AuthData(null, null); // Invalid auth data
        assertThrows(RuntimeException.class, () -> authDAO.insertAuth(invalidAuthData));
    }

    @Test
    public void testGetAuth_Positive() throws DataAccessException {
        AuthData authData = new AuthData("authToken1", "username1");
        authDAO.insertAuth(authData);
        assertDoesNotThrow(() -> {
            AuthData retrievedAuth = authDAO.getAuth("authToken1");
            assertNotNull(retrievedAuth);
            assertEquals(authData.authToken(), retrievedAuth.authToken());
            assertEquals(authData.username(), retrievedAuth.username());
        });
    }

    @Test
    public void testGetAuth_Negative() throws DataAccessException {
        // Attempt to retrieve a non-existent auth token should return null
        assertNull(authDAO.getAuth("nonExistentAuthToken"));
    }

    @Test
    public void testDeleteAuth_Positive() throws DataAccessException {
        AuthData authData = new AuthData("authToken1", "username1");
        authDAO.insertAuth(authData);
        assertDoesNotThrow(() -> {
            authDAO.deleteAuth("authToken1");
            assertNull(authDAO.getAuth("authToken1"));
        });
    }

    @Test
    public void testDeleteAuth_Negative() {
        // Attempt to delete a non-existent auth token should throw DataAccessException
        assertThrows(DataAccessException.class, () -> authDAO.deleteAuth("nonExistentAuthToken"));
    }

    @Test
    public void testClear_Positive() throws DataAccessException {
        AuthData authData = new AuthData("authToken1", "username1");
        authDAO.insertAuth(authData);

        assertDoesNotThrow(() -> {
            authDAO.clear();
            assertNull(authDAO.getAuth("authToken1"));
        });
    }
}
