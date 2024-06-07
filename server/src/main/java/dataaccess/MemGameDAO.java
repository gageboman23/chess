package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemGameDAO implements GameDAO {
    static int gameCounter = 0;
    static HashMap<Integer, GameData> gameList = new HashMap<>();

    @Override
    public int createGame(String gameName) throws DataAccessException {
        gameCounter++;
        int gameID = gameCounter;
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(gameID, null, null, gameName, game);
        gameList.put(gameID, gameData);
        return gameID;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        if(gameList.get(gameID) != null){
            return gameList.get(gameID);
        }
        else {
            throw new DataAccessException("Error: bad request");
        }
    }

    @Override
    public void updateGame(int gameID, GameData gameData) throws DataAccessException{
        if(gameList.get(gameID) != null){
            gameList.replace(gameID, gameData);
        }
        else {
            throw new DataAccessException("Error: bad request");
        }
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return gameList.values();
    }

    @Override
    public void clear() {
        gameList.clear();
    }
}
