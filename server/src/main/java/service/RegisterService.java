package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import server.Responses.ErrorResponse;
import server.Responses.RegisterResponse;

import java.util.UUID;

public class RegisterService {

    UserDAOBase userDAO = new LocalUserDAO();
    AuthDAOBase authDAO = new LocalAuthDAO();


    public Object register(UserData userData) throws DataAccessException {

        if (userData.username() == null || userData.email() == null || userData.password() == null){
        return new ErrorResponse("Error: bad request");
        }

        if (userDAO.getUser(userData.username()) != null){
            return new ErrorResponse("Error: already taken");
        }

        userDAO.insertUser(userData);
        AuthData authData = createAuth(userData);
        authDAO.insertAuth(authData);
        return new RegisterResponse(authData.username(), authData.authToken());
    }

    private AuthData createAuth(UserData userData){
        String authToken = UUID.randomUUID().toString();
        return new AuthData(authToken, userData.username());
    }





}
 // one pub method "register"

// makes calls to all DataAccess methods necessary
