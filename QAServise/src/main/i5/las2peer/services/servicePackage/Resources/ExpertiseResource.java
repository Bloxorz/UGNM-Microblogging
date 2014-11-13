package i5.las2peer.services.servicePackage.Resources;

import com.google.gson.Gson;
import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.Exceptions.*;
import i5.las2peer.services.servicePackage.Manager.ManagerFacade;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marv on 12.11.2014.
 */
public class ExpertiseResource extends AbstractResource {

    public ExpertiseResource(Connection conn) {
        super(conn);
    }

    public HttpResponse getExpertiseCollection(String token) {
        HttpResponse response = new HttpResponse("");
        try {
            List<ExpertiseDTO> expertise = ManagerFacade.getInstance().getExpertiseList(token, conn);

            Gson gson = new Gson();
            String json = gson.toJson(expertise);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
            response.setStatus(500);
        }
        return response;
    }

    public HttpResponse addExpertise(String token, ExpertiseDTO expertise) {
        HttpResponse response = new HttpResponse("");
        try {

            List<HashtagDTO> hashtags = ManagerFacade.getInstance().getHashtags(token);
            long id = ManagerFacade.getInstance().addExpertise(token, conn, expertise, hashtags);

            String json = "{'id': " + id + "}";
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
            response.setStatus(500);
        } catch (CantInsertException e) {
            response.setStatus(304);
        } catch (NotWellFormedException e) {
            response.setStatus(400);
        }

        return response;
    }

    public HttpResponse getExpertise(String token, long expertiseId) {
        HttpResponse response = new HttpResponse("");
        try {
             ExpertiseDTO exp = ManagerFacade.getInstance().getExpertise(token, conn, expertiseId);

            Gson gson = new Gson();
            String json = gson.toJson(exp);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
            response.setStatus(500);
        } catch (HTTPNotFoundException e) {
            response.setStatus(404);
        }

        return response;
    }

    public HttpResponse deleteExpertise(String token, long expertiseId) {
        throw  new NotImplementedException();
    }

    public HttpResponse editExpertise(String token, ExpertiseDTO expertise) {
        HttpResponse response = new HttpResponse("");
        try {
           ManagerFacade.getInstance().editExpertise(token, conn, expertise);

            response.setStatus(200);

        } catch (SQLException e) {
            response.setStatus(500);
        } catch (NotWellFormedException e) {
            response.setStatus(400);
        } catch (CantUpdateException e) {
            response.setStatus(304);
        }

        return response;
    }

    public HttpResponse getHashtagsToExpertise(String token, long expertiseId) {
        throw  new NotImplementedException();
    }

    public HttpResponse assignHashtagToExpertise(String token, HashtagDTO hashtag, long expertiseId) {
        throw  new NotImplementedException();
    }
}
