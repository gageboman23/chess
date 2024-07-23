package dataaccess;

import model.AuthData;

import java.util.HashMap;

public class LocalAuthDAO implements AuthDAOBase{

    static HashMap<String, AuthData> authMap = new HashMap<>();


    @Override
    public void insertAuth(AuthData authData) throws DataAccessException{ //insert
        authMap.put(authData.authToken(), authData); // better to pair it with username or auth token?
    }


    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
            return authMap.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
            authMap.remove(authToken);
    }


    @Override
    public void clear() {
        authMap.clear();
    }
}
