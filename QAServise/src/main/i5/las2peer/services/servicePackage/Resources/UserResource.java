package i5.las2peer.services.servicePackage.Resources;

import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
import i5.las2peer.services.servicePackage.Exceptions.NotWellFormedException;
import i5.las2peer.services.servicePackage.Manager.ManagerFacade;
import i5.las2peer.restMapper.HttpResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

/**
 * Created by Marv on 12.11.2014.
 */
public class UserResource extends AbstractResource {

    public UserResource(Connection conn) {
        super(conn);
    }

    public HttpResponse getUserCollection() {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            List<UserDTO> user = ManagerFacade.getInstance().getUserList(conn);

            Gson gson = new Gson();
            String json = gson.toJson(user);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;
    }

    public HttpResponse addUser(UserDTO user) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            Long users = ManagerFacade.getInstance().addUser(conn, user);

            Gson gson = new Gson();
            String json = gson.toJson(users);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        
          catch (CantInsertException e){
        	  response.setStatus(304);
          }

        return response;
    }

    public HttpResponse getUser(long userId) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            UserDTO user = ManagerFacade.getInstance().getUser(conn, userId);

            Gson gson = new Gson();
            String json = gson.toJson(user);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;
    }
    

    public HttpResponse editUser(UserDTO user) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            ManagerFacade.getInstance().editUser(conn, user);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        
          catch (CantUpdateException e){
        	  response.setStatus(304);
          }
        
          catch (NotWellFormedException e){
        	  
        	  response.setStatus(400);
          }
        
        return response;
    	
    }

    public HttpResponse deleteUser(long UserId) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            ManagerFacade.getInstance().deleteUser(conn, UserId);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        
          catch (CantUpdateException e){
        	  response.setStatus(304);
          }
        
        return response;
    }

    public HttpResponse questionBookmarks(long UserId) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            List<QuestionDTO> user = ManagerFacade.getInstance().bookmarkedQuestions(conn, UserId);

            Gson gson = new Gson();
            String json = gson.toJson(user);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;
    }

    public HttpResponse addBookmark(long UserId, long QuestionId) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            ManagerFacade.getInstance().bookmark(conn, UserId, QuestionId);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        
          catch (CantInsertException e){
        	  response.setStatus(304);
          }
        
        return response;
    }
}
