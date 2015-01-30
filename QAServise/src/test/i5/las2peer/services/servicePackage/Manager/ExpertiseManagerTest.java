package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ExpertiseManagerTest extends AbstractManagerTest {

    private static ExpertiseManager manager;
    private static ExpertiseDTO[] testDTOs;

    @BeforeClass
    public static void initClass() {
        manager = new ExpertiseManager();
        testDTOs = DatabaseManagerTest.getTestExpertises();
    }

    @Test
    public void testGetExpertiseList() throws Exception {
        assertArrayEquals( testDTOs, manager.getExpertiseList(conn).toArray() );
    }

    @Test
    public void testAddExpertise() throws Exception {
        long newId = manager.addExpertise(conn, new ExpertiseDTO(-1, "neueKompetenz"));
        long expectedId = testDTOs.length+1;
        assertEquals( newId, expectedId );
        assertEquals( manager.getExpertise(conn, newId), new ExpertiseDTO(expectedId, "neueKompetenz"));
    }

    @Test
    public void testGetExpertise() throws Exception {
        ExpertiseDTO dto = manager.getExpertise(conn, 3);
        assertEquals(dto, testDTOs[2]);
    }

    @Test
    public void testEditExpertise() throws Exception {
        ExpertiseDTO modified = new ExpertiseDTO(2, "modified");
        manager.editExpertise(conn, modified);
        assertEquals( modified, manager.getExpertise(conn, 2));
    }

    @Test
    public void testDeleteExpertise() throws Exception {
        manager.deleteExpertise(conn, 3);
        try {
            manager.getExpertise(conn, 3);
            fail("an excepton should be thrown");
        } catch (CantFindException e) {
            // OK
        } catch (Exception e) {
            fail("wrong exception thrown");
        }
    }
}