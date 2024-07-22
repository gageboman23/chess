package service;

import model.AuthData;

import java.util.UUID;

public class AuthService {

    public AuthData createAuth(String username) {// create a new auth token and attach it to the username in the server and then return AuthData (username, authToken)
        String authToken = UUID.randomUUID().toString();

        // not sure if this is stored in the same database or if i need to create a separate auth database... Scratch that, have not used DAOs at all. I think thatll go here anyway.

    }
}
