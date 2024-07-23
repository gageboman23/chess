package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.UserData;
import server.Responses.ErrorResponse;
import service.RegisterService;
import spark.*;

public class RegisterHandler extends BaseHandler {

    private final RegisterService registerService = new RegisterService();

    public RegisterHandler() throws DataAccessException {
    }

    @Override
    public Object handle(Request req, Response res) throws DataAccessException {
        System.out.println("Register Handler");

        var newUser = new Gson().fromJson(req.body(), UserData.class);
        Object respObj;
        try {
            respObj  = registerService.register(newUser);

            if (respObj instanceof ErrorResponse){
                ErrorResponse errorResponse = (ErrorResponse) respObj;
                switch (errorResponse.message()){
                    case "Error: bad request":
                        res.status(400);
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
 // this is where I will receive the register request, deserialize the json, pass to service, and when i get it back, reserialize and return proper codes for behavior.