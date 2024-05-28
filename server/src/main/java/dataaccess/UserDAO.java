package dataaccess;
import model.UserData;

public interface UserDAO {
    void createUser(UserData u) throws Exception;
    UserData getUser(String username) throws DataAccessException;
    void clear();
}

