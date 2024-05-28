package dataaccess;
import model.GameData;

import java.util.Collection;

public interface GameDAO {
    int createGame(String gameName) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void updateGame(int gameID, GameData gameData) throws DataAccessException;
    Collection<GameData> listGames() throws DataAccessException;
    void clear();
}