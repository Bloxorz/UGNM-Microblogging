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
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.*;
import java.util.*;

/**
 * Created by Marv on 05.11.2014.
 */
public class UserManager {

    private QuestionManager qm = new QuestionManager();

    public List<UserDTO> getUserList(Connection conn) throws SQLException {
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
    }

    public long addUser(Connection conn, UserDTO user) throws SQLException, CantInsertException {
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

    }

    public UserDTO getUser(Connection conn, long userId) throws SQLException {
        UserDTO user = null;


        //test your sql on DB first!
        final String sql = "SELECT rating as rat, image as img, contact as con, email as mail, pass as pass FROM User " +
                "WHERE User.idUser = ?";

        /* language level 7, ensures that preparedstatement will be closed, and throws exception, if fails */
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1, userId);

            ResultSet rs = pstmt.executeQuery();

            //resultset has at least one entry
            if(rs.next()) {
                user = new UserDTO();
                user.setIdUser(userId);
                user.setElo(rs.getInt("rat"));
                user.setImagePath(rs.getString("img"));
                user.setContactInfo(rs.getString("con"));
                user.setEmail(rs.getString("mail"));
            }
        }

        return user;
    }


    /**
     * Archives a user
     * @param conn the Connection
     * @param userId a valid userId
     * @throws SQLException uknown SQLException, see msg for further details
     * @throws CantUpdateException 0 rows affected
     */
    public void deleteUser(Connection conn, long userId) throws SQLException, CantUpdateException {
        final String sql = "Update User u SET u.accountClosed = true";
        try(Statement stmt = conn.createStatement();) {
            if(stmt.executeUpdate(sql) == 0) {
                throw new CantUpdateException("User could not be deleted");
            }
        }
    }

    public List<QuestionDTO>  getUserQuestions(Connection conn, final long userId) throws SQLException {
        List<QuestionDTO> allQuestions = qm.getQuestionList(conn);
        Iterator<QuestionDTO> iter = allQuestions.iterator();
        while (iter.hasNext() && (iter.next().getIdUser() != userId)) {
            iter.remove();
        }
        return allQuestions;
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
        List<QuestionDTO> res = qr.query(conn, "SELECT idPost, timestamp, text, Post.idUser FROM Post JOIN Question ON idQuestion=idPost JOIN FavoriteQuestionsToUser ON Question.idQuestion=FavoriteQuestionsToUser.idQuestion WHERE FavoriteQuestionsToUser.idUser=? ORDER BY idPost", hq, userId);
        if(res == null) return new LinkedList<>();

        for(QuestionDTO question : res) {
            question.setHashtags( qm.getHashtagsToQuestion(conn, question.getIdPost()) );
        }
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
    public void bookmark(Connection conn, long userId,long questionId) throws SQLException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String, Object>> h = new MapHandler();
        qr.insert(conn, "INSERT INTO FavoriteQuestionsToUser (idUser, idQuestion) VALUES (?,?)", h, userId, questionId);
    }

    // returns true if the user has an entry in the User-table after this functioncall, else false
    public boolean registerUser(Connection conn, UserDTO userDTO) throws SQLException, CantInsertException {
        QueryRunner qr = new QueryRunner();
        ResultSetHandler<Map<String, Object>> h = new MapHandler();
        boolean userHasNoEntry = null == qr.query(conn, "SELECT * FROM User WHERE idUser=?", h, userDTO.getIdUser());
        if(userHasNoEntry) {
            try {
                qr.insert(conn, "INSERT INTO User (idUser, rating, image, contact, email) VALUES (?,?,?,?,?)", h, userDTO.getIdUser(), userDTO.getElo(), userDTO.getImagePath(), userDTO.getContactInfo(), userDTO.getEmail());
            } catch(SQLException e) {
                throw new CantInsertException(e.toString());
            }
        }
        return true;
    }
}
