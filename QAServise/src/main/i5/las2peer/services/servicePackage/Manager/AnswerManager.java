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

    public void upvoteAnswer(Connection conn, long answerId, long userId) throws SQLException, CantUpdateException, CantFindException, CantInsertException {

        QueryRunner qr = new QueryRunner();
        Map<String, Object> queryMap;
        ResultSetHandler<Map<String, Object>> h = new MapHandler();

        // check answer exists
        if( null == qr.query(conn, "SELECT idAnswer FROM Answer WHERE idAnswer=?", h, answerId) )
            throw new CantFindException("Can't find answer with id:" + answerId);

        // check user not rating himself
        queryMap = qr.query(conn, "SELECT idUser FROM Post JOIN Answer ON idPost=idAnswer WHERE idAnswer=?", h, answerId);
        if ((Long)queryMap.get("idUser") == userId) {
            throw new CantInsertException("User can't upvote hisself!");
        }

        // check already rated
        queryMap = qr.query(conn, "SELECT idUserToRatedAnswer FROM UserToRatedAnswer WHERE idUser=? AND idAnswer=?", h, userId, answerId);
        if (queryMap != null) {
            throw new CantInsertException("User already rated this answer!");
        }

        // get current rating
        queryMap = qr.query(conn, "SELECT rating FROM Answer WHERE idAnswer=?", h, answerId);

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
