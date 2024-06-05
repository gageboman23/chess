package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import ResponseTypes.RegisterResponse;
import spark.Request;
import spark.Response;

public class LoginHandler extends GenHandler{

    public LoginHandler() throws DataAccessException {
    }

    public Object handle(Request req, Response res) throws DataAccessException{
        try {
            System.out.println("Test");
            UserData userData = new Gson().fromJson(req.body(), UserData.class);
            if (userService.verifyUser(userData)){
                AuthData authData = authService.newToken(userData.username());
                RegisterResponse response = new RegisterResponse(authData.username(), authData.authToken());
                return response.toJSon();
            }
            else {
                throw new DataAccessException("Error: unauthorized");
            }
        }
        catch (DataAccessException e) {
            return findException(req, res, e);
        }
    }
}