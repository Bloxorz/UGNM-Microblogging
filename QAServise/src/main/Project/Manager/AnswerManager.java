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
 * Created by Adam on 12.11.2014.
 */
public class AnswerManager extends AbstractManager {

    /**
     * Returns a well formed question if an entry exists or null if an entry does not exist in the database
     * @param conn the given connection
     * @param answerId the answerId to look up
     * @return a well formed answer or null
     * @throws SQLException unknown Database error
     */
    public AnswerDTO getAnswer(Connection conn, long answerId) throws SQLException {
        AnswerDTO answer = null;
        final String sql = "SELECT idPost as id, timestamp as timestamp, text as text, " +
                "idUser as userId, rating as rating, idQuestion as idQuestion " +
                "FROM Post p right join Question q on " +
                "p.idPost = q.idQuestion WHERE id = " + answerId + ";";
        try(Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) {
                answer = new AnswerDTO();
                answer.setId(rs.getLong("id"));
                answer.setTimestamp(rs.getTimestamp("timestamp"));
                answer.setText(rs.getString("text"));
                answer.setUserId(rs.getLong("userId"));

                answer.setRating(rs.getInt("rating"));
                answer.setQuestionId(rs.getLong("idQuestion"));
            }
        }
        return answer;
    }


    public void editAnswer(Connection conn, long answerId, String answerText, int rating) throws SQLException, CantUpdateException {

        final String sql = "UPDATE Post p RIGHT JOIN Answer a ON p.idPost = a.idAnswer " +
                           "SET p.text = ?, a.rating = ? WHERE a.idAnswer = " + answerId + ";";
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setString(1, answerText);
            pstmt.setInt(2, rating);

            //if 0 rows were effected
            if(pstmt.executeUpdate() == 0) {
                throw new CantUpdateException("No rows affected");
            }
        }

    }

    public void deleteAnswer(Connection conn, long answerId) throws SQLException, CantDeleteException {
        final String deleteFromAnswer = "DELETE FROM Answer a WHERE a.idAnswer = ?;";

        try(PreparedStatement qstmt = conn.prepareStatement(deleteFromAnswer); ) {
            qstmt.setLong(1, answerId);

            if(qstmt.executeUpdate() == 0) {
                throw new CantDeleteException("Cant delete from Question table");
            }

            final String deleteFromPost = "DELETE FROM Post p WHERE p.idPost = ?";
            PreparedStatement pstmt = conn.prepareStatement(deleteFromPost);
            pstmt.setLong(1, answerId);

            if(qstmt.executeUpdate() == 0) {
                throw new CantDeleteException("Cant delete from Post table");
            }
        }
    }
}
