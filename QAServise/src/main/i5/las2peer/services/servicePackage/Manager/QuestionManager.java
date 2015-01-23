package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantDeleteException;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
import i5.las2peer.services.servicePackage.General.Rating;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        List<QuestionDTO> res = new ArrayList<QuestionDTO>();

        final String sql = "SELECT idPost as id, timestamp as timestamp, text as text, " +
                "idUser as userId FROM Post p right join Question q on p.idPost = q.idQuestion;";

        try(Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                QuestionDTO question = new QuestionDTO();
                question.setId(rs.getLong("id"));
                question.setTimestamp(rs.getTimestamp("timestamp"));
                question.setText(rs.getString("text"));
                question.setUserId(rs.getLong("userId"));

                res.add(question);
            }
        }
        return res;
    }

    /**
     * adds a Question into the Databse
     * @param conn the  connection
     * @param question a well formed QuestionDTO
     * @throws SQLException unknown DB error
     * @throws CantInsertException Can't create a new Post in the DB
     */
    public long addQuestion(Connection conn, QuestionDTO question) throws SQLException, CantInsertException {

            String addAsPost = "INSERT INTO Post (text,idUser) VALUES(?,?);";

            //add all references in DB
            try(PreparedStatement pstmt = conn.prepareStatement(addAsPost, Statement.RETURN_GENERATED_KEYS);) {
                pstmt.setString(1, question.getText());
                pstmt.setLong(2, question.getUserId());

                int rowsAffected = pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                //fetch generated id
                long generatedId = 0;
                if(rs.next()) {
                    generatedId = rs.getLong(1);
                } else {
                    throw new CantInsertException("Could not Insert into Post table");
                }

                //insert question reference
                String addAsQuestion = "INSERT INTO Question (idQuestion) VALUES (?)";
                PreparedStatement qstmt = conn.prepareStatement(addAsQuestion);
                qstmt.setLong(1, generatedId);
                rowsAffected = qstmt.executeUpdate();
                if(rowsAffected == 0)
                    throw new CantInsertException("Could not Insert into Question table");

                question.setId(generatedId);
                return generatedId;
            }

    }

    /**
     * Returns a well formed question if an entry exists or null if an entry does not exist in the databse
     * @param conn the given connection
     * @param questionId the questionId to look up
     * @return a well formed question or null
     * @throws SQLException unknown Database error
     */
    public QuestionDTO getQuestion(Connection conn, long questionId) throws SQLException, CantFindException {
        QuestionDTO question = new QuestionDTO();

        final String sql = "SELECT idPost as id, timestamp as timestamp, text as text, " +
                "idUser as userId FROM Post p right join Question q on " +
                "p.idPost = q.idQuestion WHERE idPost = ?;";

        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1, questionId);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                question.setId(rs.getLong("id"));
                question.setTimestamp(rs.getTimestamp("timestamp"));
                question.setText(rs.getString("text"));
                question.setUserId(rs.getLong("userId"));
            } else {
                throw new CantFindException();
            }
        }

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
        final String deleteFromQuestion = "DELETE FROM Question WHERE idQuestion = ?;";

        try(PreparedStatement qstmt = conn.prepareStatement(deleteFromQuestion); ) {

            qstmt.setLong(1, questionId);

            if(qstmt.executeUpdate() == 0) {
                throw new CantDeleteException("Cant delete from Question table");
            }
        }
    }

    /**
     * Returns a Collection of all answers (well formed) to a given question in descending rating order
     * @param conn
     * @param questionId
     * @return
     * @throws SQLException
     */
    public List<AnswerDTO> getAnswersToQuestion(Connection conn, long questionId) throws SQLException {
        List<AnswerDTO> answers = new ArrayList<AnswerDTO>();
        final String sql = "SELECT idPost as id, timestamp as timestamp, text as text, " +
                "idUser as userId, a.rating as rating FROM Post p right join Answer a on p.idPost = a.idAnswer WHERE " +
                "a.idQuestion = ? Order by rating desc;";
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1, questionId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                AnswerDTO answer = new AnswerDTO();
                answer.setId(rs.getLong("id"));
                answer.setTimestamp(rs.getTimestamp("timestamp"));
                answer.setText(rs.getString("text"));
                answer.setUserId(rs.getLong("userId"));
                answer.setRating(Rating.fromInt(rs.getInt("rating")));
                answer.setQuestionId(questionId);
                answers.add(answer);
            }
        }
        return answers;
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
        long generatedId = 0;
        String addAsPost = "INSERT INTO Post (text,idUser) VALUES(?,?);";

        //add all references in DB
        try(PreparedStatement pstmt = conn.prepareStatement(addAsPost, Statement.RETURN_GENERATED_KEYS);) {

            pstmt.setString(1, answer.getText());
            pstmt.setLong(2, answer.getUserId());

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
            astmt.setLong(2, answer.getRating().getValue());
            astmt.setLong(3, answer.getQuestionId());
            affectedRows = astmt.executeUpdate();
            if(affectedRows == 1) {
                answer.setId(generatedId);
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
                user.setId(rs.getLong("userId"));
                user.setElo(rs.getInt("elo"));
                user.setImagePath(rs.getString("img"));
                user.setContactInfo(rs.getString("contact"));
                user.setEmail(rs.getString("email"));
                user.setPass(rs.getString("pass"));

                users.add(user);
            }
        }
        return users;
    }

}
