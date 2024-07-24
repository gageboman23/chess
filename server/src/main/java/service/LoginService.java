package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import requests.LoginRequest;
import responses.ErrorResponse;
import responses.LoginResponse;
import java.util.Objects;
import java.util.UUID;

public class LoginService {

    UserDAOBase userDAO = new LocalUserDAO();
    AuthDAOBase authDAO = new LocalAuthDAO();


    public Object login(LoginRequest loginRequest) throws DataAccessException {
        UserData serverData = userDAO.getUser(loginRequest.username());
        if (serverData != null && Objects.equals(serverData.password(), loginRequest.password())) {
            AuthData newAuth = createAuth(loginRequest.username());
            authDAO.insertAuth(newAuth);
            return new LoginResponse(serverData.username(), newAuth.authToken());
        } else {
            return new ErrorResponse("Error: unauthorized");
        }
    }

    private AuthData createAuth(String username) {
        String authToken = UUID.randomUUID().toString();
        return new AuthData(authToken, username);
    }
}
