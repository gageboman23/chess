package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.MemAuthDAO;
import model.AuthData;


public class AuthService {

    public static AuthDAO authDAO;

    public AuthService() throws DataAccessException {
        try {
            authDAO = new MemAuthDAO();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public AuthData newToken(String username) throws DataAccessException{
        return authDAO.createAuth(username);
    }

    public AuthData getAuthData(String authToken) throws DataAccessException{
        if (authToken == null || authToken.isEmpty()) {
            throw new DataAccessException("unauthorized");
        }
        return authDAO.getAuth(authToken);
    }

    public void delete(String authToken) throws DataAccessException{
        authDAO.deleteAuth(authToken);
    }

    public void clear(){
        authDAO.clear();
    }

}