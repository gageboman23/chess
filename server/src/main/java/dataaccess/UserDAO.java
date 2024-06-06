package dataaccess;
import model.UserData;

import java.util.HashMap;

public interface UserDAO {
    void createUser(UserData u) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
    HashMap<String, UserData> getAllUsers() throws DataAccessException;
    void clear();
}

