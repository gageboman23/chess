package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.MemAuthDAO;
import model.AuthData;


public class AuthService {

    public static AuthDAO authDAO = new MemAuthDAO();

    public AuthData newToken(String username) throws DataAccessException{
        return authDAO.createAuth(username);
    }

    public AuthData getAuthData(String authToken) throws DataAccessException{
        return authDAO.getAuth(authToken);
    }

    public void delete(String authToken) throws DataAccessException{
        authDAO.deleteAuth(authToken);
    }

    public void clear(){
        authDAO.clear();
    }

}