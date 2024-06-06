package ResponseTypes;

import model.GameData;

import java.util.Collection;

public class GameListResponse extends Response{

    public Collection<GameData> games;

    public GameListResponse(Collection<GameData> games){
        this.games = games;
    }

}