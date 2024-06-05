package service;

import dataaccess.DataAccessException;
import dataaccess.GameDAO;

import model.GameData;

import java.util.Collection;

public class GameService {
    public static GameDAO gameDAO;

    public Collection<GameData> listGames() throws DataAccessException {
        return gameDAO.listGames();
    }
    public GameData getGame(int gameID) throws DataAccessException {
        return gameDAO.getGame(gameID);
    }
    public int createGame(String gameName) throws DataAccessException {
        return gameDAO.createGame(gameName);
    }
    public GameData joinGame(int gameID, String username, String color) throws DataAccessException {
        GameData gameData = gameDAO.getGame(gameID);
        //not sure where to take this yet.
    }
}
