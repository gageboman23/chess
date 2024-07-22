package service;

import dataaccess.AuthDAOBase;
import dataaccess.DataAccessException;
import dataaccess.LocalAuthDAO;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class AuthService {

    AuthDAOBase authDAO = new LocalAuthDAO();

    public AuthData createAuth(String username) throws DataAccessException {
        return authDAO.createAuth(username);
    }

    public boolean verifyAuth(AuthData authData) throws DataAccessException{
        String savedAuth = authDAO.getAuth(authData.username()).authToken();
        if (authData.authToken() == savedAuth){
            return true;
        }
        return false;
    }

    public void deleteAuth(AuthData authData) throws DataAccessException{
        authDAO.deleteAuth(authData.username());
    }
}
