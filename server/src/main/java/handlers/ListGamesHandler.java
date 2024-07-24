package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import responses.ErrorResponse;
import service.ListGamesService;
import spark.*;

public class ListGamesHandler implements BaseHandler{

    private final ListGamesService listGamesService = new ListGamesService();
    Object respObj;

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("List Games Handler");

        var listGamesRequest = req.headers("authorization");
        try {
            respObj = listGamesService.listGames(listGamesRequest);
            responseSwitch(req, res,respObj);
        } catch (DataAccessException e) {
            res.status(500);
            respObj = new ErrorResponse("Error: internal server error");
        }
        return new Gson().toJson(respObj);
    }
}
