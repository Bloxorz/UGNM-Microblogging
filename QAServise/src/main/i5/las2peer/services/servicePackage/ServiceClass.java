package i5.las2peer.services.servicePackage;

import com.google.gson.JsonParseException;
import i5.las2peer.api.Service;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.restMapper.annotations.*;
import i5.las2peer.restMapper.tools.ValidationResult;
import i5.las2peer.restMapper.tools.XMLCheck;
import i5.las2peer.security.Context;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.servicePackage.Manager.GenericServiceRequest;
import i5.las2peer.services.servicePackage.Manager.GenericServiceResponse;
import i5.las2peer.services.servicePackage.Manager.ManagerFacade;
import i5.las2peer.services.servicePackage.database.DatabaseManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minidev.json.JSONObject;

/**
 * LAS2peer Service
 * 
 * This is a template for a very basic LAS2peer service
 * that uses the LAS2peer Web-Connector for RESTful access to it.
 * 
 */
@Path("example")
@Version("0.1")
public class ServiceClass extends Service {

	private String jdbcDriverClassName;
	private String jdbcLogin;
	private String jdbcPass;
	private String jdbcUrl;
	private String jdbcSchema;
	private DatabaseManager dbm;
    private Connection conn;
    private ManagerFacade mf;


    public ServiceClass() {
		// read and set properties values
		// IF THE SERVICE CLASS NAME IS CHANGED, THE PROPERTIES FILE NAME NEED TO BE CHANGED TOO!
		setFieldValues();
		// instantiate a database manager to handle database connection pooling and credentials
		dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl, jdbcSchema);
        mf = new ManagerFacade(null);

    }

    /**
     * A {@link java.util.logging.Logger} for this class with name for
     * {@link Class#getSimpleName()}.
     */
    private final Logger LOGGER;
    {
        LOGGER = Logger
                .getLogger(getClass().getSimpleName());
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

    @GET
    @Path("getUser/{id}")
    public HttpResponse getUser(final @PathParam("id") long id) {
        return GenericServiceResponse.processGenericRequest(new GenericServiceRequest() {
            public String toJson() throws SQLException {
                return mf.getUser(getConnection(), id);
            }
        });
    }
    @PUT
    @Path("editUser/{id}")
    public HttpResponse editUser(final @PathParam("id") long id, final @ContentParam String content) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException, JsonParseException {
            return mf.editUser(getConnection(), id, content);
        } } );
    }
    /*@DELETE
    @Path("deleteUser/{id}")
    public HttpResponse deleteUser(final @PathParam("id") long id) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException {
            return mf.deleteUser(getConnection(), id);
        } } );
    } */
    @GET
    @Path("hashtag/{id}")
    public HttpResponse getHashtag(final @PathParam("id") long id) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException {
            return mf.getHashtag(getConnection(), id);
        } } );
    }
    @POST
    @Path("createHashtag")
    public HttpResponse createHashtag(final @ContentParam String content) {
        return GenericServiceResponse.processGenericRequest( new GenericServiceRequest() { public String toJson() throws SQLException, JsonParseException {
            return mf.createHashtag(getConnection(), content);
        } } );
    }  /*
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

	/**
	 * Another example method.
	 * 
	 * @param myInput
	 * 
	 */
	@POST
	@Path("myMethodPath/{input}")
	public HttpResponse exampleMethod(@PathParam("input") String myInput) {
		String returnString = "";
		returnString += "You have entered " + myInput + "!";
		
		HttpResponse res = new HttpResponse(returnString);
		res.setStatus(200);
		return res;
		
	}

	/**
	 * Example method that shows how to retrieve a user email address from a database 
	 * and return an HTTP response including a JSON object.
	 * 
	 * WARNING: THIS METHOD IS ONLY FOR DEMONSTRATIONAL PURPOSES!!! 
	 * IT WILL REQUIRE RESPECTIVE DATABASE TABLES IN THE BACKEND, WHICH DON'T EXIST IN THE TEMPLATE.
	 * 
	 */
	@GET
	@Path("getUserEmail/{username}")
	public HttpResponse getUserEmail(@PathParam("username") String username) {
		String result = "";
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			// get connection from connection pool
			conn = dbm.getConnection();
			
			// prepare statement
			stmnt = conn.prepareStatement("SELECT email FROM users WHERE username = ?;");
			stmnt.setString(1, username);
			
			// retrieve result set
			rs = stmnt.executeQuery();
			
			// process result set
			if (rs.next()) {
				result = rs.getString(1);
				
				// setup resulting JSON Object
				JSONObject ro = new JSONObject();
				ro.put("email", result);
				
				// return HTTP Response on success
				HttpResponse r = new HttpResponse(ro.toJSONString());
				r.setStatus(200);
				return r;
				
			} else {
				result = "No result for username " + username;
				
				// return HTTP Response on error
				HttpResponse er = new HttpResponse(result);
				er.setStatus(404);
				return er;
			}
		} catch (Exception e) {
			// return HTTP Response on error
			HttpResponse er = new HttpResponse("Internal error: " + e.getMessage());
			er.setStatus(500);
			return er;
		} finally {
			// free resources
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					Context.logError(this, e.getMessage());
					
					// return HTTP Response on error
					HttpResponse er = new HttpResponse("Internal error: " + e.getMessage());
					er.setStatus(500);
					return er;
				}
			}
			if (stmnt != null) {
				try {
					stmnt.close();
				} catch (Exception e) {
					Context.logError(this, e.getMessage());
					
					// return HTTP Response on error
					HttpResponse er = new HttpResponse("Internal error: " + e.getMessage());
					er.setStatus(500);
					return er;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					Context.logError(this, e.getMessage());
					
					// return HTTP Response on error
					HttpResponse er = new HttpResponse("Internal error: " + e.getMessage());
					er.setStatus(500);
					return er;
				}
			}
		}
	}

	/**
	 * Example method that shows how to change a user email address in a database.
	 * 
	 * WARNING: THIS METHOD IS ONLY FOR DEMONSTRATIONAL PURPOSES!!! 
	 * IT WILL REQUIRE RESPECTIVE DATABASE TABLES IN THE BACKEND, WHICH DON'T EXIST IN THE TEMPLATE.
	 * 
	 */
	@POST
	@Path("setUserEmail/{username}/{email}")
	public HttpResponse setUserEmail(@PathParam("username") String username, @PathParam("email") String email) {
		
		String result = "";
		Connection conn = null;
		PreparedStatement stmnt = null;
		ResultSet rs = null;
		try {
			conn = dbm.getConnection();
			stmnt = conn.prepareStatement("UPDATE users SET email = ? WHERE username = ?;");
			stmnt.setString(1, email);
			stmnt.setString(2, username);
			int rows = stmnt.executeUpdate(); // same works for insert
			result = "Database updated. " + rows + " rows affected";
			
			// return 
			HttpResponse r = new HttpResponse(result);
			r.setStatus(200);
			return r;
			
		} catch (Exception e) {
			// return HTTP Response on error
			HttpResponse er = new HttpResponse("Internal error: " + e.getMessage());
			er.setStatus(500);
			return er;
		} finally {
			// free resources if exception or not
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					Context.logError(this, e.getMessage());
					
					// return HTTP Response on error
					HttpResponse er = new HttpResponse("Internal error: " + e.getMessage());
					er.setStatus(500);
					return er;
				}
			}
			if (stmnt != null) {
				try {
					stmnt.close();
				} catch (Exception e) {
					Context.logError(this, e.getMessage());
					
					// return HTTP Response on error
					HttpResponse er = new HttpResponse("Internal error: " + e.getMessage());
					er.setStatus(500);
					return er;
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					Context.logError(this, e.getMessage());
					
					// return HTTP Response on error
					HttpResponse er = new HttpResponse("Internal error: " + e.getMessage());
					er.setStatus(500);
					return er;
				}
			}
		}
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
