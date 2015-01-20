package i5.las2peer.services.servicePackage.Resources;

import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.Exceptions.*;
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
	
    public HttpResponse getHashtagCollection() {
    	
    	HttpResponse response = new HttpResponse("");
        try {
            List<HashtagDTO> hashtag = ManagerFacade.getInstance().getHashtagCollection(conn);

            Gson gson = new Gson();
            String json = gson.toJson(hashtag);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;

    }
    
    public HttpResponse addNewHashtag(String text) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
              Long hashtag = ManagerFacade.getInstance().addNewHashtag(conn, text);

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
    
    public HttpResponse getOneHashtag(long hashtagId) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
    		  HashtagDTO hashtag = ManagerFacade.getInstance().getHashtag(conn, hashtagId);

              Gson gson = new Gson();
              String json = gson.toJson(hashtag);
              response = new HttpResponse(json);
              response.setStatus(200);

         } catch (SQLException e) {
             response.setStatus(500);
        } catch (CantFindException e) {
            response.setStatus(404);
        }
    		
    	return response;
    }
    
    public HttpResponse updateHashtag(HashtagDTO hashtagDTO) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
              ManagerFacade.getInstance().updateHashtag(conn, hashtagDTO);
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

            catch (CantFindException e) {
                response.setStatus(404);
            }
    	return response;
    	
    }
    
    public HttpResponse deleteHashtag(long hashtagId) {
    	
    	HttpResponse response = new HttpResponse("");
    	try {
    		  ManagerFacade.getInstance().deleteHashtag(conn, hashtagId);

              response.setStatus(200);

         } catch (SQLException e) {
             response.setStatus(500);
            }
    		
    		catch (CantDeleteException e){
    			response.setStatus(304);
    		}
        catch (CantFindException e) {
            response.setStatus(404);
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
    
    public HttpResponse addExpertiseToHashtag(long expertiseId) {
    	
    	throw new NotImplementedException();
    }

}
