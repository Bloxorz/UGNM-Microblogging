package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.sql.Connection;

import static org.junit.Assert.*;

public class HashtagManagerTest {

    private HashtagManager manager;
    private HashtagDTO[] testDTOs;

    @Before
    public void setUp() throws Exception {
        manager = new HashtagManager();
        testDTOs = DatabaseManagerTest.getTestHashtags();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetHashtagCollection() throws Exception {
        Connection conn = DatabaseManagerTest.getTestTable();
        assertArrayEquals(manager.getHashtagCollection(conn).toArray(), testDTOs);
    }

    @Test
    public void testGetHashtag() throws Exception {
        Connection conn = DatabaseManagerTest.getTestTable();
        HashtagDTO dto = manager.getHashtag(conn, 3);
        assertEquals(dto, testDTOs[2]);
    }

    @Test
    public void testAddHashtag() throws Exception {
        Connection conn = DatabaseManagerTest.getTestTable();
        long newId = manager.addHashtag(conn, "neuesHashtag");
        assertEquals( newId, 7 );
        assertEquals( manager.getHashtag(conn, 7), new HashtagDTO(newId, "neuesHashtag"));
    }

    @Test
    public void testUpdateHashtag() throws Exception {
        Connection conn = DatabaseManagerTest.getTestTable();
        HashtagDTO modified = new HashtagDTO(2, "modified");
        manager.updateHashtag(conn, modified);
        assertEquals( modified, manager.getHashtag(conn, 2));
    }

    @Test
    public void testDeleteHashtag() throws Exception {
        Connection conn = DatabaseManagerTest.getTestTable();
        manager.deleteHashtag(conn, 3);
        try {
            manager.getHashtag(conn, 3);
            fail("an excepton should be thrown");
        } catch (CantFindException e) {
            // OK
        } catch (Exception e) {
            fail("wrong exception thrown");
        }
    }

    @Test
    public void testGetAllQuestionsToHashtag() throws Exception {
        Connection conn = DatabaseManagerTest.getTestTable();
        Object[] result = manager.getAllQuestionsToHashtag(conn, 1).toArray();
        QuestionDTO[] expected = new QuestionDTO[] {
                DatabaseManagerTest.getTestQuestions()[0],
                DatabaseManagerTest.getTestQuestions()[2]
        };
        assertArrayEquals( expected, result );
    }

    @Test
    public void testGetAllExpertiseToHashtag() throws Exception {
        Connection conn = DatabaseManagerTest.getTestTable();
        Object[] result = manager.getAllExpertiseToHashtag(conn, 1).toArray();
        ExpertiseDTO[] expected = new ExpertiseDTO[] {
                DatabaseManagerTest.getTestExpertises()[2],
                DatabaseManagerTest.getTestExpertises()[3]
        };
        assertArrayEquals( result, expected );
    }

    @Test
    public void testExistsHashtag() throws Exception {
        Connection conn = DatabaseManagerTest.getTestTable();
        assertTrue(manager.existsHashtag(conn, "Assembler"));
        assertFalse( manager.existsHashtag(conn, "Haifisch") );
    }
}