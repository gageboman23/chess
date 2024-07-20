package dataaccess;

import model.AuthData;

public interface AuthDAOBase {

    AuthData createAuth(String username) throws DataAccessException;
    AuthData getAuth(String token) throws DataAccessException;
    void deleteAuth(String token) throws DataAccessException;
    void clear();
}
