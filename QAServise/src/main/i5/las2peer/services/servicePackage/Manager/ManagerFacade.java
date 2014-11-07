package i5.las2peer.services.servicePackage.Manager;

import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.JsonParseException;

/**
 * Created by Marv on 05.11.2014.
 * this class handles all DB-Server communication, binding different Manager classes together
 */
public class ManagerFacade {

    //add your managers here
    protected UserManager usermng = new UserManager();
    protected HashtagManager hashtagmng = new HashtagManager();

    private Connection conn;
    //Handles DB-Access ( connections, etc.)

    public ManagerFacade(Connection conn) {
        this.conn = conn;
    }

    public String getUser(Connection conn, long userId) throws SQLException {
        return usermng.getUser(conn,userId);
    }
    public String editUser(Connection conn, long userId, String content) throws SQLException, JsonParseException {
        return usermng.editUser(conn,userId,content);
    }
    /*public String deleteUser(Connection conn, long userId) throws SQLException {
        return usermng.deleteUser(conn,userId);
    }*/
    public String getHashtag(Connection conn, long hashtagId) throws SQLException {
        return hashtagmng.getHashtag(conn, hashtagId);
    }
    public String createHashtag(Connection conn, String content) throws SQLException {
        return hashtagmng.createHashtag(conn, content);
    }


}
