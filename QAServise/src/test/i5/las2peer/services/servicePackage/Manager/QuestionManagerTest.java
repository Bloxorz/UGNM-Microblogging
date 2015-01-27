package i5.las2peer.services.servicePackage.Manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.PostDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionManagerTest {

    private static QuestionManager manager;
    private static QuestionDTO[] testDTOs;

    private Connection conn;

    @BeforeClass
    public static void initClass() throws ParseException {
        manager = new QuestionManager();
        testDTOs = DatabaseManagerTest.getTestQuestions();
    }

    @Before
    public void setUp() throws Exception {
        conn = DatabaseManagerTest.getTestTable();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void testGetQuestionList() throws Exception {
        System.out.println(manager.getQuestionList(conn));
        assertArrayEquals(testDTOs, manager.getQuestionList(conn).toArray());
    }

    /* without hashtags
    @Test
    public void testAddQuestion() throws Exception {
        QuestionDTO dto = new QuestionDTO(0, null, "How are you?", 5);
        long newId = manager.addQuestion(conn, dto);
        assertEquals(newId, 9);
        QuestionDTO getDto = manager.getQuestion(conn, 9);
        assertEquals( getDto.getId(), dto.getId() );
        assertEquals( getDto.getText(), dto.getText() );
        assertEquals( getDto.getUserId(), dto.getUserId() );
    }*/
    @Test
    public void testAddQuestion() throws Exception {
        List<HashtagDTO> hashtags = new LinkedList<HashtagDTO>();
        hashtags.add(DatabaseManagerTest.getTestHashtags()[2]);
        hashtags.add(DatabaseManagerTest.getTestHashtags()[3]);
        QuestionDTO dto = new QuestionDTO(0, null, "How are you?", 1, hashtags);
        long newId = manager.addQuestion(conn, dto);
        assertEquals(newId, 9);
        QuestionDTO getDto = manager.getQuestion(conn, 9);
        System.out.println(getDto);
        assertEquals( getDto.getIdPost(), dto.getIdPost() );
        assertEquals(getDto.getText(), dto.getText());
        assertEquals( getDto.getIdUser(), dto.getIdUser() );
        assertArrayEquals( getDto.getHashtags().toArray(), dto.getHashtags().toArray() );
    }


    @Test
    public void testGetQuestion() throws Exception {
        QuestionDTO dto = manager.getQuestion(conn, 1);
        assertEquals(testDTOs[0], dto);
    }

    @Test
    public void testEditQuestion() throws Exception {
      /*  ResultSetHandler<Object> h = new BeanHandler<Object>(Object.class);
        QueryRunner qr = new QueryRunner();
        Object result = qr.query(conn, "SELECT (idHashtag) FROM Hashtag WHERE text=?", h, "Java");
        System.out.println(Object.class.);
        System.out.println(Object.class.getDeclaredField("idHashtag").getInt(result));*/
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
        AnswerDTO[] expected = new AnswerDTO[] {
                DatabaseManagerTest.getTestAnswers()[2],
                DatabaseManagerTest.getTestAnswers()[3],
                DatabaseManagerTest.getTestAnswers()[4]
        };
        System.out.println( manager.getAnswersToQuestion(conn, 4));
        assertArrayEquals( expected, manager.getAnswersToQuestion(conn, 4).toArray() );
    }

    @Test
    public void testGetQuestionWithAnswers() throws Exception {
        Gson g = new Gson();
        JsonObject jo = new JsonObject();
        AnswerDTO[] expectedAnswers = new AnswerDTO[] {
                DatabaseManagerTest.getTestAnswers()[2],
                DatabaseManagerTest.getTestAnswers()[3],
                DatabaseManagerTest.getTestAnswers()[4]
        };
        jo.add("question", g.toJsonTree(DatabaseManagerTest.getTestQuestions()[2]));
        jo.add("answers", g.toJsonTree(expectedAnswers));

        assertEquals(jo.toString(), manager.getQuestionWithAnswers(conn, 4).toString());
    }

    @Test
    public void testAddAnswerToQuestion() throws Exception {
        AnswerDTO dto = new AnswerDTO(-1, null, "Geh in den Vorkurs!", 5, 0, 1);
        manager.addAnswerToQuestion(conn, dto);
        Object[] result = manager.getAnswersToQuestion(conn, 1).toArray();
        AnswerDTO[] expected = new AnswerDTO[] {dto};
        System.out.println(expected[0]);
        System.out.println(result[0]);
        assertArrayEquals( expected, result );
    }

    @Test
    public void testGetBookmarkUsersToQuestion() throws Exception {

    }

}