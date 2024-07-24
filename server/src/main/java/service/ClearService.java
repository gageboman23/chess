package service;

import dataaccess.*;

public class ClearService {

    UserDAOBase userDAO = new LocalUserDAO();
    AuthDAOBase authDAO = new LocalAuthDAO();
    GameDAOBase gameDAO = new LocalGameDAO();

    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }
}
