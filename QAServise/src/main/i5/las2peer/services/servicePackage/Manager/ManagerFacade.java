package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.*;
import i5.las2peer.services.servicePackage.Exceptions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marv on 12.11.2014.
 */

//Singleton
public class ManagerFacade {

    private static ManagerFacade instance;


    public static String IGNORE_AUTHENTIFICATION ="masterkey_123456789asdfghjklöqwertzuioxcvbnjhgfdssxdcvjuztrdxsgcfdrtgvcdrgcxs";
    private UserManager userManager;
    private ExpertiseManager expertiseManager;
    private HashtagManager hashtagManager;
    private QuestionManager questionManager;
    private AnswerManager answerManager;

    private ManagerFacade() {
        userManager = new UserManager();
        expertiseManager = new ExpertiseManager();
        hashtagManager = new HashtagManager();
        questionManager = new QuestionManager();
        answerManager = new AnswerManager();
    }

    public static ManagerFacade getInstance() {
        if (instance == null)
            ManagerFacade.instance = new ManagerFacade();
        return instance;
    }

    public List<HashtagDTO> getHashtags(String token) {
        List<HashtagDTO> result = new ArrayList<HashtagDTO>();
        //TODO
        return result;
    }

    ////// userManager //////
    public List<UserDTO> getUserList(String token,Connection conn) throws SQLException {
        return userManager.getUserList(conn);
    }

    public long addUser(String token,Connection conn, UserDTO user) throws SQLException, CantInsertException {
        return userManager.addUser(conn, user);
    }

    public UserDTO getUser(String token,Connection conn, long userId) throws SQLException {
        return userManager.getUser(conn, userId);
    }

    public void editUser(String token,Connection conn, UserDTO user) throws SQLException, NotWellFormedException, CantUpdateException {
        userManager.editUser(conn, user);
    }

    public void deleteUser(String token,Connection conn, long userId) throws SQLException, CantUpdateException {
        userManager.deleteUser(conn, userId);
    }

    public List<QuestionDTO> bookmarkedQuestions(String token, Connection conn, long userId) throws SQLException {
        return userManager.bookmarkedQuestions(conn, userId);
    }

    public void bookmark(String token, Connection conn, long userId, long questionId) throws SQLException, CantInsertException {
        userManager.bookmark(conn, userId, questionId);
    }
    ////// expertiseManager //////
    public List<ExpertiseDTO> getExpertiseList(String token, Connection conn) throws SQLException {
        return expertiseManager.getExpertiseList(conn);
    }

    public long addExpertise(String token, Connection conn, ExpertiseDTO expertise, List<HashtagDTO> hashtags) throws SQLException, NotWellFormedException, CantInsertException {
        return expertiseManager.addExpertise(conn, expertise, hashtags);
    }

    public ExpertiseDTO getExpertise(String token, Connection conn, long expertiseId) throws SQLException {
        return expertiseManager.getExpertise(conn, expertiseId);
    }

    public void editExpertise(String token, Connection conn, ExpertiseDTO expertise) throws NotWellFormedException, SQLException, CantUpdateException {
       expertiseManager.editExpertise(conn, expertise);
    }
        ////// hashtagManager //////
    public List<HashtagDTO> getHashtagCollection(String token, Connection conn) throws SQLException {
        return hashtagManager.getHashtagCollection(conn);
    }
    public HashtagDTO getHashtag(String token, Connection conn, long hashtagId) throws SQLException {
        return hashtagManager.getHashtag(conn, hashtagId);
    }

    public void updateHashtag(String token,Connection conn, HashtagDTO hashtag) throws SQLException {
        hashtagManager.updateHashtag(conn, hashtag);
    }
    public void deleteHashtag(String token,Connection conn, long hashtagId) throws SQLException {
        hashtagManager.deleteHashtag(conn, hashtagId);
    }
    public List<HashtagDTO> getAllQuestionsToHashtag(String token,Connection conn, long hashtagId) throws SQLException {
        return hashtagManager.getAllQuestionsToHashtag(conn, hashtagId);
    }

    public boolean existsHashtag(String token, Connection conn, String text) throws SQLException {
        //TODO Authentification

        return hashtagManager.existsHashtag(conn, text);
    }


    public long addHashtag(String token, Connection conn, String text) throws SQLException, CantInsertException {
        //TODO Authentification

        return hashtagManager.addHashtag(conn, text);

    }
    
    ////// questionManager //////
    public List<QuestionDTO> getQuestionList(String token, Connection conn) throws SQLException {
        return questionManager.getQuestionList(conn);
    }
    public long addQuestion(String token, Connection conn, QuestionDTO question) throws SQLException, CantInsertException {
        return questionManager.addQuestion(conn, question);
    }
    public QuestionDTO getQuestion(String token, Connection conn, long questionId) throws SQLException {
        return questionManager.getQuestion(conn, questionId);
    }
    public void editQuestion(String token, Connection conn, String questionText) throws SQLException, CantUpdateException {
        questionManager.editQuestion(conn, questionText);
    }
    public void deleteQuestion(String token, Connection conn, long questionId) throws SQLException, CantDeleteException {
        questionManager.deleteQuestion(conn, questionId);
    }
    public List<AnswerDTO> getAnswersToQuestion(String token, Connection conn, long questionId) throws SQLException {
        return questionManager.getAnswersToQuestion(conn, questionId);
    }
    public long addAnswerToQuestion(String token, Connection conn, AnswerDTO answer) throws SQLException, CantInsertException {
        return questionManager.addAnswerToQuestion(conn, answer);
    }
    public List<UserDTO> getBookmarkUsersToQuestion(String token, Connection conn, long questionId) throws SQLException {
        return questionManager.getBookmarkUsersToQuestion(conn, questionId);
    }
    ////// answerManager //////
    public AnswerDTO getAnswer(String token, Connection conn, long answerId) throws SQLException {
        return answerManager.getAnswer(conn, answerId);
    }
    public void editAnswer(String token, Connection conn, long answerId, String answerText, int rating) throws SQLException, CantUpdateException {
        answerManager.editAnswer(conn, answerId, answerText, rating);
    }
    public void deleteAnswer(String token, Connection conn, long answerId) throws SQLException, CantDeleteException {
        answerManager.deleteAnswer(conn, answerId);
    }
}

