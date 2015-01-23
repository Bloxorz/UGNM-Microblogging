package i5.las2peer.services.servicePackage.Resources;

import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class QuestionResourceTest {

    private static QuestionResource resource;

    @BeforeClass
    public static void initClass() {
        resource = new QuestionResource(null);
    }

    @Before
    public void setUp() throws Exception {
        Connection oldConn = resource.setConnection(DatabaseManagerTest.getTestTable());
        if(oldConn != null) oldConn.close();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetQuestionCollection() throws Exception {

    }

    @Test
    public void testAddQuestion() throws Exception {

    }

    @Test
    public void testGetQuestion() throws Exception {

    }

    @Test
    public void testEditQuestion() throws Exception {

    }

    @Test
    public void testDeleteQuestion() throws Exception {

    }

    @Test
    public void testGetAnswersToQuestion() throws Exception {

    }

    @Test
    public void testGetQuestionAndAnswers() throws Exception {
        System.out.println(resource.getQuestionAndAnswers(4).getResult());
    }

    @Test
    public void testAddAnswerToQuestion() throws Exception {

    }

    @Test
    public void testGetBookmarkUsersToQuestion() throws Exception {

    }
}