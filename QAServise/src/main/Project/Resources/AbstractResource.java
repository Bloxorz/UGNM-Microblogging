package Project.Resources;

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
