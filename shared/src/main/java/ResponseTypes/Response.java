package ResponseTypes;

import com.google.gson.Gson;

public class Response {

    public String toJSon() {
        var serializer = new Gson();
        return serializer.toJson(this);
    }
}