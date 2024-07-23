package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import spark.*;
import service.*;

public class BaseHandler {

    RegisterService registerService = new RegisterService();
    AuthService authService = new AuthService();
    UserService userService = new UserService();
    Gson serializer = new Gson();

    public BaseHandler() throws DataAccessException{}

    public Object handle(Request req, Response res) throws Exception{
        throw new Exception("Not Implemented");
    }

    public void authenticate(AuthData authData) throws Exception{
        boolean verified = authService.verifyAuth(authData);
        if (!verified){
            throw new DataAccessException("Error: unauthorized");
        }
    }


}
