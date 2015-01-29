package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.sql.Connection;

import static org.junit.Assert.*;

public class HashtagManagerTest {

    private static HashtagManager manager;
    private static HashtagDTO[] testDTOs;

    private Connection conn;

    @BeforeClass
    public static void initClass() {
        manager = new HashtagManager();
        testDTOs = DatabaseManagerTest.getTestHashtags();
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
    public void testGetHashtagCollection() throws Exception {
        assertArrayEquals(manager.getHashtagCollection(conn).toArray(), testDTOs);
    }

    @Test
    public void testGetHashtag() throws Exception {
        HashtagDTO dto = manager.getHashtag(conn, 3);
        assertEquals(dto, testDTOs[2]);
    }

    @Test
    public void testAddHashtag() throws Exception {
        long newId = manager.addHashtag(conn, "neuesHashtag");
        assertEquals( newId, 7 );
        assertEquals( manager.getHashtag(conn, 7), new HashtagDTO(newId, "neuesHashtag"));
    }

    @Test
    public void testUpdateHashtag() throws Exception {
        HashtagDTO modified = new HashtagDTO(2, "modified");
        manager.updateHashtag(conn, modified);
        assertEquals( modified, manager.getHashtag(conn, 2));
    }

    @Test
    public void testDeleteHashtag() throws Exception {
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
        Object[] result = manager.getAllQuestionsToHashtag(conn, 1).toArray();
        QuestionDTO[] expected = new QuestionDTO[] {
                DatabaseManagerTest.getTestQuestions()[0],
                DatabaseManagerTest.getTestQuestions()[2]
        };
        assertArrayEquals( expected, result );
    }

    @Test
    public void testGetAllExpertiseToHashtag() throws Exception {
        Object[] result = manager.getAllExpertiseToHashtag(conn, 1).toArray();
        ExpertiseDTO[] expected = new ExpertiseDTO[] {
                DatabaseManagerTest.getTestExpertises()[2],
                DatabaseManagerTest.getTestExpertises()[3]
        };
        assertArrayEquals( result, expected );
    }

    @Test
    public void testExistsHashtag() throws Exception {
        assertTrue(manager.existsHashtag(conn, "Assembler"));
        assertFalse( manager.existsHashtag(conn, "Haifisch") );
    }

    @Test
    public void testGetAllQuestionsToHashtag1() throws Exception {
        assertArrayEquals(
                DatabaseManagerTest.getTestQuestions(0,2),
                manager.getAllQuestionsToHashtag(conn, 1).toArray()
        );
    }
}