package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class LocalAuthDAO implements AuthDAOBase{

    static HashMap<String, AuthData> authMap = new HashMap<>();


    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        authMap.put(username, authData); // better to pair it with username or auth token?
        return authData;
    }


    @Override
    public AuthData getAuth(String username) throws DataAccessException {
        if(authMap.get(username) != null) {
            return authMap.get(username);
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
    }

    @Override
    public void deleteAuth(String username) throws DataAccessException {
        if(authMap.get(username) != null) {
            authMap.remove(username);
        }
        else {
            throw new DataAccessException("Error: unauthorized");
        }
    }


    @Override
    public void clear() {
        authMap.clear();
    }
}
