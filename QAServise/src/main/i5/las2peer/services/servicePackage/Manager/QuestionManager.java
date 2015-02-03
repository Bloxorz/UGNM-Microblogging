package i5.las2peer.services.servicePackage.Manager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import i5.las2peer.services.servicePackage.DTO.*;
import i5.las2peer.services.servicePackage.Exceptions.CantDeleteException;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.*;
import java.util.*;

/**
 * Created by Marv on 12.11.2014.
 */
public class QuestionManager extends AbstractManager{

    private static UserManager um = new UserManager();

    public List<QuestionDTO> getQuestionList(Connection conn, long userWhoAsksId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<QuestionDTO>> h = new BeanListHandler<QuestionDTO>(QuestionDTO.class);
        List<QuestionDTO> res = qr.query(conn, "SELECT idPost, timestamp, text, idUser, idQuestion FROM Post JOIN Question ON idPost=idQuestion ORDER BY idPost DESC", h);
        if(res.size() == 0)
            throw new SQLException("No questions were found in database.");
        fillOutHashtagsAndFavourCountAndIsFavourite(conn, res, userWhoAsksId);
        return res;
    }


    // Attention, following function only considers the fields "text" from hashtags, not their id. This way is easier to implement at frontend.
    public long addQuestion(Connection conn, QuestionDTO question) throws SQLException, CantInsertException, CantFindException, CantUpdateException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String, Object>> mapHandler = new MapHandler();
        Map<String, Object> resultMap;

        // check elo
        if (um.getElo(conn, question.getIdUser()) <= 0) {
            throw new CantInsertException("Your elo is too low!");
        }

        // insert into Post
        resultMap = qr.insert(conn, "INSERT INTO Post (text,idUser) VALUES (?,?)", mapHandler, question.getText(), question.getIdUser());
        long generatedId;
        if (resultMap == null)
            throw new CantInsertException("Could not Insert into Post table");
        else
            generatedId = (Long) resultMap.get("GENERATED_KEY");

        // insert into Question
        int rowsAffected = qr.update(conn, "INSERT INTO Question (idQuestion) VALUES (?)", generatedId);
        if (rowsAffected == 0)
            throw new CantInsertException("Could not Insert into Question table");
        question.setIdPost(generatedId);

        // insert hashtags
        ResultSetHandler<HashtagDTO> hh = new BeanHandler<HashtagDTO>(HashtagDTO.class);
        for (int i = 0; i < question.getHashtags().size(); i++) {
            long hashtagId;
            HashtagDTO hashtag = qr.query(conn, "SELECT idHashtag FROM Hashtag WHERE text=?", hh, question.getHashtags().get(i).getText());
            if (hashtag == null) {
                resultMap = qr.insert(conn, "INSERT INTO Hashtag (text) VALUES (?)", mapHandler, question.getHashtags().get(i).getText());
                hashtagId = (Long) resultMap.get("GENERATED_KEY");
            } else {
                hashtagId = hashtag.getIdHashtag();
            }
            rowsAffected = qr.update(conn, "INSERT INTO QuestionToHashtag (idQuestion, idHashtag) VALUES (?,?)", generatedId, hashtagId);
        }

        // update elo
        um.decreaseElo(conn, question.getIdUser());

        return generatedId;
    }

    public QuestionDTO getQuestion(Connection conn, long questionId, long userWhoAsksId) throws SQLException, CantFindException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<QuestionDTO> hq = new BeanHandler<QuestionDTO>(QuestionDTO.class);
        QuestionDTO question = qr.query(conn, "SELECT idPost, timestamp, text, idUser FROM Post JOIN Question ON idPost=idQuestion WHERE idQuestion=?", hq, questionId);
        if(question == null)
            throw new CantFindException();
        List<QuestionDTO> questionList = new LinkedList<QuestionDTO>(); questionList.add(question);
        fillOutHashtagsAndFavourCountAndIsFavourite(conn, questionList, userWhoAsksId);

        return question;
    }

    public List<AnswerDTO> getAnswersToQuestion(Connection conn, long questionId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<AnswerDTO>> h = new BeanListHandler<AnswerDTO>(AnswerDTO.class);
        //return
        return qr.query(conn, "SELECT idPost, idQuestion, timestamp, text, idUser, rating FROM Post JOIN Answer ON idPost=idAnswer WHERE idQuestion = ? ORDER BY rating DESC", h, questionId);
    }

    public Map<String, Object> getQuestionWithAnswers(Connection conn, long questionId, long userWhoAsksId) throws SQLException, CantFindException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("question", getQuestion(conn, questionId, userWhoAsksId));
        map.put("answers", getAnswersToQuestion(conn, questionId));
        return map;
    }

    public long addAnswerToQuestion(Connection conn, AnswerDTO answer) throws SQLException, CantInsertException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String, Object>> mapHandler = new MapHandler();
        Map<String, Object> resultMap;

        resultMap = qr.insert(conn, "INSERT INTO Post (text,idUser) VALUES(?,?)", mapHandler, answer.getText(), answer.getIdUser());
        if (resultMap == null) {
            throw new CantInsertException("Could not Insert into Post table");
        }
        long generatedId = (Long) resultMap.get("GENERATED_KEY");

        qr.insert(conn, "INSERT INTO Answer (idAnswer,idQuestion) VALUES (?,?)", mapHandler, generatedId, answer.getIdQuestion());
        answer.setIdPost(generatedId);

        return generatedId;
    }

    public int getBookmarkCount(Connection conn, long questionId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<UserDTO>> h = new BeanListHandler<UserDTO>(UserDTO.class);
        List<UserDTO> userList = qr.query(conn, "SELECT User.idUser,elo FROM User JOIN FavouriteQuestionToUser ON User.idUser=FavouriteQuestionToUser.idUser WHERE idQuestion=? ORDER BY User.idUser", h, questionId);

        return userList.size();
    }

    public List<HashtagDTO> getHashtagsToQuestion(Connection conn, long questionId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<HashtagDTO>> hh = new BeanListHandler<HashtagDTO>(HashtagDTO.class);
        return qr.query(conn, "SELECT * FROM Hashtag JOIN QuestionToHashtag ON Hashtag.idHashtag=QuestionToHashtag.idHashtag WHERE idQuestion=? ORDER BY Hashtag.idHashtag", hh, questionId);
    }

    void fillOutHashtagsAndFavourCountAndIsFavourite(Connection conn, List<QuestionDTO> questions, long userToCheckForFavourite) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String, Object>> mapHandler = new MapHandler();

        for (QuestionDTO question : questions) {
            question.setHashtags(getHashtagsToQuestion(conn, question.getIdPost()));
            question.setFavourCount(getBookmarkCount(conn, question.getIdPost()));
            question.setFavourite(null != qr.query(conn, "SELECT idFavouriteQuestionToUser FROM FavouriteQuestionToUser WHERE idQuestion=? AND idUser=?", mapHandler, question.getIdPost(), userToCheckForFavourite));
        }
    }
}
