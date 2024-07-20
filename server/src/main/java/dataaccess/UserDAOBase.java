package dataaccess;

import model.UserData;

public interface UserDAOBase {
    void createUser(UserData ud) throws DataAccessException;
    UserData getUser(String username) throws DataAccessException;
    void deleteUser(String username);
    void clear();

}
