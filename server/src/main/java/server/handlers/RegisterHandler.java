package server.handlers;

import ResponseTypes.RegisterResponse;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import spark.Request;
import spark.Response;

public class RegisterHandler extends GenHandler{

    public RegisterHandler() throws DataAccessException {
    }

    public Object handle(Request req, Response res){

        System.out.println("Register Handler");

        try {
            var newUser = new Gson().fromJson(req.body(), UserData.class);
            userService.register(newUser);
            String username = newUser.username();
            AuthData authData = authService.newToken(username);
            res.status(200);
            var response = new RegisterResponse(username, authData.authToken());
            return response.toJSon();
        } catch (Exception e){
            return findException(req, res, e);
        }
    }
}