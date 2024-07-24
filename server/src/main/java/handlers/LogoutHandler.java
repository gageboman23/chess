package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import Responses.ErrorResponse;
import service.LogoutService;
import spark.*;

public class LogoutHandler{

    private final LogoutService logoutService = new LogoutService();
    Object respObj;

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Logout Handler");

        var logoutRequest = req.headers("authorization");
        try {
            respObj = logoutService.logout(logoutRequest);
            if (respObj instanceof ErrorResponse errorResponse) {
                if (errorResponse.message().equals("Error: unauthorized")) {
                    res.status(401);
                }
            } else {
                res.status(200);
            }
            return new Gson().toJson(respObj);


        } catch (DataAccessException e) {
            res.status(500);
            respObj = new ErrorResponse("Error: internal server error");
            return new Gson().toJson(respObj);
        }
    }
}

//logoutRequest = req.headers(authorization);
