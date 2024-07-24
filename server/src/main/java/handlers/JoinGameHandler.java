package handlers;


import requests.JoinGameRequest;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import responses.ErrorResponse;
import service.JoinGameService;
import spark.*;

public class JoinGameHandler implements BaseHandler{

    private final JoinGameService joinGameService = new JoinGameService();
    Object respObj;

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Join Game Handler");

        String authToken = req.headers("authorization");
        JoinGameRequest joinGameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
        try {
            respObj = joinGameService.joinGame(joinGameRequest, authToken);
            responseSwitch(req, res,respObj);
        } catch (DataAccessException e) {
            res.status(500);
            respObj = new ErrorResponse("Error: internal server error");
        }
        return new Gson().toJson(respObj);
    }
}