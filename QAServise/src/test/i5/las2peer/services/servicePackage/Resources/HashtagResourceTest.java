package i5.las2peer.services.servicePackage.Resources;


import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class HashtagResourceTest {

    private static HashtagResource resource;

    @BeforeClass
    public static void initClass() {
        resource = new HashtagResource(null);
    }

    @Before
    public void setUp() throws Exception {
        Connection oldConn = resource.setConnection(DatabaseManagerTest.getTestTable());
        if(oldConn != null) oldConn.close();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetHashtagCollection() throws Exception {
        HttpResponse response = resource.getHashtagCollection();
    /*    assertEquals(200, response.getStatus());
        assertEquals(
                "[{\"text\":\"Java\", \"id\":1},{\"text\":\"Assembler\",\"id\":2},{\"text\":\"For-Loop\",\"id\":3},{\"text\":\"All\",\"id\":4},{\"text\":\"Analysis\",\"id\":5},{\"text\":\"Polynome\",\"id\":6}]",
                response.getResult() );
  */  }

    @Test
    public void testAddNewHashtag() throws Exception {
        //assertTrue( ResourceTestHelper.jsonEquals( "[{\"text\":\"Java\", \"id\":1},{\"text\":\"Assembler\",\"id\":2}]", "[  { \"text\"  : \"Java\", \"id\":1},{\"text\":\"Assembler\",  \"id\":2}]" ));
    }

    @Test
    public void testGetOneHashtag() throws Exception {

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
    public void testGetAllExpertisesToHashtag() throws Exception {

    }

    @Test
    public void testAddExpertiseToHashtag() throws Exception {

    }
}