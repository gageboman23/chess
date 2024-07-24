package server;

import handlers.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.post("/user", ((req, res) -> (new RegisterHandler()).handle(req, res)));
        Spark.delete("/db", ((req, res) -> (new ClearHandler()).handle(req, res)));
        Spark.post("/session", (((req, res) -> (new LoginHandler()).handle(req, res))));
        Spark.delete("/session", (((req, res) -> (new LogoutHandler()).handle(req, res))));
        Spark.get("/game", (((req, res) -> (new ListGamesHandler()).handle(req, res))));
        Spark.post("/game", (((req, res) -> (new CreateGameHandler()).handle(req, res))));
        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
