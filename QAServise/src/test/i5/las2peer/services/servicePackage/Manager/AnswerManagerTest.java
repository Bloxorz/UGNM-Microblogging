package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class AnswerManagerTest {

    private static AnswerManager manager;
    private static AnswerDTO[] testDTOs;

    private Connection conn;

    @BeforeClass
    public static void initClass() {
        manager = new AnswerManager();
        testDTOs = DatabaseManagerTest.getTestAnswers();
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
    public void testGetAnswer() throws Exception {
        AnswerDTO dto = manager.getAnswer(conn, 5);
        assertEquals(testDTOs[1], dto);
    }

    @Test
    public void testEditAnswer() throws Exception {

    }

    @Test
    public void testDeleteAnswer() throws Exception {

    }
}