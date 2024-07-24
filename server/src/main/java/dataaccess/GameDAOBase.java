package dataaccess;

import model.GameData;

public interface GameDAOBase {

    void insertGame(GameData gameData) throws DataAccessException;
    GameData getGame(String gameID) throws DataAccessException;
    void deleteAuth(String gameID) throws DataAccessException;
    void clear() throws DataAccessException;
}
