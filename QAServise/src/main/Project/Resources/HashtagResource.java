package Project.Resources;

import Project.DTO.HashtagDTO;
import Project.Manager.ManagerFacade;
import com.google.gson.Gson;
import i5.las2peer.restMapper.HttpResponse;

import java.util.List;

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
    
    public HttpResponse addNewHashtag() {
    	
    	//TODO
        Gson gson = new Gson();
        String json = "";
        HttpResponse response = new HttpResponse(json);
        //TODO

        int status = 404;
        response.setStatus(status);


        return response;
    	
    }
    
    public HttpResponse getOneHashtag() {
    	
    	//TODO
        Gson gson = new Gson();
        String json = "";
        HttpResponse response = new HttpResponse(json);
        //TODO

        int status = 404;
        response.setStatus(status);


        return response;
    }
    
    public HttpResponse updateHashtag() {
    	
    	//TODO
        Gson gson = new Gson();
        String json = "";
        HttpResponse response = new HttpResponse(json);
        //TODO

        int status = 404;
        response.setStatus(status);


        return response;
    	
    }
    
    public HttpResponse deleteHashtag() {
    	
    	//TODO
        Gson gson = new Gson();
        String json = "";
        HttpResponse response = new HttpResponse(json);
        //TODO

        int status = 404;
        response.setStatus(status);


        return response;
    	
    }
    
    public HttpResponse getAllQuestionsToHashtag() {
    	
    	//TODO
        Gson gson = new Gson();
        String json = "";
        HttpResponse response = new HttpResponse(json);
        //TODO

        int status = 404;
        response.setStatus(status);


        return response;
        
    }
    
    public HttpResponse getAllExpertisesToHashtag() {
    	
    	//TODO
        Gson gson = new Gson();
        String json = "";
        HttpResponse response = new HttpResponse(json);
        //TODO

        int status = 404;
        response.setStatus(status);


        return response;
    }
    
    public HttpResponse addExpertiseToHashtag() {
    	
    	//TODO
        Gson gson = new Gson();
        String json = "";
        HttpResponse response = new HttpResponse(json);
        //TODO

        int status = 404;
        response.setStatus(status);


        return response;
    	
    }

}
