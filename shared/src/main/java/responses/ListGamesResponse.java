package responses;

import java.util.Collection;


public record ListGamesResponse(Collection<GameInfo> games) {
}

record GameInfo(int gameID, String whiteUsername, String blackUsername, String gameName) {
}

