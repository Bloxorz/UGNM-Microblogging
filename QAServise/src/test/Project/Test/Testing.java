package Project.Test;

import Project.Resources.QuestionResource;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Created by Marv on 12.11.2014.
 */
public class Testing {

    private static String jdbcDriverClassName= "com.mysql.jdbc.Driver";
    private static String jdbcUrl= "jdbc:mysql://buche.informatik.rwth-aachen.de:3306/";
    private static String jdbcSchema="ugnm1415g2";
    private static String jdbcLogin="ugnm1415g2";
    private static String jdbcPass="bf3244PRAN";
    private static DatabaseManager dbm;
    public static void main(String[] args) throws SQLException {

        // instantiate a database manager to handle database connection pooling and credentials
        dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl, jdbcSchema);
        QuestionResource q = new QuestionResource(dbm.getConnection());

        q.getQuestionCollection("");
    }
}
