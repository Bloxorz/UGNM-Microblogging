package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

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

    }

    @Test
    public void testAddQuestion() throws Exception {
        QuestionDTO dto = new QuestionDTO(0, null, "How are you?", 5);
        long newId = manager.addQuestion(conn, dto);
        assertEquals( newId, 9 );
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

    }

    @Test
    public void testGetAnswersToQuestion() throws Exception {

    }

    @Test
    public void testAddAnswerToQuestion() throws Exception {

    }

    @Test
    public void testGetBookmarkUsersToQuestion() throws Exception {

    }
}