package DataAccessTests;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.MemGameDAO;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class sometests {

    private GameDAO GDTest;

    @BeforeEach
    void setUp() {
        GDTest = new MemGameDAO();
        GDTest.clear();
    }

    @Test
    void TestCreateGame() throws DataAccessException {
        int testID = GDTest.createGame("testGame");
        Assertions.assertEquals(1, testID);
        Assertions.assertEquals("testGame", GDTest.getGame(testID).gameName());
    }
}
