package server.Requests;

public record JoinGameRequest(String authToken, String playerColor, int gameID) {
}
