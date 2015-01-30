package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;

import static org.junit.Assert.*;

public class AbstractManagerTest {
    protected Connection conn;

    @Before
    public void setUp() throws Exception {
        conn = DatabaseManagerTest.getTestTable();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }
}