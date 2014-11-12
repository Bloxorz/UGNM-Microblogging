package i5.las2peer.services.servicePackage.Resources;

import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.restMapper.HttpResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;

/**
 * Created by Marv on 12.11.2014.
 */
public class ExpertiseResource extends AbstractResource {

    public ExpertiseResource(Connection conn) {
        super(conn);
    }

    public HttpResponse getExpertiseCollection(String token) {
        throw  new NotImplementedException();
    }

    public HttpResponse addExpertise(String token, ExpertiseDTO expertise) {
        throw  new NotImplementedException();
    }

    public HttpResponse getExpertise(String token, long expertiseId) {
        throw  new NotImplementedException();
    }

    public HttpResponse deleteExpertise(String token, long expertiseId) {
        throw  new NotImplementedException();
    }

    public HttpResponse editExpertise(String token, ExpertiseDTO expertise) {
        throw  new NotImplementedException();
    }

    public HttpResponse getHashtagsToExpertise(String token, long expertiseId) {
        throw  new NotImplementedException();
    }

    public HttpResponse assignHashtagToExpertise(String token, HashtagDTO hashtag, long expertiseId) {
        throw  new NotImplementedException();
    }
}
