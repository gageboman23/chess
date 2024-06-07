package service;

import chess.ChessGame;
import dataaccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static service.GameService.gameDAO;

public class GameServiceTest {

    GameService gameService = new GameService();
    UserService userService = new UserService();

    public GameServiceTest() throws DataAccessException {
    }

    @BeforeEach
    void clear(){
        gameService.clear();
        userService.clear();
    }

    @Test
    void clearTest() throws DataAccessException {
        assertDoesNotThrow(() -> {
            gameService.clear();
        });
        assertTrue(gameService.gameDAO.listGames().isEmpty());

    }

    @Test
    void createGame() throws Exception{
        gameService.createGame("game1");
        assertNotNull(gameDAO.getGame(1));
    }

    @Test
    void createGameFail() throws Exception{
        gameService.createGame("game1");
        UserData user = new UserData("user", "pass", "email");
        userService.verifyUser(user);
        assertFalse(userService.verifyUser(user));
    }

    @Test
    void joinGame() throws Exception {
        gameService.createGame("game1");
        ChessGame game = new ChessGame();
        game.getBoard().resetBoard();
        GameData testGame = new GameData(1, "user", "user", "game1", game);
        UserData user = new UserData("user", "pass", "email");
        userService.register(user);
        gameService.joinGame(1, "WHITE", user.username());
        gameService.joinGame(1, "BLACK", user.username());
        gameService.joinGame(1, "", user.username());
        assertEquals(gameService.getGame(1).blackUsername(), testGame.blackUsername());
        assertEquals(gameService.getGame(1).whiteUsername(), testGame.whiteUsername());
    }

    @Test
    void joinGameFail() throws Exception {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            gameService.createGame("game1");
            UserData user = new UserData("user", "pass", "email");
            userService.register(user);
            gameService.joinGame(1, "WHITE", user.username());
            gameService.joinGame(1, "WHITE", user.username());
        });
        assertTrue(exception.getMessage().contains("already taken"));
    }

    @Test
    void listGames() throws Exception {
        gameService.createGame("game1");
        Collection<GameData> gameList = gameService.listGames();
        assertEquals(gameList.size(), 1);
        gameService.createGame("game2");
        gameList = gameService.listGames();
        assertEquals(gameList.size(), 2);
    }

    @Test
    void listGamesFail() throws Exception {
        gameService.createGame("game1");
        Collection<GameData> gameList = gameService.listGames();
        UserData user = new UserData("u", "p", "e");
        assertFalse(userService.verifyUser(user));
    }

    @Test
    void getGame() throws Exception {
        int id = gameService.createGame("game1");
        GameData game = gameService.getGame(id);
        assertNotNull(game);
    }


    @Test
    void getGameFail() throws Exception {
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            int id = gameService.createGame("game1");
            GameData game = gameService.getGame(id+10);
        });
        assertTrue(exception.getMessage().contains("bad request"));
    }

}