package i5.las2peer.services.servicePackage.Resources;

import com.google.gson.*;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.DTO.PostDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantDeleteException;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
import i5.las2peer.services.servicePackage.Manager.ManagerFacade;
import i5.las2peer.restMapper.HttpResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marv on 12.11.2014.
 */
public class QuestionResource extends AbstractResource {


    public QuestionResource(Connection conn) {
        super(conn);
    }

    public HttpResponse getQuestionCollection(long userThatAsksId) {
        HttpResponse response = new HttpResponse("");
        try {
            List<QuestionDTO> questions = ManagerFacade.getInstance().getQuestionList(conn, userThatAsksId);

            //Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return new HttpResponse(getDefaultGson().toJson(questions), 200);
        } catch (SQLException e) {
           return new HttpResponse(e.toString(), 500);
        }
    }

    public HttpResponse addQuestion(String content, long userId) {
        if(userId == 0) {
            return new HttpResponse("You have to be logged in to add a question!", 401);
        } else {
            try {
                QuestionDTO question = new Gson().fromJson(content, QuestionDTO.class);
                question.setIdUser(userId);
                Map<String, Object> resultmap = new HashMap<String, Object>();
                resultmap.put("idQuestion", ManagerFacade.getInstance().addQuestion(conn, question));
                HttpResponse response = new HttpResponse("Question succesfully posted!", 200);
                response.setResult(new Gson().toJson(resultmap));
                return response;
            } catch (JsonParseException e) {
                return new HttpResponse(e.toString(), 400);
            } catch (SQLException e) {
                return new HttpResponse(e.toString(), 500);
            } catch (CantInsertException e) {
                return new HttpResponse(e.toString(), 500);
            } catch (CantUpdateException e) {
                return new HttpResponse(e.toString(), 500);
            } catch (CantFindException e) {
                return new HttpResponse(e.toString(), 404);
            }
        }
    }
    public HttpResponse addAnswerToQuestion(long questionId, String content, long userId) {

        HttpResponse res;
        try {
            AnswerDTO answer = (AnswerDTO) new Gson().fromJson(content, AnswerDTO.class);
            answer.setIdQuestion(questionId);
            answer.setIdUser(userId);
            ManagerFacade.getInstance().addAnswerToQuestion(conn, answer);
            return new HttpResponse("Answer successfully posted.", 200);
        } catch (JsonParseException e) {
            return new HttpResponse(e.toString(), 400);
        } catch (SQLException e) {
            return new HttpResponse(e.toString(), 500);
        } catch (CantInsertException e) {
            return new HttpResponse(e.toString(), 500);
        }
    }

    public HttpResponse getQuestionWithAnswers(long questionId, long userThatAsksId) {
        HttpResponse res;
        try {
            Map<String, Object> result = ManagerFacade.getInstance().getQuestionWithAnswers(conn, questionId, userThatAsksId);

            return new HttpResponse(getDefaultGson().toJson(result), 200);
        } catch (SQLException e) {
            return new HttpResponse(e.toString(), 500);
        } catch (CantFindException e) {
            return new HttpResponse(e.toString(), 404);
        }
    }
}
