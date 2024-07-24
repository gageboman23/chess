package service;

import Responses.ListGamesResponse;
import dataaccess.*;
import model.AuthData;
import model.UserData;

import Responses.ErrorResponse;
import Responses.LoginResponse;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

public class ListGamesService {

    GameDAOBase gameDAO = new LocalGameDAO();
    AuthDAOBase authDAO = new LocalAuthDAO();


    public Object listGames(String authoken) throws DataAccessException {
        if (authoken != null && authDAO.getAuth(authoken) != null){
            Collection gameList = gameDAO.listGames(authoken);
            return new ListGamesResponse(gameList);
        } else {
            return new ErrorResponse("Error: unauthorized");
        }
    }
}
