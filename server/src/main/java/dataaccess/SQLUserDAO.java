package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

public class SQLUserDAO implements UserDAO{
    DAOUtils utils;

    public SQLUserDAO() throws DataAccessException {
        utils = new DAOUtils();
        utils.configureDatabase("user");
    }

    @Override
    public UserData createUser(String username, String password, String email) throws DataAccessException {
        if (getUser(username) == null) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
            var id = utils.executeUpdate(statement, username, hashedPassword, email);
            return new UserData(username, password, email);
        }
        return null;
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM user WHERE username=?";
            try (var ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String uname = rs.getString("username");
                        String password = rs.getString("password");
                        String email = rs.getString("email");
                        return new UserData(uname, password, email);
                    }
                }
            }
        } catch (Exception e) {
            throw new DataAccessException(500, "Internal Server Error: Could not access user database.");
        }
        return null;
    }

    @Override
    public void clearUsers() throws DataAccessException {
        var statement = "TRUNCATE user";
        utils.executeUpdate(statement);
    }

}
