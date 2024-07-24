package handlers;


import Requests.JoinGameRequest;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import Responses.ErrorResponse;
import service.JoinGameService;
import spark.*;

public class JoinGameHandler{

    private final JoinGameService joinGameService = new JoinGameService();
    Object respObj;

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Join Game Handler");

        String authToken = req.headers("authorization");
        JoinGameRequest joinGameRequest = new Gson().fromJson(req.body(), JoinGameRequest.class);
        try {
            respObj  = joinGameService.joinGame(joinGameRequest, authToken);

            if (respObj instanceof ErrorResponse errorResponse){
                switch (errorResponse.message()){
                    case "Error: bad request":
                        res.status(400);
                        break;
                    case "Error: unauthorized":
                        res.status(401);
                        break;
                    case "Error: already taken":
                        res.status(403);
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