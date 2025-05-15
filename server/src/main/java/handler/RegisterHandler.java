package handler;

import chess.ChessGame;
import com.google.gson.Gson;
import request.LoginRequest;
import request.RegisterRequest;

public class RegisterHandler {

    public Object handleRequest(Object req, Object res) {//may want to try generics: Class<T> responseClass

        var serializer = new Gson();
        var game = new ChessGame();

        // serialize to JSON
        var json = serializer.toJson(game);

        //deserialize back to ChessGame
        game = serializer.fromJson(json, ChessGame.class);

        //RegisterRequest request = (RegisterRequest)gson.fromJson(req, LoginRequest.class);

        return null;
    }
}
