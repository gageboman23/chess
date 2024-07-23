package server.Responses;

import java.util.List;

public record ListGamesResponse(List<GameInfo> games) {}

record GameInfo(int gameID, String whiteUsername, String blackUsername, String gameName) {}

