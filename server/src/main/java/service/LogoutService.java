package service;

import Responses.ErrorResponse;
import dataaccess.*;

public class LogoutService {

    AuthDAOBase authDAO = new LocalAuthDAO();


    public Object logout(String authToken) throws DataAccessException {
        if (authToken == null || authDAO.getAuth(authToken) == null) {
            return new ErrorResponse("Error: unauthorized");
        }
        authDAO.deleteAuth(authToken);
        return null;
    }
}
