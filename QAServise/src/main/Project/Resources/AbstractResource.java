package Project.Resources;

import i5.las2peer.services.servicePackage.database.DatabaseManager;

import java.sql.Connection;

/**
 * Created by Marv on 12.11.2014.
 */
public abstract class AbstractResource {

    Connection conn;


    public AbstractResource(Connection conn) {
        this.conn = conn;
    }

}
