package Project.Manager;

import Project.DTO.UserDTO;
import Project.General.Rating;
import Project.Resources.RestResource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Marv on 05.11.2014.
 */
public class UserManager {


    public UserDTO getUser(Connection conn, long userId) throws SQLException {

        UserDTO user = new UserDTO();
        user.setId(userId);

        //test your sql on DB first!
        final String sql = "SELECT rating as rat, image as img, contact as con, email as mail, pass as pass FROM User " +
                "WHERE User.idUser = ?";

        /* language level 7, ensures that preparedstatement will be closed, and throws exception, if fails */
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1, userId);

            ResultSet rs = pstmt.executeQuery();

            //resultset has at least one entry
            if(rs.next()) {
                user.setRating(Rating.fromInt(rs.getInt("rat")));
                user.setImagePath(rs.getString("img"));
                user.setContactInfo(rs.getString("con"));
                user.setEmail(rs.getString("mail"));
                user.setPass(rs.getString("pass"));
            }
        }

        return user;
    }



}
