package server;

import com.google.gson.Gson;
import exception.ResponseException;
import request.*;
import result.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {serverUrl = url;}

    public RegisterResult register(RegisterRequest registerRequest) throws ResponseException {
        var path = "/user";
        return this.makeRequest("POST", path, registerRequest, RegisterResult.class, null);
    }

    public LoginResult login(LoginRequest loginRequest) throws ResponseException {
        var path = "/session";
        return this.makeRequest("POST", path, loginRequest, LoginResult.class, null);
    }

    public LogoutResult logout(LogoutRequest logoutRequest) throws ResponseException {
        var path = "/session";
        return this.makeRequest("DELETE", path, logoutRequest, LogoutResult.class, null);
    }

    public CreateGameResult createGame(CreateGameRequest createGameRequest, String authToken) throws ResponseException {
        var path = "/game";
        return this.makeRequest("POST", path, createGameRequest, CreateGameResult.class, authToken);
    }

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest, String authToken) throws ResponseException {
        var path = "/game";
        return this.makeRequest("PUT", path, joinGameRequest, JoinGameResult.class, authToken);
    }

    public ListGamesResult listGames(String authToken) throws ResponseException {
        var path = "/game";
        return this.makeRequest("GET", path, null, ListGamesResult.class, authToken);
    }

    public void clear(String authToken) throws ResponseException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null, authToken);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            addHeaders(http, request, authToken); //added
            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (ResponseException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ResponseException(500, ex.getMessage());
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

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            try (InputStream respErr = http.getErrorStream()) {
                if (respErr != null) {
                    throw ResponseException.fromJson(respErr);
                }
            }

            throw new ResponseException(status, "other failure: " + status);
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

    private void addHeaders(HttpURLConnection http, Object request, String authToken) {
        // If there's a request body
        if (request != null) {
            http.setRequestProperty("Content-Type", "application/json");
        }
        // If the request is either logout or listGames
        if (request instanceof LogoutRequest(String auth)) {
            http.setRequestProperty("Authorization", auth);
        }
        if (request instanceof ListGamesRequest(String auth)) {
            http.setRequestProperty("Authorization", auth);
        }
        // If request is createGame or joinGame
        if (authToken != null) {
            if (request instanceof CreateGameRequest createGameRequest) {
                http.setRequestProperty("Authorization", authToken);
            }
            if (request instanceof JoinGameRequest joinGameRequest) {
                http.setRequestProperty("Authorization", authToken);
            }
        }
        // If listGames
        if (request == null && authToken != null) {
            http.setRequestProperty("Authorization", authToken);
        }
    }

}

