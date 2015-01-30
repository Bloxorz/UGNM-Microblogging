package i5.las2peer.services.servicePackage.Resources;

import com.google.gson.GsonBuilder;
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

    public HttpResponse getHashtagCollection() {
        try {
            List<HashtagDTO> hashtags = ManagerFacade.getInstance().getHashtagCollection(conn);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return new HttpResponse(gson.toJson(hashtags), 200);
        } catch (SQLException e) {
           return new HttpResponse(e.toString(), 500);
        } catch (CantFindException e) {
            return new HttpResponse(e.toString(), 404);
        }
    }
    
    public HttpResponse getAllQuestionsToHashtag(long hashtagId, long userThatAsksId) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String json = gson.toJson(ManagerFacade.getInstance().getAllQuestionsToHashtag(conn, hashtagId, userThatAsksId));
            return new HttpResponse(json, 200);
        } catch (SQLException e) {
            return new HttpResponse(e.toString(), 500);
        }
    }
}
