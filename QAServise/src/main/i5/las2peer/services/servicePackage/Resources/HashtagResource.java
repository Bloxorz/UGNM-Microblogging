package i5.las2peer.services.servicePackage.Resources;

import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantDeleteException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
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
public class HashtagResource extends AbstractResource {
    public HashtagResource(Connection conn) {
        super(conn);
    }

    //TODO in der serviceclass wrappen
	
    public HttpResponse getHashtagCollection(String token) {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            List<HashtagDTO> hashtag = ManagerFacade.getInstance().getHashtagCollection(token, conn);

            Gson gson = new Gson();
            String json = gson.toJson(hashtag);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;

    }
    
    public HttpResponse addNewHashtag(String token, String text) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
              Long hashtag = ManagerFacade.getInstance().addNewHashtag(token, conn, text);

              Gson gson = new Gson();
              String json = gson.toJson(hashtag);
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
    
    public HttpResponse getOneHashtag(String token, long hashtagId) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
    		  HashtagDTO hashtag = ManagerFacade.getInstance().getHashtag(token, conn, hashtagId);

              Gson gson = new Gson();
              String json = gson.toJson(hashtag);
              response = new HttpResponse(json);
              response.setStatus(200);

         } catch (SQLException e) {
             response.setStatus(500);
            }
    		
    	return response;
    }
    
    public HttpResponse updateHashtag(String token, HashtagDTO hashtagDTO) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
              ManagerFacade.getInstance().updateHashtag(token, conn, hashtagDTO);
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
    
    public HttpResponse deleteHashtag(String token, long hashtagId) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
    		  ManagerFacade.getInstance().deleteHashtag(token, conn, hashtagId);

              response.setStatus(200);

         } catch (SQLException e) {
             response.setStatus(500);
            }
    		
    		catch (CantDeleteException e){
    			response.setStatus(304);
    		}
    	

    		
    	return response;
    }
    
    public HttpResponse getAllQuestionsToHashtag(long hashtagId) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
    		List<QuestionDTO> quest = ManagerFacade.getInstance().getAllQuestionsToHashtag(conn, hashtagId);
    		  
    		  Gson gson = new Gson();
              String json = gson.toJson(quest);
              response = new HttpResponse(json);
              response.setStatus(200);

         } catch (SQLException e) {
             response.setStatus(500);
            }
    	
    	return response;
    }
    
    public HttpResponse getAllExpertisesToHashtag(long hashtagId) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
    		  List<ExpertiseDTO> ex = ManagerFacade.getInstance().getAllExpertiseToHashtag(conn, hashtagId);

    		  Gson gson = new Gson();
              String json = gson.toJson(ex);
              response = new HttpResponse(json);
              response.setStatus(200);

         } catch (SQLException e) {
             response.setStatus(500);
            }
    	
    	return response;
    }
    
    public HttpResponse addExpertiseToHashtag(String token, long expertiseId) {
    	
    	throw new NotImplementedException();
    }

}
