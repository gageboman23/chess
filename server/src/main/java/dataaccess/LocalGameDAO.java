package dataaccess;

import model.AuthData;
import model.GameData;

import java.util.HashMap;

public class LocalGameDAO implements GameDAOBase {

    static HashMap<String, GameData> GameMap = new HashMap<>();

    @Override
    public void insertGame(GameData gameData) throws DataAccessException {

    }

    @Override
    public GameData getGame(String gameID) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String gameID) throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException{
        GameMap.clear();
    }
}
