package client;

import model.GameData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessClient {

    private final ServerFacade server;
    private final String serverUrl;
    private List<GameData> gameDataList = new ArrayList<>();


    private State state = State.SIGNEDOUT;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(8080);
        this.serverUrl = serverUrl;
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "signin" -> logIn(params);
                case "register" -> register(params);
                case "creategame" -> createGame(params);
                case "logout" -> logout();
                case "joingame" -> joinGame(params);
                case "listgames" -> listGames();
                case "observegame" -> observeGame(params);
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    public String help() {
        if (state == State.SIGNEDOUT) {
            return """
                    - signIn <username> <password>
                    - register <username> <password> <email>
                    - quit
                    - help
                    """;
        }
        return """
                - help
                - logout
                - createGame <game name>
                - listGames
                - joinGame <game ID> <player color>
                - observeGame <game ID>
                """;
    }

    public String register(String... params) throws Exception {
        if (params.length >= 3) {
            String username = params[0];
            String password = params[1];
            String email = params[2];
            server.register(username, password, email);
            state = State.SIGNEDIN;
        }
        else {
            throw new Exception("Error: Expected <username> <password> <email>");

        }
        return "New user created. User logged in.";
    }

    public String logIn(String... params) throws Exception{
        if (params.length >= 2){
            String username = params[0];
            String password = params[1];
            server.logIn(username, password);
            state = State.SIGNEDIN;
        }
        else {
            throw new Exception("Error: Expected <username> <password>");

        }
        return "User logged in.";
    }


    public String logout() throws Exception {
        assertSignedIn();
        System.out.println("Logging out");
        server.logout();
        state = State.SIGNEDOUT;
        return "Signed out";
    }

}