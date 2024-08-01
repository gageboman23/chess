package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;
import responses.ErrorResponse;
import responses.LoginResponse;
import java.util.UUID;

public class LoginService {

    UserDAOBase userDAO = new SQLUserDAO();
    AuthDAOBase authDAO = new SQLAuthDAO();


    public Object login(LoginRequest loginRequest) throws DataAccessException {
        UserData serverData = userDAO.getUser(loginRequest.username());
        if (serverData != null) {
            if (BCrypt.checkpw(loginRequest.password(), serverData.password())) {
                AuthData newAuth = createAuth(loginRequest.username());
                authDAO.insertAuth(newAuth);
                return new LoginResponse(serverData.username(), newAuth.authToken());
            }
        } else {
            return new ErrorResponse("Error: unauthorized");
        }
        return new ErrorResponse("Error: unauthorized");
    }

    private AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        return new AuthData(authToken, username);
    }
}


