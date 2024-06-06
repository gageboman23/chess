package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemUserDAO implements UserDAO {
    HashMap<String, UserData> userDataHashMap = new HashMap<>();

    @Override
    public void createUser(UserData u) throws DataAccessException {
        if (u.username() == null || u.password() == null) {
            throw new DataAccessException("Invalid user data");
        } else if (userDataHashMap.containsKey(u.username())) {
            throw new DataAccessException("User already exists");
        } else {
            userDataHashMap.put(u.username(), u);
        }

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return userDataHashMap.get(username);
    }

    @Override
    public HashMap<String, UserData> getAllUsers() throws DataAccessException {
        return userDataHashMap;
    }

    @Override
    public void clear() {
        userDataHashMap.clear();
    }
}
