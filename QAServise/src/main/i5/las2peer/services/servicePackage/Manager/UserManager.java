package i5.las2peer.services.servicePackage.Manager;

import com.sun.mail.util.logging.MailHandler;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;
import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.Exceptions.CantFindException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
import i5.las2peer.services.servicePackage.Exceptions.NotWellFormedException;
import org.apache.commons.dbutils.BaseResultSetHandler;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.*;
import java.util.*;

/**
 * Created by Marv on 05.11.2014.
 */
public class UserManager {

    private static QuestionManager qm = new QuestionManager();

    /*public List<UserDTO> getUserList(Connection conn) throws SQLException {
        final String sql = "SELECT idUser as userId, rating as rat, image as img, contact as con, " +
                "email as mail, pass as pass FROM User;";

        List<UserDTO> users = new ArrayList<UserDTO>();

        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                UserDTO user = new UserDTO();
                user.setIdUser(rs.getLong("userId"));
                user.setElo(rs.getInt("rat"));
                user.setImagePath(rs.getString("img"));
                user.setContactInfo(rs.getString("con"));
                user.setEmail(rs.getString("mail"));

                users.add(user);
            }
        }

        return users;
    }*/

    /*public long addUser(Connection conn, UserDTO user) throws SQLException, CantInsertException {
        final String sql = "INSERT INTO User(rating, image, contact, email, pass) VALUES " +
                "(?,?,?,?,?)";

        try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); ) {
            pstmt.setInt(1, user.getElo());
            pstmt.setString(2, user.getImagePath());
            pstmt.setString(3, user.getContactInfo());
            pstmt.setString(4, user.getEmail());

            if(pstmt.executeUpdate() == 0) {
                throw new CantInsertException("Can't insert into User");
            }
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                return rs.getLong(1);
            }
        }
        throw new CantInsertException("User could not been added");

    }*/

