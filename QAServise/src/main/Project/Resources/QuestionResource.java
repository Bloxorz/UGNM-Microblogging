package Project.Resources;

import Project.DTO.AnswerDTO;
import Project.DTO.QuestionDTO;
import Project.Manager.ManagerFacade;
import com.google.gson.Gson;
import i5.las2peer.restMapper.HttpResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Marv on 12.11.2014.
 */
public class QuestionResource extends AbstractResource {


    public QuestionResource(Connection conn) {
        super(conn);
    }

    public HttpResponse getQuestionCollection(String token) {
        HttpResponse response = new HttpResponse("");
        try {
            List<QuestionDTO> questions = ManagerFacade.getInstance().getQuestionList(token, conn);

            Gson gson = new Gson();
            String json = gson.toJson(questions);
            response = new HttpResponse("json");
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;
    }

    public HttpResponse addQuestion(String token, QuestionDTO question) {
        throw new NotImplementedException();
    }

    public HttpResponse getQuestion(String token, long questionId) {
        throw new NotImplementedException();
    }

    public HttpResponse editQuestion(String token, QuestionDTO question) {
        throw new NotImplementedException();
    }

    public HttpResponse deleteQuestion(String token, long questionId) {
        throw new NotImplementedException();
    }

    public HttpResponse getAnswersToQuestion(String token,long questionId) {
        throw new NotImplementedException();
    }

    public HttpResponse addAnswerToQuestion(String token, AnswerDTO answer) {
        throw new NotImplementedException();
    }

    public HttpResponse getBookmarkUsersToQuestion(String token, long questionId) {
    	throw new NotImplementedException();
    }

}
