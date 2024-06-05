package ResponseTypes;

public class RegisterResponse extends Response{

    private String username;
    public String authToken;

    public RegisterResponse(String username, String authToken){
        this.username = username;
        this.authToken = authToken;
    }




}