package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class HashtagManagerTest {

    private HashtagManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new HashtagManager();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetHashtagCollection() throws Exception {

    }

    @Test
    public void testGetHashtag() throws Exception {
        Connection conn = DatabaseManagerTest.getTestTable();
        HashtagDTO dto = manager.getHashtag(conn, 3);
        assertEquals(dto.getId(), 3);
        assertEquals(dto.getText(), "For-Loop");
    }

    @Test
    public void testAddHashtag() throws Exception {

    }

    @Test
    public void testUpdateHashtag() throws Exception {

    }

    @Test
    public void testDeleteHashtag() throws Exception {

    }

    @Test
    public void testGetAllQuestionsToHashtag() throws Exception {

    }

    @Test
    public void testGetAllExpertiseToHashtag() throws Exception {

    }

    @Test
    public void testExistsHashtag() throws Exception {

    }
}