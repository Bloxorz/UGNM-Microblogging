package i5.las2peer.services.servicePackage.Manager;

import com.google.gson.JsonElement;
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

    public List<HashtagDTO> getHashtags() {
        List<HashtagDTO> result = new ArrayList<HashtagDTO>();
        //TODO
        return result;
    }

    ////// userManager //////
    public List<UserDTO> getUserList(Connection conn) throws SQLException {
        return userManager.getUserList(conn);
    }

    public long addUser(Connection conn, UserDTO user) throws SQLException, CantInsertException {
        return userManager.addUser(conn, user);
    }

    public UserDTO getUser(Connection conn, long userId) throws SQLException {
        return userManager.getUser(conn, userId);
    }

    public void deleteUser(Connection conn, long userId) throws SQLException, CantUpdateException {
        userManager.deleteUser(conn, userId);
    }

    public List<QuestionDTO> bookmarkedQuestions( Connection conn, long userId) throws SQLException {
        return userManager.bookmarkedQuestions(conn, userId);
    }

    public void bookmark( Connection conn, long userId, long questionId) throws SQLException, CantInsertException {
        userManager.bookmark(conn, userId, questionId);
    }
    ////// expertiseManager //////
    public List<ExpertiseDTO> getExpertiseList( Connection conn) throws SQLException {
        return expertiseManager.getExpertiseList(conn);
    }

    public long addExpertise( Connection conn, ExpertiseDTO expertise, List<HashtagDTO> hashtags) throws SQLException, NotWellFormedException, CantInsertException {
        return expertiseManager.addExpertise(conn, expertise/*, hashtags*/);
    }

    public ExpertiseDTO getExpertise( Connection conn, long expertiseId) throws SQLException, CantFindException {
        return expertiseManager.getExpertise(conn, expertiseId);
    }

    public void editExpertise( Connection conn, ExpertiseDTO expertise) throws NotWellFormedException, SQLException, CantUpdateException {
       expertiseManager.editExpertise(conn, expertise);
    }

    public void deleteExpertise( Connection conn, long expertiseId) throws CantDeleteException, SQLException, CantFindException {
        expertiseManager.deleteExpertise(conn, expertiseId);
    }
        ////// hashtagManager //////
    public List<HashtagDTO> getHashtagCollection( Connection conn) throws SQLException {
        return hashtagManager.getHashtagCollection(conn);
    }
    public HashtagDTO getHashtag(Connection conn, long hashtagId) throws SQLException, CantFindException {
        return hashtagManager.getHashtag(conn, hashtagId);
    }
    
    public long addNewHashtag( Connection conn, String text) throws SQLException, CantInsertException, NotWellFormedException {
        return hashtagManager.addHashtag(conn, text);
    }

    public void updateHashtag(Connection conn, HashtagDTO hashtag) throws SQLException, CantUpdateException, NotWellFormedException, CantFindException {
        hashtagManager.updateHashtag(conn, hashtag);
    }
    public void deleteHashtag(Connection conn, long hashtagId) throws SQLException, CantDeleteException, CantFindException {
        hashtagManager.deleteHashtag(conn, hashtagId);
    }
    public List<QuestionDTO> getAllQuestionsToHashtag(Connection conn, long hashtagId) throws SQLException {
        return hashtagManager.getAllQuestionsToHashtag(conn, hashtagId);
    }
    public List<ExpertiseDTO> getAllExpertiseToHashtag(Connection conn, long hashtagId) throws SQLException {
    	return hashtagManager.getAllExpertiseToHashtag(conn, hashtagId);
    }

    public boolean existsHashtag(Connection conn, String text) throws SQLException {
        //TODO Authentification

        return hashtagManager.existsHashtag(conn, text);
    }


    public long addHashtag( Connection conn, String text) throws SQLException, CantInsertException, NotWellFormedException {
        //TODO Authentification

        return hashtagManager.addHashtag(conn, text);

    }
    
    ////// questionManager //////
    public List<QuestionDTO> getQuestionList( Connection conn) throws SQLException {
        return questionManager.getQuestionList(conn);
    }
    public long addQuestion( Connection conn, QuestionDTO question) throws SQLException, CantInsertException {
        return questionManager.addQuestion(conn, question);
    }
    public QuestionDTO getQuestion( Connection conn, long questionId) throws SQLException, CantFindException {
        return questionManager.getQuestion(conn, questionId);
    }
    public void editQuestion( Connection conn, long questionId, String questionText) throws SQLException, CantUpdateException {
        questionManager.editQuestion(conn, questionId, questionText);
    }
    public void deleteQuestion( Connection conn, long questionId) throws SQLException, CantDeleteException {
        questionManager.deleteQuestion(conn, questionId);
    }
    public List<AnswerDTO> getAnswersToQuestion( Connection conn, long questionId) throws SQLException {
        return questionManager.getAnswersToQuestion(conn, questionId);
    }
    public long addAnswerToQuestion(Connection conn, AnswerDTO answer) throws SQLException, CantInsertException {
        return questionManager.addAnswerToQuestion(conn, answer);
    }
    public List<UserDTO> getBookmarkUsersToQuestion( Connection conn, long questionId) throws SQLException {
        return questionManager.getBookmarkUsersToQuestion(conn, questionId);
    }
    public JsonElement getQuestionWithAnswers(Connection conn, long questionId) throws SQLException, CantFindException {
        return questionManager.getQuestionWithAnswers(conn, questionId);
    }
    ////// answerManager //////
    public AnswerDTO getAnswer( Connection conn, long answerId) throws SQLException {
        return answerManager.getAnswer(conn, answerId);
    }
    public void editAnswer( Connection conn, long answerId, String answerText, int rating) throws SQLException, CantUpdateException {
        answerManager.editAnswer(conn, answerId, answerText, rating);
    }
    public void deleteAnswer( Connection conn, long answerId) throws SQLException, CantDeleteException {
        answerManager.deleteAnswer(conn, answerId);
    }

    public boolean registerUser(Connection conn, UserDTO userDTO) throws SQLException, CantInsertException {
        return userManager.registerUser(conn, userDTO);
    }
}

