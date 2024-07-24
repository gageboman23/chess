package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.UserData;
import Responses.ErrorResponse;
import service.RegisterService;
import spark.*;

public class RegisterHandler{

    private final RegisterService registerService = new RegisterService();

    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Register Handler");

        var newUser = new Gson().fromJson(req.body(), UserData.class);
        Object respObj;
        try {
            respObj  = registerService.register(newUser);

            if (respObj instanceof ErrorResponse errorResponse){
                switch (errorResponse.message()){
                    case "Error: bad request":
                        res.status(400);
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
