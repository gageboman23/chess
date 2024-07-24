package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import Responses.ErrorResponse;
import service.ListGamesService;
import spark.*;

public class ListGamesHandler{

    private final ListGamesService listGamesService = new ListGamesService();
    Object respObj;

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("List Games Handler");

        var listGamesRequest = req.headers("authorization");
        try {
            respObj  = listGamesService.listGames(listGamesRequest);

            if (respObj instanceof ErrorResponse errorResponse){
                if (errorResponse.message().equals("Error: unauthorized")) {
                    res.status(401);
                } else {
                    res.status(500);
                }
            } else {
                res.status(200);
            }
        } catch (DataAccessException e) {
            res.status(500);
            respObj = new ErrorResponse("Error: internal server error");
        }
        return new Gson().toJson(respObj);
    }
}
