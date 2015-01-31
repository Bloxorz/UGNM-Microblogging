package i5.las2peer.services.servicePackage;

import com.sun.xml.internal.bind.v2.TODO;
import i5.las2peer.api.Service;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.restMapper.annotations.*;
import i5.las2peer.restMapper.tools.ValidationResult;
import i5.las2peer.restMapper.tools.XMLCheck;
import i5.las2peer.security.Context;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.Resources.*;
import i5.las2peer.services.servicePackage.database.DatabaseManager;
import net.minidev.json.JSONObject;
import org.bouncycastle.asn1.ua.UAObjectIdentifiers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * LAS2peer Service
 * 
 * This is a template for a very basic LAS2peer service
 * that uses the LAS2peer Web-Connector for RESTful access to it.
 * 
 */
@Path("api")
@Version("0.1")
public class ServiceClass extends Service {

	private String jdbcDriverClassName;
	private String jdbcLogin;
	private String jdbcPass;
	private String jdbcUrl;
	private String jdbcSchema;
	private DatabaseManager dbm;

	// resources
	private QuestionResource qr;
    private HashtagResource hr;
    private UserResource use;

	public ServiceClass() {
		// read and set properties values
		// IF THE SERVICE CLASS NAME IS CHANGED, THE PROPERTIES FILE NAME NEED TO BE CHANGED TOO!
		setFieldValues();
		// instantiate a database manager to handle database connection pooling and credentials
		dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl, jdbcSchema);

		try {
			Connection conn = dbm.getConnection();
			qr = new QuestionResource(conn);
            hr = new HashtagResource(conn);
            use = new UserResource(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean isAnonymous() {
		return getActiveAgent().getId() == getActiveNode().getAnonymous().getId();
	}

	private HttpResponse registerUser() {
		if(isAnonymous()) {
			return new HttpResponse("User is anonymous", 200);
		} else  {
			return use.registerUser(new UserDTO(getActiveAgent().getId(), 10, null, null, null));
		}
	}

	/**
	 * Simple function to validate a user login.
	 * Basically it only serves as a "calling point" and does not really validate a user
	 * (since this is done previously by LAS2peer itself, the user does not reach this method
	 * if he or she is not authenticated).
	 * 
	 */
	@GET
	@Path("validate")
	public HttpResponse validateLogin() {
		String returnString = "";
		returnString += "You are " + ((UserAgent) getActiveAgent()).getLoginName() + " and your login is valid!";
		
		HttpResponse res = new HttpResponse(returnString);
		res.setStatus(200);
		return res;
	}

	
	@GET
	@Path("user/")
	public HttpResponse getUser(){
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() != 200) return registerResponse;
		return use.getUser(isAnonymous() ? 0 : getActiveAgent().getId());
	}

	@PUT
	@Path("user/")
	public HttpResponse editUser(@ContentParam String content) {
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() != 200) return registerResponse;
		return use.editUser(isAnonymous() ? 0 : getActiveAgent().getId(), content);
	}

	@POST
	@Path("user/bookmark/{questionId}")
	public HttpResponse bookmark(@PathParam("questionId") long questionId) {
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() != 200) return registerResponse;
		return use.addBookmark(isAnonymous() ? 0 : getActiveAgent().getId(), questionId);
	}

	@GET
	@Path("user/favouriteQuestions")
	public HttpResponse getBookmarkedQuestions() {
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() != 200) return registerResponse;
		return use.bookmarkedQuestions(isAnonymous() ? 0 : getActiveAgent().getId());
	}

	@GET
	@Path("user/expertQuestions")
	public HttpResponse getExpertQuestions() {
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() != 200) return registerResponse;
		return use.getExpertiseQuestions(isAnonymous() ? 0 : getActiveAgent().getId());
	}
	
	
	@GET
    @Path("hashtag/")
    public HttpResponse getHashtags() {
        return hr.getHashtagCollection();
    }
	
	@GET
	@Path("hashtag/{id}/questions")
	public HttpResponse getAllQuestionsToHashtag(@PathParam("id") Long hashtagId){
		long userThatAsksId;
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() == 200)
			userThatAsksId = isAnonymous() ? 0 : getActiveAgent().getId();
		else
			userThatAsksId = 0;
		return hr.getAllQuestionsToHashtag(hashtagId, userThatAsksId);
	}

    @GET
	@Path("questions")
	public HttpResponse getQuestions() {
		long userThatAsksId;
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() == 200)
			userThatAsksId = isAnonymous() ? 0 : getActiveAgent().getId();
		else
			userThatAsksId = 0;
		return qr.getQuestionCollection(userThatAsksId);
	}

	@POST
	@Path("question")
	public HttpResponse addQuestion(@ContentParam String content) {
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() != 200) return registerResponse;
		return qr.addQuestion(content, isAnonymous() ? 0 : getActiveAgent().getId());
	}

	@GET
	@Path("answers/question/{questionId}")
	public HttpResponse getQuestionAndAnswers(@PathParam("questionId") long questionId) {
		long userThatAsksId;
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() == 200)
			userThatAsksId = isAnonymous() ? 0 : getActiveAgent().getId();
		else
			userThatAsksId = 0;
		return qr.getQuestionWithAnswers(questionId, userThatAsksId);
	}

	@POST
	@Path("answers/question/{questionId}")
	public HttpResponse addAnswerToQuestion(@PathParam("questionId") long questionId, @ContentParam String content) {
		HttpResponse registerResponse = registerUser();
		if(registerResponse.getStatus() != 200) return registerResponse;
		return qr.addAnswerToQuestion(questionId, content, isAnonymous() ? 0 : getActiveAgent().getId());
	}

	@POST
	@Path("answers/{answerId}")
	public HttpResponse upvoteAnswer(@PathParam("answerId") long questionId) {
		//TODO
	}

    private Connection getConnection() {
        try { return dbm.getConnection(); }
        catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

	/**
	 * Method for debugging purposes.
	 * Here the concept of restMapping validation is shown.
	 * It is important to check, if all annotations are correct and consistent.
	 * Otherwise the service will not be accessible by the WebConnector.
	 * Best to do it in the unit tests.
	 * To avoid being overlooked/ignored the method is implemented here and not in the test section.
	 * @return  true, if mapping correct
	 */
	public boolean debugMapping() {
		String XML_LOCATION = "./restMapping.xml";
		String xml = getRESTMapping();

		try {
			RESTMapper.writeFile(XML_LOCATION, xml);
		} catch (IOException e) {
			e.printStackTrace();
		}

		XMLCheck validator = new XMLCheck();
		ValidationResult result = validator.validate(xml);

		if (result.isValid())
			return true;
		return false;
	}

	/**
	 * This method is needed for every RESTful application in LAS2peer. There is no need to change!
	 * 
	 * @return the mapping
	 */
	public String getRESTMapping() {
		String result = "";
		try {
			result = RESTMapper.getMethodsAsXML(this.getClass());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return result;
	}


}
