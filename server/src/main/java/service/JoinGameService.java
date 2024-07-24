package service;

import requests.JoinGameRequest;
import responses.ErrorResponse;
import dataaccess.*;
import model.GameData;


public class JoinGameService {

    GameDAOBase gameDAO = new LocalGameDAO();
    AuthDAOBase authDAO = new LocalAuthDAO();


    public Object joinGame(JoinGameRequest joinGameRequest, String authToken) throws DataAccessException {

        if (joinGameRequest.gameID() == null || joinGameRequest.playerColor() == null || authToken == null) {
            return new ErrorResponse("Error: bad request");
        }
        if (authDAO.getAuth(authToken) == null) {
            return new ErrorResponse("Error: unauthorized");
        }
        if (gameDAO.getGame(joinGameRequest.gameID()) == null) {
            return new ErrorResponse("Error: bad request");
        }

        GameData requestedGameData;
        if (joinGameRequest.playerColor().equals("WHITE") || joinGameRequest.playerColor().equals("BLACK")) {
            requestedGameData = gameDAO.getGame(joinGameRequest.gameID());
        } else {
            return new ErrorResponse("Error: bad request");
        }

        if (joinGameRequest.playerColor().equals("BLACK")) {
            if (requestedGameData.blackUsername() != null) {
                return new ErrorResponse("Error: already taken");
            }
            gameDAO.insertGame(new GameData(requestedGameData.gameID(), requestedGameData.whiteUsername(),
                    authDAO.getAuth(authToken).username(), requestedGameData.gameName(), requestedGameData.game()));
            return null;
        }
        if (requestedGameData.whiteUsername() != null) {
            return new ErrorResponse("Error: already taken");
        }
        gameDAO.insertGame(new GameData(requestedGameData.gameID(), authDAO.getAuth(authToken).username(),
                requestedGameData.blackUsername(), requestedGameData.gameName(), requestedGameData.game()));
        return null;

    }
}
