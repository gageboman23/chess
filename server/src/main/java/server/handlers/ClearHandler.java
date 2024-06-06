package server.handlers;

import dataaccess.DataAccessException;
import spark.Request;
import spark.Response;

public class ClearHandler extends GenHandler{

    public ClearHandler() throws DataAccessException {
    }

    @Override
    public Object handle(Request req, Response res) {
        try {
            gameService.clear();
            userService.clear();
            authService.clear();
            res.status(200);
            return "{}";
        } catch (Exception e){
            return findException(req, res, e);
        }
    }
}