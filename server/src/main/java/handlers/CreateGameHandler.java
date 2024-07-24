package handlers;

import requests.CreateGameRequest;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import responses.ErrorResponse;
import service.CreateGameService;
import spark.*;

public class CreateGameHandler {

    private final CreateGameService createGameService = new CreateGameService();
    Object respObj;

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Create Game Handler");

        String authToken = req.headers("authorization");
        CreateGameRequest createGameRequest = new Gson().fromJson(req.body(), CreateGameRequest.class);
        try {
            respObj = createGameService.createGame(createGameRequest, authToken);

            if (respObj instanceof ErrorResponse errorResponse) {
                switch (errorResponse.message()) {
                    case "Error: bad request":
                        res.status(400);
                        break;
                    case "Error: unauthorized":
                        res.status(401);
                        break;
                    default:
                        res.status(500);
                        break;
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