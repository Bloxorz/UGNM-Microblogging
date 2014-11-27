package i5.las2peer.services.servicePackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import i5.las2peer.p2p.LocalNode;
import i5.las2peer.security.ServiceAgent;
import i5.las2peer.security.UserAgent;
import i5.las2peer.testing.MockAgentFactory;
import i5.las2peer.webConnector.WebConnector;
import i5.las2peer.webConnector.client.ClientResponse;
import i5.las2peer.webConnector.client.MiniClient;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Example Test Class demonstrating a basic JUnit test structure.
 * 
 * 
 *
 */
public class ServiceTest {
	
	private static final String HTTP_ADDRESS = "http://127.0.0.1";
	private static final int HTTP_PORT = WebConnector.DEFAULT_HTTP_PORT;
	
	private static LocalNode node;
	private static WebConnector connector;
	private static ByteArrayOutputStream logStream;
	
	private static UserAgent testAgent;
	private static final String testPass = "adamspass";
	
	private static final String testServiceClass = "i5.las2peer.services.servicePackage.ServiceClass";
	
	private static final String mainPath = "ugnmMicro/";
	
	
	/**
	 * Called before the tests start.
	 * 
	 * Sets up the node and initializes connector and users that can be used throughout the tests.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void startServer() throws Exception {
		
		//start node
		node = LocalNode.newNode();
		node.storeAgent(MockAgentFactory.getAdam());
		node.launch();
		
		ServiceAgent testService = ServiceAgent.generateNewAgent(testServiceClass, "a pass");
		testService.unlockPrivateKey("a pass");
		
		node.registerReceiver(testService);
		
		//start connector
		logStream = new ByteArrayOutputStream ();
		
		connector = new WebConnector(true,HTTP_PORT,false,1000);
		connector.setSocketTimeout(10000);
		connector.setLogStream(new PrintStream (logStream));
		connector.start ( node );
        Thread.sleep(1000); //wait a second for the connector to become ready
		testAgent = MockAgentFactory.getAdam();
		
        connector.updateServiceList();
        //avoid timing errors: wait for the repository manager to get all services before continuing
        try
        {
            System.out.println("waiting..");
            Thread.sleep(10000);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
		
	}
	
	
	/**
	 * Called after the tests have finished.
	 * Shuts down the server and prints out the connector log file for reference.
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void shutDownServer () throws Exception {
		
		connector.stop();
		node.shutDown();
		
        connector = null;
        node = null;
        
        LocalNode.reset();
		
		System.out.println("Connector-Log:");
		System.out.println("--------------");
		
		System.out.println(logStream.toString());
		
    }
	
	
	/**
	 * 
	 * Tests the validate method.
	 * 
	 */
	@Test
	public void testValidateLogin()
	{
		MiniClient c = new MiniClient();
		c.setAddressPort(HTTP_ADDRESS, HTTP_PORT);
		
		try
		{
			c.setLogin(Long.toString(testAgent.getId()), testPass);
            ClientResponse result=c.sendRequest("GET", mainPath +"validate", "");
            assertEquals(200, result.getHttpCode());
            assertTrue(result.getResponse().trim().contains("adam")); //login name is part of response
			System.out.println("Result of 'testValidateLogin': " + result.getResponse().trim());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail ( "Exception: " + e );
		}
		
    }

    @Test
    public void testExampleMethod()
    {
        MiniClient c = new MiniClient();
        c.setAddressPort(HTTP_ADDRESS, HTTP_PORT);

        try
        {
            c.setLogin(Long.toString(testAgent.getId()), testPass);
            ClientResponse result=c.sendRequest("POST", mainPath +"myMethodPath/testInput", ""); //testInput is the pathParam
            assertEquals(200, result.getHttpCode());
            assertTrue(result.getResponse().trim().contains("testInput")); //"testInput" name is part of response
            System.out.println("Result of 'testExampleMethod': " + result.getResponse().trim());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail ( "Exception: " + e );
        }

    }

	/**
	 * Test the ServiceClass for valid rest mapping.
	 * Important for development.
	 */
	@Test
	public void testDebugMapping() throws Exception
	{
		ServiceClass cl = new ServiceClass();
		assertTrue(cl.debugMapping());
	}

    /**
     * Tests the getExpertise method.
     */
	
