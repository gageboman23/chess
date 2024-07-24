package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import Requests.LoginRequest;
import Responses.ErrorResponse;
import service.LoginService;
import spark.*;

public class LoginHandler{

    private final LoginService loginService = new LoginService();

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Login Handler");

        var loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);
        Object respObj;
        try {
            respObj  = loginService.login(loginRequest);

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
