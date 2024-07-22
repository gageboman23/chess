package handlers;

import com.google.gson.Gson;
import spark.*;
import service.*;

public class BaseHandler {

    UserService userService = new UserService();
    AuthService authService = new AuthService();
    Gson serializer = new Gson();

}
// to & from Json methods here?