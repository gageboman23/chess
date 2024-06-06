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
    public GameData joinGame(int gameID, String playerColor, String username) throws DataAccessException{
        GameData gameData = gameDAO.getGame(gameID);
        GameData newGameData = gameData;
        if(playerColor == null || playerColor.isEmpty() || playerColor.equals("empty")){
            return newGameData;
        }
        else if (playerColor.equals("WHITE")){
            if (gameData.whiteUsername() == null){
                newGameData = new GameData(gameID, username, gameData.blackUsername(), gameData.gameName(), gameData.game());
                gameDAO.updateGame(gameID, newGameData);
            }
            else {
                throw new DataAccessException("Error: already taken");
            }
        }
        else if (playerColor.equals("BLACK")){
            if (gameData.blackUsername() == null){
                newGameData = new GameData(gameID, gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
                gameDAO.updateGame(gameID, newGameData);
            }
            else {
                throw new DataAccessException("Error: already taken");
            }
        }
        else {
            throw new DataAccessException("Error: bad request");
        }
        return newGameData;
    }

    public void clear(){
        gameDAO.clear();
    }
}
