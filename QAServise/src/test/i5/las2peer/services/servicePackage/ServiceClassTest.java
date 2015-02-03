package i5.las2peer.services.servicePackage;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import i5.las2peer.communication.Message;
import i5.las2peer.communication.MessageException;
import i5.las2peer.p2p.*;
import i5.las2peer.p2p.pastry.PastryStorageException;
import i5.las2peer.persistency.Envelope;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.security.*;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import i5.las2peer.services.servicePackage.database.DatabaseManagerTest;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ServiceClassTest {

    private static ServiceClass scAnonymous;
    private static ServiceClass scLoggedIn;
    private static DatabaseManager mockDatabase;

    private static class MockServiceClass extends ServiceClass {
        private final UserAgent activeAgent;
        private final Node activeNode;

        private static class MockAgent extends UserAgent {
            private final long userId;
            private final String login;
            public MockAgent(long userId, String login) {
                super(0, (PublicKey)null, new byte[]{}, new byte[]{});
                this.userId = userId;
                this.login = login;
            }
            @Override public String toXmlString() {return null;}
            @Override public void receiveMessage(Message message, Context context) throws MessageException {}
            @Override public String getLoginName() {return login;}
            @Override public long getId() {return userId;}
        }

        public MockServiceClass(long userId, String login) {
            this.activeAgent = new MockAgent(userId, login);
            this.activeNode = new Node() {
                @Override public void sendUnlockRequest(long l, String s, Object o, PublicKey publicKey) throws L2pSecurityException {}
                @Override public Serializable getNodeId() {return null;}
                @Override public NodeInformation getNodeInformation(Object o) throws NodeNotFoundException {return null;}
                @Override public Object[] getOtherKnownNodes() {return new Object[0];}
                @Override public void launch() throws NodeException {}
                @Override public void sendMessage(Message message, MessageResultListener messageResultListener, SendMode sendMode) {}
                @Override public void sendMessage(Message message, Object o, MessageResultListener messageResultListener) throws AgentNotKnownException, NodeNotFoundException, L2pSecurityException {}
                @Override public Envelope fetchArtifact(long l) throws ArtifactNotFoundException, StorageException {return null;}
                @Override public void storeArtifact(Envelope envelope) throws StorageException, L2pSecurityException {}
                @Override public void removeArtifact(long l, byte[] bytes) throws ArtifactNotFoundException, StorageException {}
                @Override public Object[] findRegisteredAgent(long l, int i) throws AgentNotKnownException {return new Object[0];}
                @Override public Agent getAgent(long l) throws AgentNotKnownException {return null;}
                @Override public boolean knowsAgentLocally(long l) {return false;}
                @Override public void storeAgent(Agent agent) throws AgentAlreadyRegisteredException, L2pSecurityException, AgentException {}
                @Override public void updateAgent(Agent agent) throws AgentException, L2pSecurityException, PastryStorageException {}
                @Override public Agent getAnonymous() {return new MockAgent(0, "anonymous");}
            };
        }
        @Override public Agent getActiveAgent() {return activeAgent;}
        @Override public Node getActiveNode() {return activeNode;}
    }

    @BeforeClass
    public static void initClass() throws Exception {
        scAnonymous = new MockServiceClass(0, "anonymous");
        scLoggedIn = new MockServiceClass(2, "someLoggedInUser");
        mockDatabase = new DatabaseManager("", "", "", "", "") {
            @Override public Connection getConnection() throws SQLException {
                return DatabaseManagerTest.getTestTable();
            }
        };
    }

    @Before
    public void setUp() throws Exception {
        scAnonymous.setDatabase(mockDatabase);
        scLoggedIn.setDatabase(mockDatabase);
    }

    private static String normalize(String input) throws IOException {
        input = input.replace('`', '"');
        ObjectMapper om = new ObjectMapper();
        om.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
        return om.writeValueAsString(om.readValue(input, Object.class));
        //return new Gson().toJson(new JsonParser().parse(input));
    }

    @Test
    public void testValidateLogin() throws Exception {
        System.out.println(scAnonymous.validateLogin().getResult());
        System.out.println(scLoggedIn.validateLogin().getResult());
        assertEquals(200, scAnonymous.validateLogin().getStatus());
        assertEquals(200, scLoggedIn.validateLogin().getStatus());
    }

    @Test
    public void testGetUser() throws Exception {
        assertEquals(401, scAnonymous.getUser().getStatus());
        HttpResponse response = scLoggedIn.getUser();
        assertEquals(200, response.getStatus());
        assertEquals(
                normalize("{`hashtags`:[{`text`:`Analysis`},{`text`:`Polynome`},{`text`:`Lagrange-Restglied`}],`elo`:10}"),
                normalize(response.getResult())
        );
    }

    @Test
    public void testEditUser() throws Exception {
        assertEquals(400, scLoggedIn.editUser("{InvalidJson}").getStatus());
        assertEquals(401, scAnonymous.editUser(normalize("{`hashtags`:[{`text`:`Java`},{`text`:`Assembler`}]}")).getStatus());
        HttpResponse response = scLoggedIn.editUser(normalize("{`hashtags`:[{`text`:`Java`},{`text`:`Assembler`}]}"));
        assertEquals(200, response.getStatus());
        assertEquals(
                normalize("{`hashtags`:[{`text`:`Java`},{`text`:`Assembler`}],`elo`:10}"),
                normalize(scLoggedIn.getUser().getResult())
        );
    }

    @Test
    public void testBookmark() throws Exception {
        assertEquals(401, scAnonymous.bookmark(2).getStatus());
        assertEquals(404, scLoggedIn.bookmark(3).getStatus());
        assertEquals(200, scLoggedIn.bookmark(1).getStatus());
        assertEquals(
                normalize("[{`hashtags`:[{`text`:`Java`},{`text`:`For-Loop`}],`favourCount`:2,`isFavourite`:true,`timestamp`:946681200000,`text`:`How do I write a for-loop?`,`idPost`:1},{`hashtags`:[],`favourCount`:2,`isFavourite`:true,`timestamp`:946681200000,`text`:`Where can I find the toilet?`,`idPost`:2}]"),
                scLoggedIn.getBookmarkedQuestions().getResult()
        );
    }

    @Test
    public void testGetBookmarkedQuestions() throws Exception {
        assertEquals(401, scAnonymous.getBookmarkedQuestions().getStatus());
        HttpResponse response = scLoggedIn.getBookmarkedQuestions();
        assertEquals(200, response.getStatus());
        assertEquals(
                normalize("[{`hashtags`:[],`favourCount`:2,`isFavourite`:true,`timestamp`:946681200000,`text`:`Where can I find the toilet?`,`idPost`:2}]"),
                normalize(response.getResult())
        );
    }

    /*@Test
    public void testGetExpertQuestions() throws Exception {

    }*/

    @Test
    public void testGetHashtags() throws Exception {
        HttpResponse response = scAnonymous.getHashtags();
        assertEquals(200, response.getStatus());
        assertEquals(
                normalize("[{`text`:`Java`},{`text`:`Assembler`},{`text`:`For-Loop`},{`text`:`Analysis`},{`text`:`Polynome`},{`text`:`Lagrange-Restglied`}]"),
                normalize(response.getResult())
        );
    }

    /*@Test
    public void testGetAllQuestionsToHashtag() throws Exception {

    }*/

    @Test
    public void testGetQuestions() throws Exception {
        assertEquals(200, scAnonymous.getQuestions().getStatus());
        HttpResponse response = scLoggedIn.getQuestions();
        assertEquals(200, response.getStatus());
        assertEquals(
                normalize("[{`hashtags`:[{`text`:`Java`}],`favourCount`:1,`isFavourite`:false,`timestamp`:946681200000,`text`:`How does the JFrame-constructor work?`,`idPost`:4},{`hashtags`:[],`favourCount`:2,`isFavourite`:true,`timestamp`:946681200000,`text`:`Where can I find the toilet?`,`idPost`:2},{`hashtags`:[{`text`:`Java`},{`text`:`For-Loop`}],`favourCount`:1,`isFavourite`:false,`timestamp`:946681200000,`text`:`How do I write a for-loop?`,`idPost`:1}]"),
                normalize(response.getResult())
        );
    }

    @Test
    public void testAddQuestion() throws Exception {
        assertEquals(400, scLoggedIn.addQuestion("{InvalidJson}").getStatus());
        assertEquals(401, scAnonymous.addQuestion(normalize("{`hashtags`:[{`text`:`Java`},{`text`:`NeuesHashtag`}],`text`:`How to use JUnit?`}")).getStatus());
        HttpResponse response = scLoggedIn.addQuestion(normalize("{`hashtags`:[{`text`:`Java`},{`text`:`NeuesHashtag`}],`text`:`How to use JUnit?`}"));
        assertEquals(200, response.getStatus());
        assertEquals(
                normalize("{`idQuestion`:9}"),
                normalize(response.getResult())
        );
    }

    @Test
    public void testGetQuestionAndAnswers() throws Exception {
        assertEquals(200, scAnonymous.getQuestionAndAnswers(2).getStatus());
        assertEquals(404, scAnonymous.getQuestionAndAnswers(3).getStatus());
        HttpResponse response = scLoggedIn.getQuestionAndAnswers(2);
        assertEquals(200, response.getStatus());
        assertEquals(
                normalize("{`question`:{`hashtags`:[],`favourCount`:2,`isFavourite`:true,`timestamp`:946681200000,`text`:`Where can I find the toilet?`,`idPost`:2},`answers`:[{`rating`:100,`idQuestion`:2,`timestamp`:946681200000,`text`:`In the building E2, first floor.`,`idPost`:3},{`rating`:0,`idQuestion`:2,`timestamp`:946681200000,`text`:`I think he is right`,`idPost`:5}]}"),
                normalize(response.getResult())
        );
    }

    @Test
    public void testAddAnswerToQuestion() throws Exception {
        assertEquals(400, scLoggedIn.addAnswerToQuestion(2, "{InvalidJson}").getStatus());
        assertEquals(200, scLoggedIn.addAnswerToQuestion(2, normalize("{`text`:`Meine Antwort.`}")).getStatus());
        HttpResponse response = scLoggedIn.getQuestionAndAnswers(2);
        assertEquals(200, response.getStatus());
        assertEquals(
                normalize("{`question`:{`hashtags`:[],`favourCount`:2,`isFavourite`:true,`timestamp`:946681200000,`text`:`Where can I find the toilet?`,`idPost`:2},`answers`:[{`rating`:100,`idQuestion`:2,`timestamp`:946681200000,`text`:`In the building E2, first floor.`,`idPost`:3},{`rating`:0,`idQuestion`:2,`timestamp`:946681200000,`text`:`I think he is right`,`idPost`:5},{`rating`:0,`idQuestion`:2,`timestamp`:946681200000,`text`:`Meine Antwort.`,`idPost`:9}]}"),
                normalize(response.getResult())
        );
    }

    @Test
    public void testUpvoteAnswer() throws Exception {
        assertEquals(401, scAnonymous.upvoteAnswer(3).getStatus());
        assertEquals(404, scLoggedIn.upvoteAnswer(777).getStatus());
        assertEquals(500, scLoggedIn.upvoteAnswer(3).getStatus()); // own answer
        assertEquals(200, scLoggedIn.upvoteAnswer(5).getStatus());
        assertEquals(500, scLoggedIn.upvoteAnswer(5).getStatus());
    }
}