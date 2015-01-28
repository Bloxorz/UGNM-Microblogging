package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.text.ParseException;

import static org.junit.Assert.*;

public class UserManagerTest {

    private static UserManager manager;

    private Connection conn;

    @BeforeClass
    public static void initClass() throws ParseException {
        manager = new UserManager();
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
    public void testRegisterUser() throws Exception {
        assertTrue( manager.registerUser(conn, new UserDTO(6,0,null,null,null)) );
    }

    @Test
    public void testBookmarkedQuestions() throws Exception {
        testBookmark();
    }

    @Test
    public void testBookmark() throws Exception {
        manager.bookmark(conn, 5, 2);
        manager.bookmark(conn, 5, 1);
        QuestionDTO[] expected = new QuestionDTO[] {
                DatabaseManagerTest.getTestQuestions()[0],
                DatabaseManagerTest.getTestQuestions()[1]
        };
        assertArrayEquals(expected, manager.bookmarkedQuestions(conn, 5).toArray());
    }

    @Test
    public void testGetUserQuestions() throws Exception {
        assertArrayEquals(
                DatabaseManagerTest.getTestQuestions(1),
                manager.getUserQuestions(conn, 4).toArray()
        );
    }
}