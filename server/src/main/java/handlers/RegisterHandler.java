package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.UserData;
import responses.ErrorResponse;
import service.RegisterService;
import spark.*;

public class RegisterHandler implements BaseHandler{

    private final RegisterService registerService = new RegisterService();

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Register Handler");

        var newUser = new Gson().fromJson(req.body(), UserData.class);
        Object respObj;
        try {
            respObj = registerService.register(newUser);
            responseSwitch(req, res,respObj);
        } catch (DataAccessException e) {
            res.status(500);
            respObj = new ErrorResponse("Error: internal server error");
        }
        return new Gson().toJson(respObj);
    }
}
