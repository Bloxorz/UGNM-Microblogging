package i5.las2peer.services.servicePackage.Manager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.PostDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.*;

public class QuestionManagerTest extends AbstractManagerTest {

    private static QuestionManager manager;

    @BeforeClass
    public static void initClass() throws ParseException {
        manager = new QuestionManager();
    }

    @Test
    public void testGetQuestionList() throws Exception {;
        assertArrayEquals(
                DatabaseManagerTest.getTestQuestions(2,1,0),
                manager.getQuestionList(conn, 0).toArray()
        );
    }

    @Test
    public void testAddQuestion() throws Exception {
        QuestionDTO dto = new QuestionDTO(0, DatabaseManagerTest.getDummyDate(), "How are you?", 1, Arrays.asList(DatabaseManagerTest.getTestHashtags(2,3)));
        long newId = manager.addQuestion(conn, dto);
        assertEquals(newId, 9);
        QuestionDTO getDto = manager.getQuestion(conn, 9, 0);
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
        assertEquals(
                DatabaseManagerTest.getTestQuestions()[1],
                manager.getQuestion(conn, 2, 0)
        );
    }

    /*@Test
    public void testGetAnswersToQuestion() throws Exception {
        assertArrayEquals(
                DatabaseManagerTest.getTestAnswers(2,3,4),
                manager.getAnswersToQuestion(conn, 4).toArray()
        );
    }*/

    @Test
    public void testGetQuestionWithAnswers() throws Exception {
        Map<String, Object> map = new TreeMap<>();
        map.put("question", DatabaseManagerTest.getTestQuestions()[2]);
        map.put("answers", DatabaseManagerTest.getTestAnswers(2, 3, 4));
        System.out.println(
                normalize("{`question`:{`hashtags`:[],`favourCount`:2,`isFavourite`:true,`timestamp`:946681200000,`text`:`Where can I find the toilet?`,`idPost`:2},`answers`:[{`rating`:100,`idQuestion`:2,`timestamp`:946681200000,`text`:`In the building E2, first floor.`,`idPost`:3},{`rating`:0,`idQuestion`:2,`timestamp`:946681200000,`text`:`I think he is right`,`idPost`:5}]}".replace('`','"'))
                );
        assertEquals(
                normalize(map),
                normalize(manager.getQuestionWithAnswers(conn, 4, 0))
        );
    }

    @Test
    public void testAddAnswerToQuestion() throws Exception {
        AnswerDTO dto = new AnswerDTO(-1, DatabaseManagerTest.getDummyDate(), "Geh in den Vorkurs!", 5, 0, 1);
        manager.addAnswerToQuestion(conn, dto);
        AnswerDTO[] expected = new AnswerDTO[] {dto};
        assertArrayEquals(
                expected,
                manager.getAnswersToQuestion(conn, 1).toArray()
        );
    }

    @Test
    public void testGetHashtagsToQuestion() throws Exception {
        assertArrayEquals(
                DatabaseManagerTest.getTestHashtags(0,2),
                manager.getHashtagsToQuestion(conn, 1).toArray()
        );
    }

    @Test
    public void testGetBookmarkCount() throws Exception {
        assertEquals(2, manager.getBookmarkCount(conn, 2));
    }
}