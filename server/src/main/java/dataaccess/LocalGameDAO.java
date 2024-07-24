package dataaccess;


import model.GameData;

import java.util.Collection;
import java.util.HashMap;


public class LocalGameDAO implements GameDAOBase {

    static HashMap<Integer, GameData> gameMap = new HashMap<>();

    @Override
    public void insertGame(GameData gameData) throws DataAccessException {
        gameMap.put(gameData.gameID(), gameData);
    }

    @Override
    public GameData getGame(Integer gameID) throws DataAccessException {
        return gameMap.get(gameID);
    }

    @Override
    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        return gameMap.values();
    }

    @Override
    public void deleteGame(String gameID) throws DataAccessException {
        gameMap.remove(gameID);
    }

    @Override
    public void clear() throws DataAccessException{
        gameMap.clear();
    }
}
