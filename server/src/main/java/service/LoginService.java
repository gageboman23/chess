package service;

import dataaccess.*;
import model.AuthData;
import model.UserData;
import Requests.LoginRequest;
import Responses.ErrorResponse;
import Responses.LoginResponse;

import java.util.Objects;
import java.util.UUID;

public class LoginService {

    UserDAOBase userDAO = new LocalUserDAO();
    AuthDAOBase authDAO = new LocalAuthDAO();


    public Object login(LoginRequest loginRequest) throws DataAccessException {
        UserData serverData = userDAO.getUser(loginRequest.username());
        if (serverData != null && Objects.equals(serverData.password(), loginRequest.password())){
            authDAO.insertAuth(createAuth(serverData));
            return new LoginResponse(serverData.username(), createAuth(serverData).authToken());
        } else {
            return new ErrorResponse("Error: unauthorized");
        }
    }

    private AuthData createAuth(UserData userData){
        String authToken = UUID.randomUUID().toString();
        return new AuthData(authToken, userData.username());
    }
}
