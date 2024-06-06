package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import spark.Request;
import spark.Response;

import java.util.Map;

public class JoinGameHandler extends GenHandler{

    public JoinGameHandler() throws DataAccessException {
    }

    @Override
    public Object handle(Request req, Response res) throws Exception {
        try {
            String authToken = req.headers("Authorization");
            authenticate(authToken);
            AuthData authData = authService.getAuthData(authToken);
            String username = authData.username();
            System.out.println("JGH");
            Gson gson = new Gson();
            Map<String, Object> requestBody = gson.fromJson(req.body(), Map.class);
            String playerColor = (String) requestBody.get("playerColor");
            System.out.println("JGH2");
            double dgameID = (double) requestBody.get("gameID");
            int gameID = (int) dgameID;
            GameData gameData = gameService.joinGame(gameID, playerColor, username);
            res.status(200);
            System.out.println("JGH3");
            return new Gson().toJson(gameData);
        } catch (Exception e) {
            System.out.println("JGH Exception");
            return findException(req, res, e);

        }
    }
}