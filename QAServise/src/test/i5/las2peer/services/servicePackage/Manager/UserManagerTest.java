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

public class UserManagerTest extends AbstractManagerTest {

    private static UserManager manager;

    @BeforeClass
    public static void initClass() throws ParseException {
        manager = new UserManager();
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
        QuestionDTO[] expected = DatabaseManagerTest.getTestQuestions(1, 2);
        expected[0].setFavourCount(expected[0].getFavourCount()+1);
        expected[0].setFavourite(true);
        expected[1].setFavourite(true);
        assertArrayEquals(
                expected,
                manager.bookmarkedQuestions(conn, 5).toArray()
        );
    }

    @Test
    public void testGetUserQuestions() throws Exception {
        QuestionDTO[] expected = DatabaseManagerTest.getTestQuestions(1);
        expected[0].setFavourite(true);
        assertArrayEquals(
                expected,
                manager.getUserQuestions(conn, 4).toArray()
        );
    }

    @Test
    public void testGetUser() throws Exception {
        assertEquals(
                DatabaseManagerTest.getTestUsers()[2],
                manager.getUser(conn, 3)
        );
    }

    @Test
    public void testEditUser() throws Exception {
        UserDTO expected = new UserDTO(4, 777, "image.com", "contact.de", "email.org");
        manager.editUser(conn, 4, expected);
        expected.setElo( DatabaseManagerTest.getTestUsers()[3].getElo() );
        assertEquals(
                expected,
                manager.getUser(conn, 4)
        );
    }

    @Test
    public void testGetExpertQuestions() throws Exception {
        QuestionDTO[] expected = DatabaseManagerTest.getTestQuestions(0,2);
        assertArrayEquals(
                expected,
                manager.getExpertQuestions(conn, 3).toArray()
        );
    }

    @Test
    public void testHasBookmarkedQuestion() throws Exception {
        assertFalse(manager.hasBookmarkedQuestion(conn, 1, 4));
        manager.bookmark(conn, 1, 4);
        assertTrue(manager.hasBookmarkedQuestion(conn, 1, 4));
    }

    @Test
    public void testIncreaseElo() throws Exception {
        assertEquals(10, manager.getElo(conn, 1));
        manager.increaseElo(conn, 1);
        assertEquals(11, manager.getElo(conn, 1));
    }

    @Test
    public void testDecreaseElo() throws Exception {
        assertEquals(10, manager.getElo(conn, 1));
        manager.decreaseElo(conn, 1);
        assertEquals(9, manager.getElo(conn, 1));
        for(int i=0; i<15; i++) manager.decreaseElo(conn, 1);
        assertEquals(0, manager.getElo(conn, 1));
    }

    @Test
    public void testGetElo() throws Exception {
        assertEquals(10, manager.getElo(conn, 1));
    }
}