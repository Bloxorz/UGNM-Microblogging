package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.General.Rating;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.util.Date;

import static org.junit.Assert.*;

public class QuestionManagerTest {

    private static QuestionManager manager;
    private static QuestionDTO[] testDTOs;

    private Connection conn;

    @BeforeClass
    public static void initClass() {
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
        assertArrayEquals(testDTOs, manager.getQuestionList(conn).toArray());
    }

    @Test
    public void testAddQuestion() throws Exception {
        QuestionDTO dto = new QuestionDTO(0, null, "How are you?", 5);
        long newId = manager.addQuestion(conn, dto);
        assertEquals(newId, 9);
        QuestionDTO getDto = manager.getQuestion(conn, 9);
        assertEquals( getDto.getId(), dto.getId() );
        assertEquals( getDto.getText(), dto.getText() );
        assertEquals( getDto.getUserId(), dto.getUserId() );
    }

    @Test
    public void testGetQuestion() throws Exception {
        QuestionDTO dto = manager.getQuestion(conn, 2);
        assertEquals(testDTOs[1], dto);
    }

    @Test
    public void testEditQuestion() throws Exception {

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
        Object[] result = manager.getAnswersToQuestion(conn, 4).toArray();
        AnswerDTO[] expected = new AnswerDTO[] {
                DatabaseManagerTest.getTestAnswers()[2],
                DatabaseManagerTest.getTestAnswers()[3],
                DatabaseManagerTest.getTestAnswers()[4]
        };

        assertArrayEquals( expected, result );
    }

    @Test
    public void testAddAnswerToQuestion() throws Exception {
        AnswerDTO dto = new AnswerDTO(-1, new Date(){ @Override public boolean equals(Object o){return true;}}, "Geh in den Vorkurs!", 5, Rating.fromInt(0), 1);
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