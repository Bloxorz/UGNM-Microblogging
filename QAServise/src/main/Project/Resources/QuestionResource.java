package Project.Resources;

import Project.DTO.QuestionDTO;
import i5.las2peer.restMapper.HttpResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Marv on 12.11.2014.
 */
public class QuestionResource extends AbstractResource {

    public HttpResponse getQuestionCollection(String token) {
        throw new NotImplementedException();
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

    public HttpResponse addAnswerToQuestion(String token, QuestionDTO question) {
        throw new NotImplementedException();
    }

    public HttpResponse getBookmarkUsersToQuestion(String token, long questionId) {
        
    }

}
