package dataaccess;

import model.UserData;

public class MemUserDAO implements UserDAO {
    @Override
    public void createUser(UserData u) throws Exception {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void clear() {

    }
}
