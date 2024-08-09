package client;

import com.google.gson.Gson;
import model.UserData;
import responses.LoginResponse;
import responses.RegisterResponse;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;
    private String authToken;

    public ServerFacade(int urlPort) {
        String url = "http://localhost:";
        url += String.valueOf(urlPort);
        serverUrl = url;
    }

    public String register(String username, String password, String email) throws Exception {
        var path = "/user";
        UserData user = new UserData(username, password, email);
        RegisterResponse res = this.makeRequest("POST", path, user, RegisterResponse.class);
        authToken = res.authToken();
        return authToken;
    }

    public String logIn(String username, String password) throws Exception{
        var path = "/session";
        UserData user = new UserData(username, password, null);
        LoginResponse res = this.makeRequest("POST", path, user, LoginResponse.class);
        authToken = res.authToken();
        return authToken;
    }

    public void logout() throws Exception {
        var path = "/session";
        this.makeRequest("DELETE", path, null, null);
        authToken = null;
    }


    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws Exception {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            if (authToken != null) {
                http.addRequestProperty("Authorization", authToken);
            }
            if (request != null){
                http.setDoOutput(true);
            }
            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            var res = readBody(http, responseClass);
            return res;
        } catch (Exception ex) {

            throw new Exception(ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, Exception {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new Exception("Error: Not successful");
        }
    }


    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);

                }
            }
        }
        return response;
    }

    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

}
