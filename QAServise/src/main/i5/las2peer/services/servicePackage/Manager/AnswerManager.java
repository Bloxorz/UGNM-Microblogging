package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantDeleteException;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.Map;

/**
 * Created by Adam on 12.11.2014.
 */
public class AnswerManager extends AbstractManager {

    private static UserManager um = new UserManager();
    /**
     * Returns a well formed question if an entry exists or null if an entry does not exist in the database
     * @param conn the given connection
     * @param answerId the answerId to look up
     * @return a well formed answer or null
     * @throws SQLException unknown Database error
     */
    /*public AnswerDTO getAnswer(Connection conn, long answerId) throws SQLException {
        AnswerDTO answer = null;
        final String sql = "SELECT idPost as id, timestamp as timestamp, text as text, " +
                "idUser as userId, rating as rating, idQuestion as idQuestion " +
                "FROM Post p right join Answer a on " +
                "p.idPost = a.idAnswer WHERE idAnswer = " + answerId + ";";
        try(Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) {
                answer = new AnswerDTO();
                answer.setIdPost(rs.getLong("id"));
                answer.setTimestamp(rs.getTimestamp("timestamp"));
                answer.setText(rs.getString("text"));
                answer.setIdUser(rs.getLong("userId"));

                answer.setRating(rs.getInt("rating"));
                answer.setIdQuestion(rs.getLong("idQuestion"));
            }
        }
        return answer;
    }*/

    /*public void editAnswer(Connection conn, long answerId, String answerText, int rating) throws SQLException, CantUpdateException {

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

    }*/

    /*public void deleteAnswer(Connection conn, long answerId) throws SQLException, CantDeleteException {
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
    }*/

    public void uprateAnswer(Connection conn, long answerId, long userId) throws SQLException, CantUpdateException, CantFindException, CantInsertException {

        QueryRunner qr = new QueryRunner();
        Map<String, Object> queryMap;
        ResultSetHandler<Map<String, Object>> h = new MapHandler();

        // check already rated
        queryMap = qr.query(conn, "SELECT idUserToRatedAnswer FROM UserToRatedAnswer WHERE idUser=? AND idAnswer=?", h, userId, answerId);
        if (queryMap != null) {
            throw new CantInsertException("User already rated this answer!");
        }

        // get current rating
        queryMap = qr.query(conn, "SELECT rating FROM Answer WHERE idAnswer=?", h, answerId);
        if(queryMap == null)
            throw new CantFindException("Can't find answer with id:" + answerId);

        // uprate
        int newRating = (int) queryMap.get("rating") + 1;
        int rowsAffected = qr.update(conn, "UPDATE Answer SET rating=? WHERE idAnswer=?", newRating, answerId);
        if(rowsAffected == 0)
            throw new CantUpdateException("Can't increase rating.");

        // save user
        qr.insert(conn, "INSERT INTO UserToRatedAnswer (idAnswer, idUser) VALUES (?,?)", h, answerId, userId);

        // increase elo for user that posted the answer
        queryMap = qr.query(conn, "SELECT idUser FROM Post WHERE idPost=?", h, answerId);
        um.increaseElo(conn, (Long)queryMap.get("idUser"));
    }
}
