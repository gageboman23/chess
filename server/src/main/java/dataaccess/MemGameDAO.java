package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemGameDAO implements GameDAO {
    Collection<GameData> AllGames = new ArrayList<>();

    @Override
    public int createGame(String gameName) throws DataAccessException {
        //change how game ID increments
        GameData newGame = new GameData(1, null, null, gameName, new ChessGame());
        AllGames.add(newGame);
        return newGame.gameID();
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        for (GameData gameData : AllGames) {
            if (gameData.gameID() == gameID) {
                return gameData;
            }
        }
        return null;
    }

    @Override
    public void updateGame(int gameID, GameData gameData) throws DataAccessException {

    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void clear() {

    }
}
