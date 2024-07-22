package service;

import model.UserData;

public class UserService {

    public void createUser(UserData userData) {
        String username = userData.username();
        if (!userDatabase.containsKey(username)) {
            userDatabase.put(username, userData);
        } else {
            throw new RuntimeException("Username already exists");
        }
    }

    public UserData getUser(String username) {

    }

}
