package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import spark.*;
import service.*;

public class BaseHandler {

    public BaseHandler() throws DataAccessException{}

    public Object handle(Request req, Response res) throws Exception{
        throw new Exception("Not Implemented");
    }
}
