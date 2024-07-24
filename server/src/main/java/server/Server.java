package server;

import handlers.ClearHandler;
import handlers.RegisterHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");
        Spark.get("/", (req, res) -> "Hello from Spark Server!");
        Spark.post("/user", ((req, res) -> (new RegisterHandler()).handle(req, res)));
        Spark.delete("/db", ((req, res) -> (new ClearHandler()).handle(req, res)));

        // Register your endpoints and handle exceptions here.

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
