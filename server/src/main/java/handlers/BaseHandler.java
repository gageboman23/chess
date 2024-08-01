package handlers;

import dataaccess.DataAccessException;
import spark.Response;
import spark.Request;
import com.google.gson.Gson;
import responses.*;

public interface BaseHandler {

    default Object handle(Request req, Response res) throws DataAccessException {
        return null;
    }

    default Object responseSwitch(Request req, Response res, Object respObj){
        if (respObj instanceof ErrorResponse errorResponse) {
            switch (errorResponse.message()) {
                case "Error: bad request":
                    res.status(400);
                    break;
                case "Error: unauthorized":
                    res.status(401);
                    break;
                case "Error: already taken":
                    res.status(403);
                    break;
            }
        } else {
            res.status(200);
        }
        return res;
    }
}