    public UserDTO getUser(Connection conn, long userId) throws SQLException, CantFindException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<UserDTO> h = new BeanHandler<UserDTO>(UserDTO.class);
        UserDTO user = qr.query(conn, "SELECT idUser, elo, image, contact, email FROM User WHERE idUser=?", h, userId);
        if(user == null)
            throw new CantFindException("Can not find user with id:"+userId);
        return user;
    }


    /**
     * Archives a user
     * @param conn the Connection
     * @param userId a valid userId
     * @throws SQLException uknown SQLException, see msg for further details
     * @throws CantUpdateException 0 rows affected
     */
    /*public void deleteUser(Connection conn, long userId) throws SQLException, CantUpdateException {
        final String sql = "Update User u SET u.accountClosed = true";
        try(Statement stmt = conn.createStatement();) {
            if(stmt.executeUpdate(sql) == 0) {
                throw new CantUpdateException("User could not be deleted");
            }
        }
    }*/
    
    public void editUser(Connection conn, long userId, UserDTO data) throws SQLException, CantUpdateException {
        QueryRunner qr = new QueryRunner();
        int rowsAffected = qr.update(conn, "UPDATE User SET image=?, contact=?, email=? WHERE idUser=?", data.getImage(), data.getContact(), data.getEmail(), userId);
        if(rowsAffected == 0)
            throw new CantUpdateException("0 rows affected.");
    }
    public List<QuestionDTO>  getUserQuestions(Connection conn, long userId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<QuestionDTO>> hq = new BeanListHandler<QuestionDTO>(QuestionDTO.class);
        List<QuestionDTO> res = qr.query(conn, "SELECT idPost, timestamp, text, idUser FROM Post JOIN Question ON idQuestion=idPost WHERE idUser=? ORDER BY idPost", hq, userId);
        qm.fillOutHashtagsAndFavourCount(conn, res);
        return res;
    }
    public List<QuestionDTO>  getExpertQuestions(Connection conn, long userId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<QuestionDTO>> hq = new BeanListHandler<QuestionDTO>(QuestionDTO.class);
        // following SQL-query: Post JOIN Question JOIN Question-Hashtag JOIN Hashtag-Epertise JOIN Expertise-User WHERE idUser=?
        List<QuestionDTO> res = qr.query(conn, "SELECT idPost, Post.idUser, Post.text, timestamp FROM Post JOIN Question ON idPost=idQuestion JOIN QuestionToHashtag ON Question.idQuestion=QuestionToHashtag.idQuestion JOIN HashtagToExpertise ON QuestionToHashtag.idHashtag=HashtagToExpertise.idHashtag JOIN UserToExpertise ON HashtagToExpertise.idExpertise=UserToExpertise.idExpertise WHERE UserToExpertise.idUser=? GROUP BY idPost ORDER BY idPost", hq, userId);
        qm.fillOutHashtagsAndFavourCount(conn, res);
        return res;
    }

    /**
     * Returns all Questions, well formed, a user has bookmarked
     * @param conn the Connection
     * @param userId a valid UserId
     * @return a Collection of QuestionDTOs
     * @throws SQLException unknown Server Error
     */
    public List<QuestionDTO>  bookmarkedQuestions(Connection conn, long userId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<List<QuestionDTO>> hq = new BeanListHandler<QuestionDTO>(QuestionDTO.class);
        List<QuestionDTO> res = qr.query(conn, "SELECT idPost, timestamp, text, Post.idUser FROM Post JOIN Question ON idQuestion=idPost JOIN FavouriteQuestionToUser ON Question.idQuestion=FavouriteQuestionToUser.idQuestion WHERE FavouriteQuestionToUser.idUser=? ORDER BY idPost", hq, userId);
        qm.fillOutHashtagsAndFavourCount(conn, res);
        return res;
    }

    /**
     * Adds a Question to User's bookmarks
     * @param conn the Connection
     * @param userId A valid userId
     * @param questionId A valid questionId
     * @throws SQLException unknown Server Error, see msg for further detail
     * @throws CantInsertException zero rows effected
     */
    public void bookmark(Connection conn, long userId,long questionId) throws SQLException, CantInsertException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String, Object>> h = new MapHandler();
        if (hasBookmarkedQuestion(conn, userId, questionId)) {
            throw new CantInsertException("This question is already bookmarked!");
        }
        qr.insert(conn, "INSERT INTO FavouriteQuestionToUser (idUser, idQuestion) VALUES (?,?)", h, userId, questionId);
    }
    public boolean hasBookmarkedQuestion(Connection conn, long userId,long questionId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String, Object>> h = new MapHandler();
        return null != qr.query(conn, "SELECT idFavouriteQuestionToUser FROM FavouriteQuestionToUser WHERE idUser=? AND idQuestion=?", h, userId, questionId);
    }

    // returns true if the user has an entry in the User-table after this functioncall, else false
    public boolean registerUser(Connection conn, UserDTO userDTO) throws SQLException, CantInsertException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String, Object>> h = new MapHandler();
        boolean userHasNoEntry = null == qr.query(conn, "SELECT * FROM User WHERE idUser=?", h, userDTO.getIdUser());
        if(userHasNoEntry) {
            try {
                qr.insert(conn, "INSERT INTO User (idUser, elo, image, contact, email) VALUES (?,?,?,?,?)", h, userDTO.getIdUser(), userDTO.getElo(), userDTO.getImage(), userDTO.getContact(), userDTO.getEmail());
            } catch(SQLException e) {
                throw new CantInsertException(e.toString());
            }
        }
        return true;
    }

    public void increaseElo(Connection conn, long userId) throws SQLException, CantUpdateException, CantFindException {
        int elo = (int) Math.min((long) getElo(conn,userId) + 1, (long) Integer.MAX_VALUE);
        QueryRunner qr = new QueryRunner();
        int rowsAffected = qr.update(conn, "UPDATE User SET elo=? WHERE idUser=?", elo, userId);
        if(rowsAffected == 0)
            throw new CantUpdateException("0 rows affected.");
    }
    public void decreaseElo(Connection conn, long userId) throws SQLException, CantUpdateException, CantFindException {
        int elo = Math.max(getElo(conn,userId)-1, 0);
        QueryRunner qr = new QueryRunner();
        int rowsAffected = qr.update(conn, "UPDATE User SET elo=? WHERE idUser=?", elo, userId);
        if(rowsAffected == 0)
            throw new CantUpdateException("0 rows affected.");
    }
    public int getElo(Connection conn, long userId) throws SQLException, CantFindException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String, Object>> h = new MapHandler();
        Map<String, Object> result = qr.query(conn, "SELECT elo FROM User WHERE idUser=?", h, userId);
        if( result == null )
            throw new CantFindException("Can't find user with id:" + userId);
        return (int) result.get("elo");
    }
}
