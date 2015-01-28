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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Marv on 12.11.2014.
 */
public class QuestionManager extends AbstractManager{

    /**
     * Returns a Collection of all Questions stored in the Databse
     * @param conn the connection
     * @return  List<QuestionDTO>  a Collection of well formed QuestionDTOs
     * @throws SQLException unknown DB error
     */
    public List<QuestionDTO> getQuestionList(Connection conn) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<QuestionDTO>> h = new BeanListHandler<QuestionDTO>(QuestionDTO.class);
        List<QuestionDTO> res = qr.query(conn, "SELECT idPost, timestamp, text, idUser, idQuestion FROM Post JOIN Question ON idPost=idQuestion", h);
        if(res == null)
            throw new SQLException("No questions were found in database.");

        for(QuestionDTO question : res) {
            question.setHashtags( getHashtagsToQuestion(conn, question.getIdPost() ) );
        }
        return res;
    }


    // Attention, following function only considers the fields "text" from hashtags, not their id. This way is easier to implement at frontend.
    public long addQuestion(Connection conn, QuestionDTO question) throws SQLException, CantInsertException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String,Object>> h = new MapHandler();
        Map<String,Object> result = qr.insert(conn, "INSERT INTO Post (text,idUser) VALUES (?,?)", h, question.getText(), question.getIdUser());
        long generatedId;
        if(result == null)
            throw new CantInsertException("Could not Insert into Post table");
        else
            generatedId = (Long)result.get("GENERATED_KEY");

        //insert question reference
        int rowsAffected = qr.update(conn, "INSERT INTO Question (idQuestion) VALUES (?)", generatedId);
        if(rowsAffected == 0)
            throw new CantInsertException("Could not Insert into Question table");

        question.setIdPost(generatedId);

        ResultSetHandler<HashtagDTO> hh = new BeanHandler<HashtagDTO>(HashtagDTO.class);
        for (int i = 0; i < question.getHashtags().size(); i++) {
            HashtagDTO hashtag = qr.query(conn, "SELECT idHashtag FROM Hashtag WHERE text = ?", hh, question.getHashtags().get(i).getText());
            rowsAffected = qr.update(conn, "INSERT INTO QuestionToHashtag (idQuestion, idHashtag) VALUES (?,?)", generatedId, hashtag.getIdHashtag());
        }

        return generatedId;
    }

    /**
     * Returns a well formed question if an entry exists or null if an entry does not exist in the databse
     * @param conn the given connection
     * @param questionId the questionId to look up
     * @return a well formed question or null
     * @throws SQLException unknown Database error
     */
    public QuestionDTO getQuestion(Connection conn, long questionId) throws SQLException, CantFindException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<QuestionDTO> hq = new BeanHandler<QuestionDTO>(QuestionDTO.class);
        QuestionDTO question = qr.query(conn, "SELECT idPost, timestamp, text, idUser FROM Post JOIN Question ON idPost=idQuestion WHERE idQuestion=?", hq, questionId);
        if(question == null)
            throw new CantFindException();

        ResultSetHandler<List<HashtagDTO>> h = new BeanListHandler<HashtagDTO>(HashtagDTO.class);
        question.setHashtags( qr.query(conn, "SELECT Hashtag.idHashtag, Hashtag.text FROM Hashtag JOIN QuestionToHashtag WHERE Hashtag.idHashtag = QuestionToHashtag.idHashtag AND idQuestion = ? ORDER BY idHashtag", h, questionId) );

        return question;
    }


    public void editQuestion(Connection conn, long questionId, String questionText) throws SQLException, CantUpdateException {
        final String sql = "UPDATE Post p RIGHT JOIN Question q ON p.idPost = q.idQuestion " +
                "SET p.text = ? WHERE q.idQuestion = " + questionId + ";";
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setString(1, questionText);

            //if 0 rows were effected
            if(pstmt.executeUpdate() == 0) {
                throw new CantUpdateException("No rows affected");
            }
        }
    }

    public void deleteQuestion(Connection conn, long questionId) throws SQLException, CantDeleteException {
        QueryRunner qr = new QueryRunner();
        int rowsAffected = qr.update(conn,
                "DELETE Post FROM Answer JOIN Question ON Answer.idQuestion=Question.idQuestion\n" +
                "JOIN Post ON Post.idPost=Question.idQuestion OR Post.idPost=Answer.idAnswer\n" +
                "WHERE Question.idQuestion=?", questionId);
        // the values from tables Question and Answers are automatically deleted by ON CASCADE DELETE
        if(rowsAffected == 0)
            throw new CantDeleteException("Can't delete question");
    }

    /**
     * Returns a Collection of all answers (well formed) to a given question in descending rating order
     * @param conn
     * @param questionId
     * @return
     * @throws SQLException
     */

    public List<AnswerDTO> getAnswersToQuestion(Connection conn, long questionId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<AnswerDTO>> h = new BeanListHandler<AnswerDTO>(AnswerDTO.class);
        return qr.query(conn, "SELECT idPost, idQuestion, timestamp, text, idUser, rating FROM Post JOIN Answer ON idPost=idAnswer WHERE idQuestion = ? ORDER BY rating DESC", h, questionId);
    }

    public JsonElement getQuestionWithAnswers(Connection conn, long questionId) throws SQLException, CantFindException {
        Gson g = new Gson();
        JsonObject jo = new JsonObject();
        jo.add("question", g.toJsonTree(getQuestion(conn, questionId)));
        jo.add("answers", g.toJsonTree(getAnswersToQuestion(conn, questionId)));
        return jo;
    }

    /**
     * Adds an answer to a given Question
     * @param conn the Connection
     * @param answer must be a well formed answer
     * @return the generated answerId
     * @throws SQLException a Database occurred
     * @throws CantInsertException Can't insert into Databse, see message for further detail;
     */
    public long addAnswerToQuestion(Connection conn, AnswerDTO answer) throws SQLException, CantInsertException {
        System.out.println(answer);
        long generatedId = 0;
        String addAsPost = "INSERT INTO Post (text,idUser) VALUES(?,?);";

        //add all references in DB
        try(PreparedStatement pstmt = conn.prepareStatement(addAsPost, Statement.RETURN_GENERATED_KEYS);) {

            pstmt.setString(1, answer.getText());
            pstmt.setLong(2, answer.getIdUser());

            int affectedRows = pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            //fetch generated id

            if(rs.next()) {
                generatedId = rs.getLong(1);
            } else {
                throw new CantInsertException("Could not Insert into Post table");
            }

            //insert answer reference
            String addAsAnswer = "INSERT INTO Answer (idAnswer,rating,idQuestion) VALUES (?,?,?)";
            PreparedStatement astmt = conn.prepareStatement(addAsAnswer);
            astmt.setLong(1, generatedId);
            astmt.setLong(2, answer.getRating());
            astmt.setLong(3, answer.getIdQuestion());
            affectedRows = astmt.executeUpdate();
            if(affectedRows == 1) {
                answer.setIdPost(generatedId);
            } else {
                throw new CantInsertException("Could not Insert into Post table");
            }
        }
        return generatedId;
    }

    public List<UserDTO> getBookmarkUsersToQuestion(Connection conn, long questionId) throws SQLException {
        List<UserDTO> users = new ArrayList<UserDTO>();

        final String sql = "SELECT u.idUser as userId, rating as elo, image as img, contact as contact, email as email, " +
                "pass as pass FROM User u right join UserToQuestion q on u.idUser = q.idUser WHERE q.idQuestion = ?";

        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1,questionId);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                UserDTO user = new UserDTO();
                user.setIdUser(rs.getLong("userId"));
                user.setElo(rs.getInt("elo"));
                user.setImagePath(rs.getString("img"));
                user.setContactInfo(rs.getString("contact"));
                user.setEmail(rs.getString("email"));

                users.add(user);
            }
        }
        return users;
    }

    public List<HashtagDTO> getHashtagsToQuestion(Connection conn, long questionId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<HashtagDTO>> hh = new BeanListHandler<HashtagDTO>(HashtagDTO.class);
        return qr.query(conn, "SELECT * FROM Hashtag JOIN QuestionToHashtag ON Hashtag.idHashtag=QuestionToHashtag.idHashtag WHERE idQuestion=? ORDER BY Hashtag.idHashtag", hh, questionId);
    }

}
