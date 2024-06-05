package server.handlers;


import ResponseTypes.ErrorResponse;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import spark.Request;
import spark.Response;
import service.*;


public class GenHandler {

    GameService gameService = new GameService();
    UserService userService = new UserService();
    AuthService authService = new AuthService();
    Gson serializer = new Gson();

    public GenHandler() throws DataAccessException {
    }

    public Object handle(Request req, Response res) throws Exception{
        throw new Exception("Not Implemented");
    }

    public void authenticate(String authToken) throws Exception{
        AuthData authData = authService.getAuthData(authToken);
        if (authData == null){
            throw new DataAccessException("Error: unauthorized");
        }
    }

    public Object findException(Request req, Response res, Exception e){
        ErrorResponse response = new ErrorResponse(e.getMessage());
        if (e.getMessage().equals("Error: bad request")){
            res.status(400);
        }
        else if (e.getMessage().equals("Error: unauthorized")) {
            res.status(401);
        } else if (e.getMessage().equals("Error: already taken")) {
            res.status(403);
        }
        else {
            res.status(500);
        }
        return response.toJSon();
    }


}