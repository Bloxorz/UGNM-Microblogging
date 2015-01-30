package i5.las2peer.services.servicePackage.Manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.PostDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.General.Rating;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionManagerTest extends AbstractManagerTest {

    private static QuestionManager manager;
    private static QuestionDTO[] testDTOs;

    @BeforeClass
    public static void initClass() throws ParseException {
        manager = new QuestionManager();
        testDTOs = DatabaseManagerTest.getTestQuestions();
    }

    @Test
    public void testGetQuestionList() throws Exception {
        System.out.println(manager.getQuestionList(conn));
        assertArrayEquals(testDTOs, manager.getQuestionList(conn).toArray());
    }

    @Test
    public void testAddQuestion() throws Exception {
        QuestionDTO dto = new QuestionDTO(0, DatabaseManagerTest.getDummyDate(), "How are you?", 1, Arrays.asList(DatabaseManagerTest.getTestHashtags(2,3)));
        long newId = manager.addQuestion(conn, dto);
        assertEquals(newId, 9);
        QuestionDTO getDto = manager.getQuestion(conn, 9);
        assertEquals(dto, getDto);
        assertEquals( new UserManager().getElo(conn, 1), 9 );
        for( int i=1; i<10; i++)
            manager.addQuestion(conn, dto);
        try {
            manager.addQuestion(conn, dto);
            fail("Should throw exception when elo is too low to ask a question.");
        } catch (CantInsertException e) {
            // OK
        }
        assertEquals( new UserManager().getElo(conn, 1), 0 );
    }


    @Test
    public void testGetQuestion() throws Exception {
        QuestionDTO dto = manager.getQuestion(conn, 1);
        assertEquals(testDTOs[0], dto);
    }

    @Test
    public void testDeleteQuestion() throws Exception {
        manager.deleteQuestion(conn, 4);
        try {
            manager.getQuestion(conn, 4);
            fail("an excepton should be thrown");
        } catch (CantFindException e) {
            // OK
        } catch (Exception e) {
            fail("wrong exception thrown");
        }

        assertArrayEquals( manager.getAnswersToQuestion(conn, 4).toArray(), new AnswerDTO[]{} );
    }

    @Test
    public void testGetAnswersToQuestion() throws Exception {
        assertArrayEquals(
                DatabaseManagerTest.getTestAnswers(2,3,4),
                manager.getAnswersToQuestion(conn, 4).toArray()
        );
    }

    @Test
    public void testGetQuestionWithAnswers() throws Exception {
        Gson g = new Gson();
        JsonObject jo = new JsonObject();
        jo.add("question", g.toJsonTree(DatabaseManagerTest.getTestQuestions()[2]));
        jo.add("answers", g.toJsonTree(DatabaseManagerTest.getTestAnswers(2,3,4)));

        assertEquals(jo.toString(), manager.getQuestionWithAnswers(conn, 4).toString());
    }

    @Test
    public void testAddAnswerToQuestion() throws Exception {
        AnswerDTO dto = new AnswerDTO(-1, DatabaseManagerTest.getDummyDate(), "Geh in den Vorkurs!", 5, 0, 1);
        manager.addAnswerToQuestion(conn, dto);
        Object[] result = manager.getAnswersToQuestion(conn, 1).toArray();
        AnswerDTO[] expected = new AnswerDTO[] {dto};
        assertArrayEquals( expected, result );
    }

    @Test
    public void testGetHashtagsToQuestion() throws Exception {
        assertArrayEquals(
                DatabaseManagerTest.getTestHashtags(0,2),
                manager.getHashtagsToQuestion(conn, 1).toArray()
        );
    }

    @Test
    public void testGetBookmarkUsersToQuestion() throws Exception {
        assertArrayEquals(
                DatabaseManagerTest.getTestUsers(1,3),
                manager.getBookmarkUsersToQuestion(conn, 2).toArray()
        );
    }

    @Test
    public void testGetBookmarkCount() throws Exception {
        assertEquals(2, manager.getBookmarkCount(conn, 2));
    }
}