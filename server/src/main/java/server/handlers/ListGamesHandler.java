package server.handlers;

import dataaccess.DataAccessException;
import model.GameData;
import ResponseTypes.GameListResponse;
import spark.Request;
import spark.Response;
import java.util.Collection;

public class ListGamesHandler extends GenHandler{

    public ListGamesHandler() throws DataAccessException {
    }

    public Object handle(Request req, Response res){
        try {
            String authToken = req.headers("Authorization");
            authenticate(authToken);
            Collection<GameData> games = gameService.listGames();
            res.status(200);
            GameListResponse response = new GameListResponse(games);
            String output = response.toJSon();
            return output;
        } catch (Exception e) {
            return findException(req, res, e);
        }
    }

}