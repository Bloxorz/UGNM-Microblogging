package i5.las2peer.services.servicePackage.Resources;

import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.NotWellFormedException;
import i5.las2peer.services.servicePackage.Manager.ManagerFacade;
import i5.las2peer.restMapper.HttpResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
        
          catch (NotWellFormedException e){
        	  
        	  response.setStatus(400);
          }
        
        return response;
    }

    public HttpResponse getUser(String token, long userId) {
        throw  new NotImplementedException();
    }

    public HttpResponse editUser(String token, UserDTO user) {
        throw  new NotImplementedException();
    }

    public HttpResponse deleteUser(String token, long UserId) {
        throw  new NotImplementedException();
    }

    public HttpResponse questionBookmarks(String token, long UserId) {
        throw  new NotImplementedException();
    }

    public HttpResponse addBookmark(String token, long QuestionId) {
        throw  new NotImplementedException();
    }
}
