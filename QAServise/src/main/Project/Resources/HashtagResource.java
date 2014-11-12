package Project.Resources;

import Project.DTO.HashtagDTO;
import Project.Manager.ManagerFacade;

import com.google.gson.Gson;

import i5.las2peer.restMapper.HttpResponse;

import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Marv on 12.11.2014.
 */
public class HashtagResource extends AbstractResource {

    //TODO in der serviceclass wrappen
    public HttpResponse getHashtagCollection() {
        List<HashtagDTO> allHashtags = ManagerFacade.getInstance().getHashtags();

        //TODO
        Gson gson = new Gson();
        String json = "";
        HttpResponse response = new HttpResponse(json);
        //TODO

        int status = 404;
        response.setStatus(status);


        return response;

    }
    
    public HttpResponse addNewHashtag(String token, String text) {
    	
    	throw new NotImplementedException();
    	
    }
    
    public HttpResponse getOneHashtag(String token, long hashtagId) {
    	
    	throw new NotImplementedException();
    }
    
    public HttpResponse updateHashtag(String token, HashtagDTO hashtagDTO) {
    	
    	throw new NotImplementedException();
    	
    }
    
    public HttpResponse deleteHashtag(String token, long hashtagId) {
    	
    	throw new NotImplementedException();
    }
    
    public HttpResponse getAllQuestionsToHashtag(String token) {
    	
    	throw new NotImplementedException();
    }
    
    public HttpResponse getAllExpertisesToHashtag(String token) {
    	
    	throw new NotImplementedException();
    }
    
    public HttpResponse addExpertiseToHashtag(String token, long expertiseId) {
    	
    	throw new NotImplementedException();
    }

}
