package i5.las2peer.services.servicePackage.Manager;

import com.google.gson.JsonElement;
import i5.las2peer.services.servicePackage.DTO.*;
import i5.las2peer.services.servicePackage.Exceptions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Marv on 12.11.2014.
 */

//Singleton
public class ManagerFacade {

    private static ManagerFacade instance;

    private UserManager userManager;
    private HashtagManager hashtagManager;
    private QuestionManager questionManager;
    private AnswerManager answerManager;

    private ManagerFacade() {
        userManager = new UserManager();
        hashtagManager = new HashtagManager();
        questionManager = new QuestionManager();
        answerManager = new AnswerManager();
    }

    public static ManagerFacade getInstance() {
        if (instance == null)
            ManagerFacade.instance = new ManagerFacade();
        return instance;
    }

    ////// userManager //////
    public UserDTO getUser(Connection conn, long userId) throws SQLException, CantFindException {
        return userManager.getUser(conn, userId);
    }
    public boolean registerUser(Connection conn, UserDTO userDTO) throws SQLException, CantInsertException {
        return userManager.registerUser(conn, userDTO);
    }
    public void editUser(Connection conn, long userId, UserDTO data) throws SQLException, CantUpdateException {
        userManager.editUser(conn, userId, data);
    }

    public List<QuestionDTO> bookmarkedQuestions( Connection conn, long userId) throws SQLException {
        return userManager.bookmarkedQuestions(conn, userId);
    }

    public void bookmark( Connection conn, long userId, long questionId) throws SQLException, CantInsertException, CantFindException {
        userManager.bookmark(conn, userId, questionId);
    }

        ////// hashtagManager //////
    public List<HashtagDTO> getHashtagCollection( Connection conn) throws SQLException, CantFindException {
        return hashtagManager.getHashtagCollection(conn);
    }
    public List<QuestionDTO> getAllQuestionsToHashtag(Connection conn, long hashtagId, long userThatAsksId) throws SQLException {
        return hashtagManager.getAllQuestionsToHashtag(conn, hashtagId, userThatAsksId);
    }
    
    ////// questionManager //////
    public List<QuestionDTO> getQuestionList( Connection conn, long userThatAsksId) throws SQLException {
        return questionManager.getQuestionList(conn, userThatAsksId);
    }
    public long addQuestion( Connection conn, QuestionDTO question) throws SQLException, CantInsertException, CantFindException, CantUpdateException {
        return questionManager.addQuestion(conn, question);
    }
    /*public List<AnswerDTO> getAnswersToQuestion( Connection conn, long questionId) throws SQLException {
        return questionManager.getAnswersToQuestion(conn, questionId);
    }*/
    public Map<String, Object> getQuestionWithAnswers(Connection conn, long questionId, long userThatAsksId) throws SQLException, CantFindException {
        return questionManager.getQuestionWithAnswers(conn, questionId, userThatAsksId);
    }
    public long addAnswerToQuestion(Connection conn, AnswerDTO answer) throws SQLException, CantInsertException {
        // check question exists
        return questionManager.addAnswerToQuestion(conn, answer);
    }

    ////// answerManager //////
    public List<QuestionDTO> getExpertiseQuestions(Connection conn, long userId) throws SQLException {
        return userManager.getExpertQuestions(conn, userId);
    }

    public void upvoteAnswer(Connection conn, long userId, long answerId) throws SQLException, CantFindException, CantInsertException, CantUpdateException {
        answerManager.upvoteAnswer(conn, answerId, userId);
    }
}

