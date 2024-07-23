package dataaccess;

import model.UserData;

import java.util.HashMap;

public class LocalUserDAO implements UserDAOBase{

    static HashMap<String, UserData> userMap = new HashMap<>();

    @Override
    public void insertUser(UserData ud) throws DataAccessException {
            userMap.put(ud.username(), ud);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return userMap.get(username);
    }

    @Override
    public void deleteUser(String username) {
        userMap.remove(username);

    }

    @Override
    public void clear() {
        userMap.clear();
    }
}
