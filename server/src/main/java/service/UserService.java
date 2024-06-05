package service;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

public class UserService {
    public UserDAO userDAO;
    // not quite sure if this is complete but I think its a start

    public AuthData register(UserData user) throws DataAccessException {
        String password = user.password();
        if (password == null || password.isEmpty()) {
            throw new DataAccessException("Password cannot be empty");
        }
        UserData newUser = new UserData(user.username(), password, user.email());
        userDAO.createUser(newUser);
        return new AuthData(newUser.username(), newUser.password());
    }

    public AuthData login(UserData user) throws DataAccessException {
        String username = user.username();
        String password = user.password();
        UserData existingUser = userDAO.getUser(username);
        if (existingUser == null) {
            throw new DataAccessException("Username not found");
        } else if (password.equals(existingUser.password())) {
            return new AuthData(user.username(), user.password());
        } else {
            throw new DataAccessException("Wrong password");
        }
    }

    public void logout(UserData user) throws DataAccessException {
        userDAO.clear();
    }
}
