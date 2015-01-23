package i5.las2peer.services.servicePackage.Resources;

import java.sql.Connection;

/**
 * Created by Marv on 12.11.2014.
 */
public abstract class AbstractResource {

    Connection conn;


    public AbstractResource(Connection conn) {
        this.conn = conn;
    }

    public Connection setConnection(Connection newConn) {
        Connection oldConn = conn;
        conn = newConn;
        return oldConn;
    }
}
