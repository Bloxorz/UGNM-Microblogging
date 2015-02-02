package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.BeforeClass;
import org.junit.Test;


import static org.junit.Assert.*;

public class HashtagManagerTest extends AbstractManagerTest {

    private static HashtagManager manager;

    @BeforeClass
    public static void initClass() {
        manager = new HashtagManager();
    }

    @Test
    public void testGetHashtagCollection() throws Exception {
        assertArrayEquals(
                DatabaseManagerTest.getTestHashtags(),
                manager.getHashtagCollection(conn).toArray()
        );
    }

    @Test
    public void testGetAllQuestionsToHashtag( ) throws Exception {
        assertArrayEquals(
                DatabaseManagerTest.getTestQuestions(0,2),
                manager.getAllQuestionsToHashtag(conn, 1, 0).toArray()
        );
    }

    @Test
    public void testCreateHashtagIfNotExists() throws Exception {
        assertEquals(4, manager.createHashtagIfNotExists(conn, "Analysis"));
        assertEquals(7, manager.createHashtagIfNotExists(conn, "NeuesHashtag"));
    }
}