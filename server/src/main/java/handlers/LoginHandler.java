package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import requests.LoginRequest;
import responses.ErrorResponse;
import service.LoginService;
import spark.*;

public class LoginHandler implements BaseHandler{

    private final LoginService loginService = new LoginService();

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Login Handler");

        var loginRequest = new Gson().fromJson(req.body(), LoginRequest.class);
        Object respObj;
        try {
            respObj = loginService.login(loginRequest);
            responseSwitch(req, res,respObj);
        } catch (DataAccessException e) {
            res.status(500);
            respObj = new ErrorResponse("Error: internal server error");
        }
        return new Gson().toJson(respObj);
    }
}
