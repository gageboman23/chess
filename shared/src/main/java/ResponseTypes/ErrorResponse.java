package ResponseTypes;

public class ErrorResponse extends Response{

    private String message;

    public ErrorResponse(String message){
        this.message = message;
    }

}
