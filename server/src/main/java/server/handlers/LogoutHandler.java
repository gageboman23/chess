package server.handlers;

import dataaccess.DataAccessException;
import spark.Request;
import spark.Response;

public class LogoutHandler extends GenHandler{

    public LogoutHandler() throws DataAccessException {
    }

    public Object handle(Request req, Response res){
        try {
            String authToken = req.headers("Authorization");
            authenticate(authToken);
            authService.delete(authToken);
            res.status(200);
            return "{}";
        } catch (Exception e) {
            return findException(req, res, e);
        }
    }

}