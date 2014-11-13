package i5.las2peer.services.servicePackage.Resources;

import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
<<<<<<< HEAD
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
=======
>>>>>>> dca1c9b... UserResource update
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

    public HttpResponse getUserCollection(String token) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            List<UserDTO> user = ManagerFacade.getInstance().getUserList(token, conn);

            Gson gson = new Gson();
            String json = gson.toJson(user);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;
    }

    public HttpResponse addUser(String token, UserDTO user) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            Long users = ManagerFacade.getInstance().addUser(token, conn, user);

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
<<<<<<< HEAD

=======
        
          catch (NotWellFormedException e){
        	  
        	  response.setStatus(400);
          }
        
>>>>>>> dca1c9b... UserResource update
        return response;
    }

    public HttpResponse getUser(String token, long userId) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            UserDTO user = ManagerFacade.getInstance().getUser(token, conn, userId);

            Gson gson = new Gson();
            String json = gson.toJson(user);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;
    }
    

    public HttpResponse editUser(String token, UserDTO user) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            ManagerFacade.getInstance().editUser(token, conn, user);
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

    public HttpResponse deleteUser(String token, long UserId) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            ManagerFacade.getInstance().deleteUser(token, conn, UserId);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        
          catch (CantUpdateException e){
        	  response.setStatus(304);
          }
        
        return response;
    }

    public HttpResponse questionBookmarks(String token, long UserId) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            List<QuestionDTO> user = ManagerFacade.getInstance().bookmarkedQuestions(token, conn, UserId);

            Gson gson = new Gson();
            String json = gson.toJson(user);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;
    }

    public HttpResponse addBookmark(String token, long UserId, long QuestionId) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            ManagerFacade.getInstance().bookmark(token, conn, UserId, QuestionId);
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
