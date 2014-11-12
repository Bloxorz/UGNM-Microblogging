package Project.Manager;

import Project.DTO.AnswerDTO;
import Project.DTO.QuestionDTO;
import Project.DTO.UserDTO;
import Project.Exceptions.CantDeleteException;
import Project.Exceptions.CantInsertException;
import Project.Exceptions.CantUpdateException;
import Project.General.Rating;
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

        if(question.wellformed()) {
            String addAsPost = "INSERT INTO Post (timestamp,text,idUser) VALUES(?,?,?);";

            //add all references in DB
            try(PreparedStatement pstmt = conn.prepareStatement(addAsPost, Statement.RETURN_GENERATED_KEYS);) {
                pstmt.setTimestamp(1, question.getTimestamp());
                pstmt.setString(2, question.getText());
                pstmt.setLong(3, question.getUserId());

                ResultSet rs = pstmt.executeQuery();

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
                qstmt.executeQuery();
                return generatedId;
            }
        }
        throw new CantInsertException("Question could not been added");

    }

    /**
     * Returns a well formed question if an entry exists or null if an entry does not exist in the databse
     * @param conn the given connection
     * @param questionId the questionId to look up
     * @return a well formed question or null
     * @throws SQLException unknown Database error
     */
    public QuestionDTO getQuestion(Connection conn, long questionId) throws SQLException {
        QuestionDTO question = null;
        final String sql = "SELECT idPost as id, timestamp as timestamp, text as text, " +
                "idUser as userId FROM Post p right join Question q on " +
                "p.idPost = q.idQuestion WHERE id = " + questionId + ";";
        try(Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) {
                question = new QuestionDTO();
                question.setId(rs.getLong("id"));
                question.setTimestamp(rs.getTimestamp("timestamp"));
                question.setText(rs.getString("text"));
                question.setUserId(rs.getLong("userId"));
            }
        }
        return question;
    }


    public void editQuestion(Connection conn, String questionText) throws SQLException, CantUpdateException {

        final String sql = "UPDATE Post p SET p.text = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setString(1, questionText);

            //if 0 rows were effected
            if(pstmt.executeUpdate() == 0) {
                throw new CantUpdateException("No rows affected");
            }
        }

    }

    public void deleteQuestion(Connection conn, long questionId) throws SQLException, CantDeleteException {
        final String deleteFromQuestion = "DELETE FROM Question q WHERE q.idQuestion = ?;";

        try(PreparedStatement qstmt = conn.prepareStatement(deleteFromQuestion); ) {
            qstmt.setLong(1, questionId);

            if(qstmt.executeUpdate() == 0) {
                throw new CantDeleteException("Cant delete from Question table");
            }

            final String deleteFromPost = "DELETE FROM Post p WHERE p.idPost = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteFromPost);
            pstmt.setLong(1, questionId);

            if(qstmt.executeUpdate() == 0) {
                throw new CantDeleteException("Cant delete from Post table");
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
        if(answer.wellformed()) {
            String addAsPost = "INSERT INTO Post (timestamp,text,idUser) VALUES(?,?,?);";

            //add all references in DB
            try(PreparedStatement pstmt = conn.prepareStatement(addAsPost, Statement.RETURN_GENERATED_KEYS);) {
                pstmt.setTimestamp(1, answer.getTimestamp());
                pstmt.setString(2, answer.getText());
                pstmt.setLong(3, answer.getUserId());

                ResultSet rs = pstmt.executeQuery();

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
                astmt.setInt(2, answer.getRating().getValue());
                astmt.setLong(3, answer.getQuestionId());
                astmt.executeQuery();
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
