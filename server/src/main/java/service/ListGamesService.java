package service;

import Responses.ListGamesResponse;
import Responses.ErrorResponse;
import dataaccess.AuthDAOBase;
import dataaccess.DataAccessException;
import dataaccess.GameDAOBase;
import dataaccess.LocalAuthDAO;
import dataaccess.LocalGameDAO;

import java.util.Collection;

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
