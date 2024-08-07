package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAOBase {

    void insertGame(GameData gameData) throws DataAccessException;

    GameData getGame(Integer gameID) throws DataAccessException;

    Collection<GameData> listGames(String authToken) throws DataAccessException;

    void clear() throws DataAccessException;
}
