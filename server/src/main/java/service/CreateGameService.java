package service;

import dataaccess.*;
import requests.CreateGameRequest;
import responses.CreateGameResponse;
import responses.ErrorResponse;
import chess.ChessGame;
import model.GameData;

import java.util.Random;


public class CreateGameService {

    GameDAOBase gameDAO = new SQLGameDAO();
    AuthDAOBase authDAO = new LocalAuthDAO();


    public Object createGame(CreateGameRequest createGameRequest, String authToken) throws DataAccessException {
        if (createGameRequest.gameName() == null || authToken == null) {
            return new ErrorResponse("Error: bad request");
        }
        if (authDAO.getAuth(authToken) == null) {
            return new ErrorResponse("Error: unauthorized");
        }
        GameData newGame = new GameData(generateGameID(), null, null, createGameRequest.gameName(), new ChessGame());
        gameDAO.insertGame(newGame);
        return new CreateGameResponse(newGame.gameID());
    }

    private Integer generateGameID() {
        Random random = new Random();
        return random.nextInt(500) + 1;
    }


}
