package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.HashtagDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * Created by Marv on 05.11.2014.
 */
public class HashtagManager {


    public String getHashtag(Connection conn, long hashtagId) throws SQLException {

        HashtagDTO hashtag = new HashtagDTO();
        hashtag.setId(hashtagId);

        final String sql = "SELECT text FROM Hashtag " +
                "WHERE Hashtag.idHashtag = ?";

        /* language level 7, ensures that preparedstatement will be closed, and throws exception, if fails */
        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
            pstmt.setLong(1, hashtagId);

            ResultSet rs = pstmt.executeQuery();

            //resultset has at least one entry
            if(rs.next()) {
                hashtag.setText(rs.getString("text"));
            }
        }

        return new Gson().toJson(hashtag);
    }

    public String createHashtag(Connection conn, String content) throws SQLException, JsonParseException {
    
        HashtagDTO hashtag = (HashtagDTO) new Gson().fromJson(content, HashtagDTO.class);
        
        final String sql = "INSERT INTO Hashtag (text) VALUES (?);";

        /* language level 7, ensures that preparedstatement will be closed, and throws exception, if fails */
        try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); ) {
            pstmt.setString(1, hashtag.getText());

            int rows = pstmt.executeUpdate();
            if (rows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    hashtag.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }            
            return "Database updated. " + rows + " rows affected";
        }
    }
}
