package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.UserDTO;
import i5.las2peer.services.servicePackage.General.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * Created by Marv on 05.11.2014.
 */
public class UserManager {


    public String getUser(Connection conn, long userId) throws SQLException {

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

        return new Gson().toJson(user);
    }

    public String editUser(Connection conn, long userId, String content) throws SQLException, JsonParseException {
    
        UserDTO user = (UserDTO) new Gson().fromJson(content, UserDTO.class);
        
        final String sql = "UPDATE User SET rating = ?, image = ?, contact = ?, email = ?, pass = ? " +
                "WHERE User.idUser = ?";

        /* language level 7, ensures that preparedstatement will be closed, and throws exception, if fails */
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1, user.getRating().getValue());
            pstmt.setString(2, user.getImagePath());
            pstmt.setString(3, user.getContactInfo());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPass());
            pstmt.setLong(6, userId);

            int rows = pstmt.executeUpdate();
            return "Database updated. " + rows + " rows affected";
        }
    }
}
