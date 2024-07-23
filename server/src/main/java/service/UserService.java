package service;

import dataaccess.DataAccessException;
import dataaccess.LocalUserDAO;
import dataaccess.UserDAOBase;
import model.UserData;

public class UserService {

    UserDAOBase userDAO = new LocalUserDAO();

    public void createUser(UserData userData) throws DataAccessException {
        userDAO.createUser(userData);
    }

    public UserData getUser(String username) throws DataAccessException{
        return userDAO.getUser(username);
    }

    public boolean verifyPassword(UserData userdata) throws DataAccessException{
        String savedPassword = userDAO.getUser(userdata.username()).password();
        if (savedPassword == userdata.password()){
            return true;
        }
        return fa
    }

}
