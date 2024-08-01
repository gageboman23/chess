package service;

import dataaccess.*;

public class ClearService {

    UserDAOBase userDAO = new SQLUserDAO();
    AuthDAOBase authDAO = new SQLAuthDAO();
    GameDAOBase gameDAO = new SQLGameDAO();

    public void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
    }
}
