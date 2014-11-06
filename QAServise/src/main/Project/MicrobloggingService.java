package Project;

import i5.las2peer.api.Service;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.restMapper.annotations.*;
import i5.las2peer.restMapper.tools.ValidationResult;
import i5.las2peer.restMapper.tools.XMLCheck;
import i5.las2peer.security.Context;
import i5.las2peer.security.UserAgent;
import Project.DatabaseManager;

import Project.Manager.*;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.*;
import java.util.logging.*;


/**
 * LAS2peer Service offering Microblogging requests
 * 
 */
@Path("microblogging")
@Version("0.1")
public class MicrobloggingService extends Service {

	private String jdbcDriverClassName;
	private String jdbcLogin;
	private String jdbcPass;
	private String jdbcUrl;
	private String jdbcSchema;
	private DatabaseManager dbm;
    private Connection conn;
    private ManagerFacade mf;
    
    /**
     * A {@link Logger} for this class with name for
     * {@link Class#getSimpleName()}.
     */
	private final Logger LOGGER;
    {
        LOGGER = Logger
                .getLogger(getClass().getSimpleName());
    }
	
	public MicrobloggingService() {
        setFieldValues();
        dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl, jdbcSchema);
        mf = new ManagerFacade(null);
	}
    
    /**
     * Returns a connection to the database. If there is not already a connection, one is established.
     */
    private Connection getConnection() {
        try {
            if(conn != null)
                return conn;
            else
                return dbm.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "dbm connection lost");
            return null;
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
    @Path("getUser/{id}")
    public HttpResponse getUser(final @PathParam("id") long id) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException {
            return mf.getUser(getConnection(), id);
        } } );
    }
    @PUT
    @Path("editUser/{id}")
    public HttpResponse editUser(final @PathParam("id") long id, final @ContentParam String content) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException {
            return mf.editUser(getConnection(), id, content);
        } } );
    }    
    /*@DELETE
    @Path("deleteUser/{id}")
    public HttpResponse deleteUser(final @PathParam("id") long id) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException {
            return mf.deleteUser(getConnection(), id);
        } } );
    } *//*
    @GET
    @Path("hashtag/{id}")
    public HttpResponse getHashtag(final @PathParam("id") long id) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException {
            return mf.getHashtag(getConnection(), id);
        } } );
    }
    @POST
    @Path("createHashtag")
    public HttpResponse createHashtag(final @PathParam("id") long id, final @ContentParam String content) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException {
            return mf.createHashtag(getConnection(), id, content);
        } } );
    }  
    @DELETE
    @Path("deleteHashtag")
    public HttpResponse deleteHashtag(final @PathParam("id") long id) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException {
            return mf.deleteHashtag(getConnection(), id);
        } } );
    }  
    @POST
    @Path("hashtag/{idH}/referToExpertise/{idE}")
    @DELETE
    @Path("hashtag/{idH}/deleteReferenceTo/{idE}")
    @GET
    @Path("answer/{questionId}")
    @POST
    @Path("answer/{questionId}")
    @GET
    @Path("question/{id}")
    @POST
    @Path("question/{id}")
    @PUT
    @Path("question/{id}")
    @DELETE
    @Path("question/{id}")
    @GET
    @Path("question/user/detail/{id}")
    @GET
    @Path("question/user/{id}")
    @GET
    @Path("question/hashtag/detail/{id}")
    @GET
    @Path("question/hashtag/{id}")
    */

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
