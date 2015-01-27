package i5.las2peer.services.servicePackage.database;

import com.google.gson.Gson;
import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.General.Rating;
import i5.las2peer.services.servicePackage.ServiceClass;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DatabaseManagerTest {

    private static BasicDataSource dataSource = null;

    private static void createFreshLocalTables() throws SQLException {
        try {
            //String executeSqlCommand = "mysql -h 127.0.0.1 -P 3306 -u root --password= ugnm1415g2 < /tmp/file.sql";
            //System.out.println(executeSqlCommand);

            ProcessBuilder ps=new ProcessBuilder("mysql","--host=127.0.0.1","--port=3306","--user=root","--password=","ugnm1415g2");

            ps.redirectErrorStream(true);
            ps.redirectInput(new File("./src/test/i5/las2peer/services/servicePackage/database/testdatabase.sql"));

            Process pr = ps.start();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = pr.waitFor();

            in.close();

            System.out.println("Exited with error code " + exitVal);
            assertEquals(exitVal, 0);

        } catch (Exception e) {
            throw new SQLException(e.toString());
        }
    }

    public static HashtagDTO[] getTestHashtags() {
        return new HashtagDTO[] {
                new HashtagDTO(1,"Java"),
                new HashtagDTO(2,"Assembler"),
                new HashtagDTO(3,"For-Loop"),
                new HashtagDTO(4,"All"),
                new HashtagDTO(5,"Analysis"),
                new HashtagDTO(6,"Polynome")
        };
    }

    public static ExpertiseDTO[] getTestExpertises() {
        return new ExpertiseDTO[] {
                new ExpertiseDTO(1,"Lineare Algebra"),
                new ExpertiseDTO(2,"Analysis für Informatiker"),
                new ExpertiseDTO(3,"Einführung in die Programmierung"),
                new ExpertiseDTO(4,"Praktikum Unternehmensgründung und Neue Medie")
        };
    }

    public static QuestionDTO[] getTestQuestions() throws ParseException {
        Date dummyDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2000-01-01 00:00:00");
        HashtagDTO[] hs = getTestHashtags();
        List<HashtagDTO> hashtags1 = Arrays.asList(new HashtagDTO[]{getTestHashtags()[0], getTestHashtags()[2]});
        return new QuestionDTO[] {
                new QuestionDTO(1, dummyDate, "How do I write a for-loop?", 1, Arrays.asList(new HashtagDTO[]{hs[0], hs[2]})), // #For-Loop #Java
                new QuestionDTO(2, dummyDate, "Where can I find the toilet?", 4, Arrays.asList(new HashtagDTO[]{hs[3]})), // #All
                new QuestionDTO(4, dummyDate, "How does the JFrame-constructor work?", 2, Arrays.asList(new HashtagDTO[]{hs[0]})) // #Java
        };
    }

    public static AnswerDTO[] getTestAnswers() throws ParseException {
        Date dummyDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2000-01-01 00:00:00");
        return new AnswerDTO[] {
                new AnswerDTO(3, dummyDate, "In the building E2, first floor.", 2, 100, 2),
                new AnswerDTO(5, dummyDate, "I think he is right", 5, 0, 2),
                new AnswerDTO(6, dummyDate, "You should google for it.", 3, 0, 4),
                new AnswerDTO(7, dummyDate, "I want to recherche it...", 5, 0, 4),
                new AnswerDTO(8, dummyDate, "I already googled, but couldn'nt find anything :(", 2, 0, 4)
        };
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

    @Test
    public void testDatabaseConfiguration() throws Exception {
        ServiceClass s = new ServiceClass();
        Class c = ServiceClass.class;
        Field f;
        f = c.getDeclaredField("jdbcDriverClassName"); f.setAccessible(true);
        String jdbcDriverClassName = (String)f.get(s);
        f = c.getDeclaredField("jdbcLogin"); f.setAccessible(true);
        String jdbcLogin = (String)f.get(s);
        f = c.getDeclaredField("jdbcPass"); f.setAccessible(true);
        String jdbcPass = (String)f.get(s);
        f = c.getDeclaredField("jdbcUrl"); f.setAccessible(true);
        String jdbcUrl = (String)f.get(s);
        f = c.getDeclaredField("jdbcSchema"); f.setAccessible(true);
        String jdbcSchema = (String)f.get(s);

        DatabaseManager dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl,jdbcSchema);
        assertNotEquals(null, dbm.getConnection());
    }

}



