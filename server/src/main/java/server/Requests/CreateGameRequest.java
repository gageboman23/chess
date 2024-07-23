package server.Requests;

public record CreateGameRequest(String authToken, String gameName) {}
