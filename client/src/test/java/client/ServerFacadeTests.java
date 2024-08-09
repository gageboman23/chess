package client;

import model.GameData;
import model.UserData;
import server.Server;
import org.junit.jupiter.api.*;
import responses.GameInfo;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void clearDatabase() throws Exception {
        facade.clearDB();
    }

    @Test
    void register_Positive() throws Exception {
        var authToken = facade.register("player1", "password", "p1@email.com");
        assertNotNull(authToken);
        assertTrue(authToken.length() > 10);
    }

    @Test
    void register_Negative() {
        assertThrows(Exception.class, () -> facade.register("", "", ""));
    }

    @Test
    void logIn_Positive() throws Exception {
        facade.register("player2", "password", "p2@email.com");
        var authToken = facade.logIn("player2", "password");
        assertNotNull(authToken);
        assertTrue(authToken.length() > 10);
    }

    @Test
    void logIn_Negative() {
        assertThrows(Exception.class, () -> facade.logIn("nonexistentUser", "wrongPassword"));
    }

    @Test
    void logout_Positive() throws Exception {
        facade.register("player3", "password", "p3@email.com");
        facade.logIn("player3", "password");
        facade.logout();
        assertThrows(Exception.class, () -> facade.createGame("TestGame")); // Should fail due to no auth token
    }

    @Test
    void logout_Negative() throws Exception {
        facade.register("player4", "password", "p4@email.com");
        facade.logIn("player4", "password");
        facade.logout();
        assertThrows(Exception.class, () -> facade.logout()); // Should fail because already logged out
    }

    @Test
    void createGame_Positive() throws Exception {
        facade.register("player5", "password", "p5@email.com");
        facade.logIn("player5", "password");
        assertDoesNotThrow(() -> facade.createGame("NewGame"));
    }

    @Test
    void createGame_Negative() {
        assertThrows(Exception.class, () -> facade.createGame("GameWithoutLogin"));
    }

    @Test
    void joinGame_Positive() throws Exception {
        facade.register("player6", "password", "p6@email.com");
        facade.logIn("player6", "password");
        facade.createGame("JoinableGame");
        var gameData = facade.joinGame(1, "white");
        assertNotNull(gameData);
    }

    @Test
    void joinGame_Negative() {
        assertThrows(Exception.class, () -> facade.joinGame(-1, "black")); // Invalid game ID
    }

    @Test
    void listGames_Positive() throws Exception {
        facade.register("player7", "password", "p7@email.com");
        facade.logIn("player7", "password");
        facade.createGame("ListableGame");
        Collection<GameInfo> games = facade.listGames();
        assertNotNull(games);
        assertFalse(games.isEmpty());
    }

    @Test
    void listGames_Negative() {
        assertThrows(Exception.class, () -> facade.listGames()); // Should fail due to no auth token
    }

    @Test
    void observeGame_Positive() throws Exception {
        facade.register("player8", "password", "p8@email.com");
        facade.logIn("player8", "password");
        facade.createGame("ObservableGame");
        var gameData = facade.observeGame(1);
        assertNotNull(gameData);
    }

    @Test
    void observeGame_Negative() {
        assertThrows(Exception.class, () -> facade.observeGame(-1)); // Invalid game ID
    }
}
