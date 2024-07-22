package dataaccess;

import model.UserData;

import java.util.HashMap;

public class LocalUserDAO implements UserDAOBase{

    static HashMap<String, UserData> userMap = new HashMap<>();

    @Override
    public void createUser(UserData ud) throws DataAccessException {
        if (ud.password() == null || ud.username() == null || ud.email() == null) {
            throw new DataAccessException("Error: bad request");
        } else if(userMap.get(ud.username()) != null){
            throw new DataAccessException("Error: already taken");
        } else {
            userMap.put(ud.username(), ud);
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        if (username == null){
            throw new DataAccessException("Error: bad request");
        }
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
