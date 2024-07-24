package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import requests.CreateGameRequest;
import responses.ErrorResponse;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler implements BaseHandler {

    private final CreateGameService createGameService = new CreateGameService();
    Object respObj;

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Create Game Handler");

        String authToken = req.headers("authorization");
        CreateGameRequest createGameRequest = new Gson().fromJson(req.body(), CreateGameRequest.class);
        try {
            respObj = createGameService.createGame(createGameRequest, authToken);
            responseSwitch(req, res,respObj);
        } catch (DataAccessException e) {
            res.status(500);
            respObj = new ErrorResponse("Error: internal server error");
        }
        return new Gson().toJson(respObj);
    }
}