package dataaccess;

import model.AuthData;

import java.util.UUID;

public class SQLAuthDAO implements AuthDAO{

    DAOUtils utils;

    public SQLAuthDAO() throws DataAccessException {
        utils = new DAOUtils();
        utils.configureDatabase("auth");
    }

    @Override
    public AuthData createAuthToken(String username) throws DataAccessException {
        var statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        String newToken = UUID.randomUUID().toString();
        var id = utils.executeUpdate(statement, newToken, username);
        return new AuthData(newToken, username);
    }

    @Override
    public String getAuthToken(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM auth WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("authToken");
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(500, "Error: Unable to connect to SQL Auth DAO");
        }
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM auth WHERE authToken=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String token = rs.getString("authToken");
                        String username = rs.getString("username");
                        return new AuthData(authToken, username);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(500, "Error: Unable to connect to SQL Auth DAO");
        }
        return null;
    }

    @Override
    public boolean deleteAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken=?";
        utils.executeUpdate(statement, authToken);
        return true;
    }

    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auth";
        utils.executeUpdate(statement);
    }

}
