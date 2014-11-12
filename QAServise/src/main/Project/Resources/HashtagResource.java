package Project.Resources;

import Project.DTO.HashtagDTO;
import i5.las2peer.restMapper.HttpResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;

/**
 * Created by Marv on 12.11.2014.
 */
public class HashtagResource extends AbstractResource {
    public HashtagResource(Connection conn) {
        super(conn);
    }

    //TODO in der serviceclass wrappen
	
    public HttpResponse getHashtagCollection(String token) {
        throw new NotImplementedException();

    }
    
    public HttpResponse addNewHashtag(String token, String text) {
    	
    	throw new NotImplementedException(); //fertig
    	
    }
    
    public HttpResponse getOneHashtag(String token, long hashtagId) {
    	
    	throw new NotImplementedException(); //fertig
    }
    
    public HttpResponse updateHashtag(String token, HashtagDTO hashtagDTO) {
    	
    	throw new NotImplementedException(); //fertig
    	
    }
    
    public HttpResponse deleteHashtag(String token, long hashtagId) {
    	
    	throw new NotImplementedException(); //fertig
    }
    
    public HttpResponse getAllQuestionsToHashtag(String token, long hashtagId) {
    	
    	throw new NotImplementedException();
    }
    
    public HttpResponse getAllExpertisesToHashtag(String token) {
    	
    	throw new NotImplementedException();
    }
    
    public HttpResponse addExpertiseToHashtag(String token, long expertiseId) {
    	
    	throw new NotImplementedException();
    }

}
