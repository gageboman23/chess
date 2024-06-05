package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;

public class MemGameDAO implements GameDAO {
    Collection<GameData> AllGames = new ArrayList<>();
    int gameCounter = 0;

    @Override
    public int createGame(String gameName) throws DataAccessException {
        gameCounter++;
        GameData newGame = new GameData(gameCounter, null, null, gameName, new ChessGame());
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
        for (GameData game : AllGames) {
            if (game.gameID() == gameID) {
                game = gameData;
            }
        }

    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return AllGames;
    }

    @Override
    public void clear() {
        AllGames.clear();
    }
}
