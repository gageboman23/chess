package dataaccess;

import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemAuthDAO implements AuthDAO {

    HashMap<String, AuthData> authDataHashMap = new HashMap<>();
    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(authToken, username);
        authDataHashMap.put(authToken, authData);
        return authData;

    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        if (authDataHashMap.get(token) == null) {
            throw new DataAccessException("unauthorized");
        }
        return authDataHashMap.get(token);
    }

    @Override
    public void deleteAuth(String token) throws DataAccessException {
        if (authDataHashMap.containsKey(token)){
            authDataHashMap.remove(token);
        } else {
            throw new DataAccessException("unauthorized");
        }

        if (authDataHashMap.get(token) != null) {
            throw new DataAccessException("unauthorized");
        }

    }

    @Override
    public void clear() {
        authDataHashMap.clear();
    }
}
