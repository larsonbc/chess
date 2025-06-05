package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class SQLGameDAO implements GameDAO{

    DAOUtils utils;

    public SQLGameDAO() throws DataAccessException {
        utils = new DAOUtils();
        utils.configureDatabase("game");
    }

    @Override
    public GameData createGame(String gameName) throws DataAccessException {
        try {
            var statement = "INSERT INTO game (game_name, game) VALUES (?, ?)";
            ChessGame newGame = new ChessGame();
            var json = new Gson().toJson(newGame);
            var id = utils.executeUpdate(statement, gameName, json);
            return new GameData(id, null, null, gameName, newGame);
        } catch (Exception e) {
            throw new DataAccessException(500, "Internal Server Error: Could not create game.");
        }
    }

    @Override
    public GameData getGame(int gameID) {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game WHERE id=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public ArrayList<GameData> listGames() {
        var result = new ArrayList<GameData>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM game";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGame(rs));
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    @Override
    public boolean updateGame(String playerColor, int gameID, String username) throws DataAccessException {
        GameData oldGame = getGame(gameID);
        if (oldGame == null) {
            throw new DataAccessException(400, "Error: bad request");
        }
        if ((oldGame.whiteUsername() != null && Objects.equals(playerColor, "WHITE")) ||
                (oldGame.blackUsername() != null && Objects.equals(playerColor, "BLACK"))) {
            throw new DataAccessException(403, "Error: already taken");
        }
        var statement = "";
        if (Objects.equals(playerColor, "WHITE")) {
            statement = "UPDATE game SET white_username = ? WHERE id = ?";
        } else if (Objects.equals(playerColor, "BLACK")){
            statement = "UPDATE game SET black_username = ? WHERE id = ?";
        } else {
            throw new DataAccessException(400, "Error: bad request");
        }

        try (var conn = DatabaseManager.getConnection()) {
            var ps = conn.prepareStatement(statement);
            ps.setString(1, username);
            ps.setInt(2, gameID);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE game";
        utils.executeUpdate(statement);
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        var id = rs.getInt("id");
        var whiteUsername = rs.getString("white_username");
        var blackUsername = rs.getString("black_username");
        var gameName = rs.getString("game_name");
        var json = rs.getString("game");
        var gameState = new Gson().fromJson(json, ChessGame.class);
        return new GameData(id, whiteUsername, blackUsername, gameName, gameState);
    }

    public void saveGame(int gameID, ChessGame newGameState) throws DataAccessException {
        GameData oldGame = getGame(gameID);
        if (oldGame == null) {
            throw new DataAccessException(400, "Error: bad request");
        }
        String json = new Gson().toJson(newGameState);
        var statement = "UPDATE game SET game = ? WHERE id = ?";
        try (var conn = DatabaseManager.getConnection()) {
            var ps = conn.prepareStatement(statement);
            ps.setString(1, json);
            ps.setInt(2, gameID);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DataAccessException(400, "Error: Game not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

}
