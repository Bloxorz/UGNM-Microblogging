package Project.Manager;

import Project.DTO.UserDTO;
import i5.las2peer.services.servicePackage.database.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Marv on 05.11.2014.
 * this class handles all DB-Server communication, binding different Manager classes together
 */
public class ManagerFacade {

    //add your managers here
    protected UserManager usermng = new UserManager();

    private Connection conn;
    //Handles DB-Access ( connections, etc.)

    public ManagerFacade(Connection conn) {
        this.conn = conn;
    }

    public UserDTO getUser(Connection conn, long userId) throws SQLException {
        return usermng.getUser(conn,userId);
    }



}
