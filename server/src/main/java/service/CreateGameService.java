package service;

import Requests.CreateGameRequest;
import Responses.CreateGameResponse;
import Responses.ErrorResponse;
import chess.ChessGame;
import dataaccess.AuthDAOBase;
import dataaccess.DataAccessException;
import dataaccess.GameDAOBase;
import dataaccess.LocalAuthDAO;
import dataaccess.LocalGameDAO;
import model.GameData;


public class CreateGameService {

    GameDAOBase gameDAO = new LocalGameDAO();
    AuthDAOBase authDAO = new LocalAuthDAO();
    int gameIDcounter = 0;


    public Object createGame(CreateGameRequest createGameRequest, String authToken) throws DataAccessException {
        if (createGameRequest.gameName() == null || authToken == null) {
            return new ErrorResponse("Error: bad request");
        }
        if (authDAO.getAuth(authToken) == null) {
            return new ErrorResponse("Error: unauthorized");
        }
        int thisGamesID = createGameID();
        gameDAO.insertGame(new GameData(thisGamesID, null, null, createGameRequest.gameName(), new ChessGame()));
        return new CreateGameResponse(thisGamesID);
        }

    private int createGameID(){
        gameIDcounter++;
        return gameIDcounter;
    }
}
