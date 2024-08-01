package service;

import responses.ListGamesResponse;
import responses.ErrorResponse;
import dataaccess.*;

import java.util.Collection;

public class ListGamesService {

    GameDAOBase gameDAO = new SQLGameDAO();
    AuthDAOBase authDAO = new SQLAuthDAO();


    public Object listGames(String authoken) throws DataAccessException {
        if (authoken != null && authDAO.getAuth(authoken) != null) {
            Collection gameList = gameDAO.listGames(authoken);
            return new ListGamesResponse(gameList);
        } else {
            return new ErrorResponse("Error: unauthorized");
        }
    }
}