	/*
	
    @Test
    public void testGetExpertise()
    {
        MiniClient c = new MiniClient();
        c.setAddressPort(HTTP_ADDRESS, HTTP_PORT);

        try
        {
            c.setLogin(Long.toString(testAgent.getId()), testPass);
            ClientResponse result=c.sendRequest("GET", mainPath + "expertise/6/token", "");
            assertEquals(200, result.getHttpCode());
            assertTrue(result.getResponse().trim().equals("{\"text\":\"guitar\",\"id\":6}"));
            System.out.println("Result of 'testExampleMethod': " + result.getResponse().trim());

            result = c.sendRequest("GET", mainPath + "expertise/100/token", "");
            assertEquals(404, result.getHttpCode());
            System.out.println("Result of 'testExampleMethod': " + result.getResponse().trim());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail ( "Exception: " + e );
        }
    }

    @Test
    public void testGetUser()
    {
        MiniClient c = new MiniClient();
        c.setAddressPort(HTTP_ADDRESS, HTTP_PORT);

        try
        {
            c.setLogin(Long.toString(testAgent.getId()), testPass);
            ClientResponse result=c.sendRequest("GET", mainPath + "user/token", "");
            assertEquals(200, result.getHttpCode());
            assertTrue(result.getResponse().trim().endsWith("\"pass\":\"87ade9\",\"id\":15}]"));
            System.out.println("Result of 'testGetUser': " + result.getResponse().trim());

            result = c.sendRequest("GET", mainPath + "user/49/token", "");
            assertEquals(404, result.getHttpCode());
            System.out.println("Result of 'testGetUser': " + result.getResponse().trim());

            result = c.sendRequest("GET", mainPath + "user/4/token", "");
            assertEquals(200, result.getHttpCode());
            assertTrue(result.getResponse().trim().contains("\"id\":4}]"));
            System.out.println("Result of 'testGetUser': " + result.getResponse().trim());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail ( "Exception: " + e );
        }
    }

    @Test
    public void testGetHashtag()
    {
        MiniClient c = new MiniClient();
        c.setAddressPort(HTTP_ADDRESS, HTTP_PORT);

        try
        {
            c.setLogin(Long.toString(testAgent.getId()), testPass);
            ClientResponse result=c.sendRequest("GET", mainPath + "hashtag/token", "");
            assertEquals(200, result.getHttpCode());
            assertTrue(result.getResponse().contains("\"text\":\"C++\"")
                        && result.getResponse().contains("\"text\":\"Java\""));
            System.out.println("Result of 'testGetHashtag': " + result.getResponse().trim());

            result = c.sendRequest("GET", mainPath + "hashtag/49/token", "");
            assertEquals(404, result.getHttpCode());
            System.out.println("Result of 'testGetHashtag': " + result.getResponse().trim());

            result = c.sendRequest("GET", mainPath + "hashtag/4/token", "");
            assertEquals(200, result.getHttpCode());
            assertTrue(result.getResponse().trim().contains("\"id\":4"));
            System.out.println("Result of 'testGetHashtag': " + result.getResponse().trim());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail ( "Exception: " + e );
        }
    }

    @Test
    public void testGetQuestion()
    {
        MiniClient c = new MiniClient();
        c.setAddressPort(HTTP_ADDRESS, HTTP_PORT);

        try
        {
            c.setLogin(Long.toString(testAgent.getId()), testPass);
            ClientResponse result=c.sendRequest("GET", mainPath + "questions/token", "");
            assertEquals(200, result.getHttpCode());
            assertTrue(result.getResponse().contains("[")
                    && result.getResponse().contains("]"));
            System.out.println("Result of 'testGetQuestion': " + result.getResponse().trim());

            result = c.sendRequest("GET", mainPath + "question/49/token", "");
            assertEquals(404, result.getHttpCode());
            System.out.println("Result of 'testGetQuestion': " + result.getResponse().trim());

            result = c.sendRequest("GET", mainPath + "hashtag/4/token", "");
            assertEquals(200, result.getHttpCode());
            assertTrue(result.getResponse().trim().contains("\"id\":4"));
            System.out.println("Result of 'testGetQuestion': " + result.getResponse().trim());
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fail ( "Exception: " + e );
        }
    }
    
    */
}
