package Project.Manager;

import java.sql.SQLException;
import Project.Exceptions.HTTPNotFoundException;
import com.google.gson.JsonParseException;

import i5.las2peer.restMapper.HttpResponse;

/**
 * Transforms a jason-String and a set of possible Exceptions to a HTTPResponse to be used from the MicrobloggingService.
 */
 public class GenericServiceResponse {
    public static HttpResponse processGenericRequest(GenericServiceRequest sr){
        HttpResponse response;
        try {
            response = new HttpResponse( sr.toJson() );
            //successful
            response.setStatus(200);
        } catch (HTTPNotFoundException e) {
            response = new HttpResponse("");
            response.setStatus(404);
        } catch (SQLException e) {
            response = new HttpResponse("");
            response.setStatus(500);
        } catch (JsonParseException e) {
            response = new HttpResponse(e.getMessage());
            response.setStatus(400);
        }
        return response;
    }
 }