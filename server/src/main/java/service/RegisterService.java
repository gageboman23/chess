package service;

import dataaccess.*;
import model.AuthData;
import spark.*;
import model.UserData;

import java.util.UUID;

public class RegisterService {

    UserDAOBase userDAO = new LocalUserDAO();
    AuthDAOBase authDAO = new LocalAuthDAO();
    Response res;


    public Object register(UserData userData) throws DataAccessException {
        if (userData.username() == null || userData.email() == null || userData.password() == null){
            res.status(400);
            res.body("");
            return res;
        }

        userDAO.insertUser(userData);
        authDAO.insertAuth(createAuth(userData));
        res.status(200);
        return
    }

    private AuthData createAuth(UserData userData){
        String authToken = UUID.randomUUID().toString();
        return new AuthData(authToken, userData.username());
    }





}
 // one pub method "register"

// makes calls to all DataAccess methods necessary
