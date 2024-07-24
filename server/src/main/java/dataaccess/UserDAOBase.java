package dataaccess;

import model.UserData;

public interface UserDAOBase {
    void insertUser(UserData ud) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;

    void clear() throws DataAccessException;

}
