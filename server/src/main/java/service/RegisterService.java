package service;

import dataaccess.AuthDAOBase;
import dataaccess.LocalUserDAO;
import dataaccess.UserDAOBase;
import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterService {

    public UserData getUser(String username) {

    }

    public void createUser(UserData userData) {
        String username = userData.username();
        if (!userDatabase.containsKey(username)) {
            userDatabase.put(username, userData);
        } else {
            throw new RuntimeException("Username already exists");
        }
    }

    public AuthData createAuth(String username) {// create a new auth token and attach it to the username in the server and then return AuthData (username, authToken)
        String authToken = UUID.randomUUID().toString();

        // not sure if this is stored in the same database or if i need to create a separate auth database... Scratch that, have not used DAOs at all. I think thatll go here anyway.

    }


}
