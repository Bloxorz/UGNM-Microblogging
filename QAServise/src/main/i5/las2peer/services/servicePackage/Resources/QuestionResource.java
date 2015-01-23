package i5.las2peer.services.servicePackage.Resources;

import com.google.gson.JsonParseException;
import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantDeleteException;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
import i5.las2peer.services.servicePackage.Manager.ManagerFacade;
import com.google.gson.Gson;
import i5.las2peer.restMapper.HttpResponse;
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

    public HttpResponse getQuestionCollection() {
        HttpResponse response = new HttpResponse("");
        try {
            List<QuestionDTO> questions = ManagerFacade.getInstance().getQuestionList(conn);

            Gson gson = new Gson();
            String json = gson.toJson(questions);
            response = new HttpResponse(json);
            response.setStatus(200);

        } catch (SQLException e) {
           response.setStatus(500);
        }
        return response;
    }

    public HttpResponse addQuestion(QuestionDTO question) {
        HttpResponse res;
        try {
            Long generatedId = ManagerFacade.getInstance().addQuestion(conn, question);

            res = new HttpResponse(generatedId.toString());
            res.setStatus(201);
            return res;
        } catch (SQLException e) {
           res = new HttpResponse("");
            res.setStatus(500);
        } catch (CantInsertException e) {
            res = new HttpResponse("");
            res.setStatus(500);
        }

        res = new HttpResponse("");
        res.setStatus(500);
        return res;
    }

    public HttpResponse getQuestion(long questionId) {
        HttpResponse res;
        try {
            QuestionDTO question = ManagerFacade.getInstance().getQuestion(conn, questionId);
            if(question == null) {
                res = new HttpResponse("null");
                res.setStatus(404);
                return res;
            }
            Gson gson = new Gson();

            res = new HttpResponse(gson.toJson(question));
            res.setStatus(200);

            return  res;
        } catch (SQLException e) {
            res = new HttpResponse("not found");
            res.setStatus(500);
            return  res;
        } catch (CantFindException e) {
            res = new HttpResponse("not found");
            res.setStatus(404);
            return  res;
        }
    }

    public HttpResponse editQuestion(long questionId, String content) {
        HttpResponse response = new HttpResponse("");
        try {
            QuestionDTO question = (QuestionDTO) new Gson().fromJson(content, QuestionDTO.class);
            ManagerFacade.getInstance().editQuestion(conn, questionId, question.getText());

            response.setStatus(200);

        } catch (CantUpdateException e) {
            response.setStatus(500);
        } catch (JsonParseException e) {
            response.setStatus(400);
        } catch (SQLException e) {
            response.setStatus(500);
        }
        return response;
    }

    public HttpResponse deleteQuestion(long questionId) {
        HttpResponse res = new HttpResponse("");

        try {
            ManagerFacade.getInstance().deleteQuestion(conn, questionId);
            res.setStatus(200);
        } catch (SQLException e) {
            res.setStatus(500);
        } catch (CantDeleteException e) {
            res.setStatus(500);
        }

        return res;
    }

    public HttpResponse getAnswersToQuestion(long questionId) {
        HttpResponse res;
        try {
            List<AnswerDTO> answers = ManagerFacade.getInstance().getAnswersToQuestion(conn, questionId);
            if(answers == null) {
                res = new HttpResponse("");
                res.setStatus(404);
                return res;
            }
            Gson gson = new Gson();

            res = new HttpResponse(gson.toJson(answers));
            res.setStatus(200);

            return  res;
        } catch (SQLException e) {
            res = new HttpResponse("not found");
            res.setStatus(500);
            return  res;
        }
    }

    public HttpResponse addAnswerToQuestion(long questionId, String content) {

        HttpResponse res;
        try {
            AnswerDTO answer = (AnswerDTO) new Gson().fromJson(content, AnswerDTO.class);
            Long generatedId = ManagerFacade.getInstance().addAnswerToQuestion(conn, answer);

            res = new HttpResponse(generatedId.toString());
            res.setStatus(201);
            return res;
        } catch (JsonParseException e) {
            res = new HttpResponse("");
            res.setStatus(400);
        } catch (SQLException e) {
            res = new HttpResponse("");
            res.setStatus(500);
        } catch (CantInsertException e) {
            res = new HttpResponse("");
            res.setStatus(500);
        }

        res = new HttpResponse("");
        res.setStatus(500);
        return res;
    }

    public HttpResponse getBookmarkUsersToQuestion(long questionId) {
        HttpResponse res;
        try {
            List<UserDTO> users = ManagerFacade.getInstance().getBookmarkUsersToQuestion(conn, questionId);
            if(users == null) {
                res = new HttpResponse("");
                res.setStatus(404);
                return res;
            }
            Gson gson = new Gson();

            res = new HttpResponse(gson.toJson(users));
            res.setStatus(200);

            return  res;
        } catch (SQLException e) {
            res = new HttpResponse("not found");
            res.setStatus(500);
            return  res;
        }
    }

}
