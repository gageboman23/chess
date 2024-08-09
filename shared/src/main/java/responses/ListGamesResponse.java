package responses;

import java.util.Collection;


public record ListGamesResponse(Collection<GameInfo> games) {
}

