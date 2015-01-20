package i5.las2peer.services.servicePackage.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DatabaseManagerTest {

    private static BasicDataSource dataSource = null;

    private static void createFreshLocalTables() throws SQLException {
        try {
            //String executeSqlCommand = "mysql -h 127.0.0.1 -P 3306 -u root --password= ugnm1415g2 < /tmp/file.sql";
            //System.out.println(executeSqlCommand);

            ProcessBuilder ps=new ProcessBuilder("mysql","--host=127.0.0.1","--port=3306","--user=root","--password=","ugnm1415g2");
            ps.redirectErrorStream(true);
            ps.redirectInput(new File("/tmp/file.sql"));

            Process pr = ps.start();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = pr.waitFor();

            in.close();

            //executeSqlCommand = "echo hey > unittest_db.sql";
            System.out.println("Exited with error code " + exitVal);
            assertEquals(exitVal, 0);

        } catch (Exception e) {
            throw new SQLException(e.toString());
        }
    }

    public static Connection getTestTable() throws SQLException {
        if( dataSource == null ) {
            dataSource = new BasicDataSource();
            dataSource.setDefaultAutoCommit(true);
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUsername("root");
            dataSource.setPassword("");
            dataSource.setUrl("jdbc:mysql://localhost:3306/ugnm1415g2");
            dataSource.setValidationQuery("SELECT 1");
            dataSource.setDefaultQueryTimeout(1000);
            dataSource.setMaxConnLifetimeMillis(100000);
        }

        createFreshLocalTables();

        return dataSource.getConnection();
    }


    @Test
    public void connectionToLocalDatabase() throws Exception {
        Connection conn = getTestTable();
    }
}