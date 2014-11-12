package Project.Manager;

import Project.DTO.HashtagDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class HashtagManager {
	
	public HashtagDTO getHashtag(Connection conn, long hashtagId) throws SQLException {
		
		 HashtagDTO hashtag = new HashtagDTO();
		 hashtag.setId(hashtagId);
		 
	     final String sql = "SELECT text FROM ugnm1415g2.hashtag " + "Where idHashtag = ?";

	        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
	            pstmt.setLong(1, hashtagId);

	            ResultSet rs = pstmt.executeQuery();
	            
	            
	            if(rs.next()) {
	            	
	            	hashtag.setText(rs.getString("text"));
	                
	            }
	        }
	        return hashtag;
	        
	}
	

}
