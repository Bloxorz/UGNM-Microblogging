package Project.Manager;

import Project.DTO.QuestionDTO;
import Project.DTO.UserDTO;
import Project.Exceptions.CantInsertException;
import Project.Exceptions.CantUpdateException;
import Project.Exceptions.NotWellFormedException;
import Project.General.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.nimbusds.jose.JWEObject;
import org.junit.runner.Result;

/**
 * Created by Marv on 05.11.2014.
 */
public class UserManager {


    public List<UserDTO> getUserList(Connection conn) throws SQLException {
        final String sql = "SELECT idUser as userId, rating as rat, image as img, contact as con, " +
                "email as mail, pass as pass FROM User;";

        List<UserDTO> users = new ArrayList<UserDTO>();

        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                UserDTO user = new UserDTO();
                user.setId(rs.getLong("userId"));
                user.setElo(rs.getInt("rat"));
                user.setImagePath(rs.getString("img"));
                user.setContactInfo(rs.getString("con"));
                user.setEmail(rs.getString("mail"));
                user.setPass(rs.getString("pass"));

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
            pstmt.setString(5, user.getPass());

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
                user.setId(userId);
                user.setElo(rs.getInt("rat"));
                user.setImagePath(rs.getString("img"));
                user.setContactInfo(rs.getString("con"));
                user.setEmail(rs.getString("mail"));
                user.setPass(rs.getString("pass"));
            }
        }

        return user;
    }

    /**
     * Edits a user in the DB, user must have a valid id (id >= 0)
     * @param conn the Connection
     * @param user the UserDTO, valid id required ( >= 0)
     * @throws SQLException unknown DB error, see msg for further detail
     * @throws NotWellFormedException invalid id (<=0)
     * @throws CantUpdateException 0 rows were effected
     */
    public void editUser(Connection conn, UserDTO user) throws SQLException, NotWellFormedException, CantUpdateException {

        final String sql = buildUserEditSQL(user);

        /* language level 7, ensures that preparedstatement will be closed, and throws exception, if fails */
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {


            if(pstmt.executeUpdate() == 0) {
                throw new CantUpdateException();
            }

        }
    }

    /**
     * builds an SQL Update String relative to the UserDTO's set parameters
     * @param user UserDTO must at least hold a valid id
     * @return An Update SQL Statement
     * @throws NotWellFormedException id was invalid (id <= 0)
     */
    private String buildUserEditSQL(UserDTO user) throws NotWellFormedException {

        StringBuilder sb = new StringBuilder("Update User u SET ");
        if(user.getId() <= 0 )
            throw new NotWellFormedException("Invalid user id");
        if(user.getElo() >= 0) {
            sb.append("u.rating = " + user.getElo() + " , ");
        }
        if(user.getContactInfo() != null) {
            sb.append("u.contact = " + user.getContactInfo() + " , ");
        }
        if(user.getEmail() != null) {
            sb.append("u.email = " + user.getEmail() + " , ");
        }
        if(user.getPass() != null) {
            sb.append("u.pass = " + user.getPass() + " , ");
        }
        if(user.getImagePath() != null) {
            sb.append("u.image = " + user.getImagePath() + " , ");
        }

        return sb.toString();
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

    /**
     * Returns all Questions, well formed, a user has bookmarked
     * @param conn the Connection
     * @param userId a valid UserId
     * @return a Collection of QuestionDTOs
     * @throws SQLException unknown Server Error
     */
    public List<QuestionDTO>  bookmarkedQuestions(Connection conn, long userId) throws SQLException {
        final String sql = "SELECT idPost as questionId, timestamp as timestamp, text as text FROM " +
                "Post p right join Question q on p.idPost = q.idQuestion WHERE p.idUser = ?;";
        List<QuestionDTO> questions = new ArrayList<QuestionDTO>();
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1, userId);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                QuestionDTO question = new QuestionDTO();
                question.setId(rs.getLong("questionId"));
                question.setTimestamp(rs.getTimestamp("timestamp"));
                question.setText(rs.getString("text"));
                question.setUserId(userId);

                questions.add(question);
            }
        }
        return questions;
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
        final String sql = "INSERT INTO UserToQuestion (idUser, idQuestion) VALUES (?,?);";

        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, questionId);

            if(pstmt.executeUpdate() == 0) {
                throw new CantInsertException("Could not insert into UserToQuestion");
            }
        }
    }
}
