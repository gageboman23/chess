package service;

import responses.ErrorResponse;
import dataaccess.*;

public class LogoutService {

    AuthDAOBase authDAO = new SQLAuthDAO();


    public Object logout(String authToken) throws DataAccessException {
        if (authToken == null || authDAO.getAuth(authToken) == null) {
            return new ErrorResponse("Error: unauthorized");
        }
        authDAO.deleteAuth(authToken);
        return null;
    }
}
