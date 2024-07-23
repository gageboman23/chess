package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import spark.*;

public class RegisterHandler extends BaseHandler {

    public RegisterHandler() throws DataAccessException {
    }

    public Object handle(Request req, Response res) throws DataAccessException {

        System.out.println("Register Handler");

        var newUser = new Gson().fromJson(req.body(), UserData.class);
        Object respObj = registerService.register(newUser);
        return new Gson().toJson(respObj);
    }
}
 // this is where I will receive the register request, deserialize the json, pass to service, and when i get it back, reserialize and return proper codes for behavior.