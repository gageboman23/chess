package service;

import dataaccess.DataAccessException;
import dataaccess.MemGameDAO;
import dataaccess.MemUserDAO;
import dataaccess.UserDAO;
import model.AuthData;
import model.UserData;

import java.util.Objects;

public class UserService {
    public static UserDAO userDAO;

    public UserService() throws DataAccessException {
        try {
            userDAO = new MemUserDAO();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage());
        }
    }


    public void register(UserData user) throws DataAccessException {
        String password = user.password();
        if (password == null || password.isEmpty()) {
            throw new DataAccessException("Error: bad request");
        }
        UserData newUser = new UserData(user.username(), password, user.email());
        userDAO.createUser(newUser);
        new AuthData(newUser.username(), newUser.password());
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

    public boolean verifyUser(UserData userData) throws DataAccessException {
        String username = userData.username();
        String password = userData.password();
        UserData existingUser = userDAO.getUser(username);
        if (existingUser == null) {
            return false;
        } else if (Objects.equals(password, existingUser.password())) {
            return true;
        } else {
            throw new DataAccessException("Error: unauthorized");
        }

    }
    public void clear(){
        userDAO.clear();
    }
}
