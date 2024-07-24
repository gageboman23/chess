package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import responses.ErrorResponse;
import service.ClearService;
import spark.Request;
import spark.Response;

public class ClearHandler {

    private final ClearService clearService = new ClearService();
    Object respObj;

    public Object handle(Request req, Response res) throws DataAccessException {
        try {
            clearService.clear();
            res.status(200);
            return new Gson().toJson(null);

        } catch (DataAccessException e) {
            res.status(500);
            respObj = new ErrorResponse("Error: internal server error");
            return new Gson().toJson(respObj);
        }
    }
}
