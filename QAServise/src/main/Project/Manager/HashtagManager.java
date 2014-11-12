package Project.Manager;

import Project.DTO.HashtagDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;



public class HashtagManager {
	
	public List<HashtagDTO> getHashtagCollection(Connection conn) throws SQLException {
		
		List<HashtagDTO> hashtag = new ArrayList<HashtagDTO>();		
		
		final String sql = "SELECT * FROM ugnm1415g2.hashtag";
		
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {

            ResultSet rs = pstmt.executeQuery();
            
            
            while(rs.next()) {
            	
            	HashtagDTO hashtagDTO = new HashtagDTO();
            	hashtagDTO.setId(rs.getLong("idHashtag"));
            	hashtagDTO.setText(rs.getString("text"));
            	
            	hashtag.add(hashtagDTO);
            	            	  
            }
        }
		
		
		return hashtag;
		
	}
	
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
	
	public HashtagDTO addHashtag(Connection conn, String text) throws SQLException {
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setText(text);
		
		
		final String sql = "INSERT INTO ugnm1415g2.hashtag (text) value ('?')";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); ) {
			
			pstmt.setString(1, text);
			

			pstmt.executeUpdate(sql);
            ResultSet rs = pstmt.getGeneratedKeys();
            
            
            if(rs.next()) {
            	
            	hashtag.setId(rs.getLong("idHashtag"));
                
            }
        }
        return hashtag;
	}
	
	public void updateHashtag(Connection conn, HashtagDTO hashtag) throws SQLException {
				
		
		final String sql = "Update ugnm1415g2.hashtag h SET h.text = ? WHERE h.idHashtag = ?";
		String text = hashtag.getText();
		Long id = hashtag.getId();
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
			
			pstmt.setString(1, text);
			pstmt.setLong(2, id);
			

			pstmt.executeUpdate(sql);
            
        }
	}
	
	public void deleteHashtag(Connection conn, long hashtagId) throws SQLException {
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setId(hashtagId);
		
		final String delFirstFkey = "DELETE FROM ugnm1415g2.hashtagtoexpertise WHERE idHashtag = ?";
		final String delSecondFkey = "DELETE FROM ugnm1415g2.questiontohashtag WHERE idHashtag = ?";
		final String delFromHashtag = "DELETE FROM ugnm1415g2.hashtag WHERE idHashtag = ?";
		
		
		try(PreparedStatement pstmt = conn.prepareStatement(delFirstFkey); ) {
			
			pstmt.setLong(1, hashtag.getId());
			pstmt.executeUpdate();
			
			PreparedStatement pstmt1 = conn.prepareStatement(delSecondFkey);
			pstmt1.setLong(1, hashtag.getId());
			pstmt1.executeUpdate();
			
			
			PreparedStatement pstmt2 = conn.prepareStatement(delFromHashtag);  
			pstmt2.setLong(1, hashtag.getId());
			pstmt2.executeUpdate();
			
				
		}
		
	}
	
	public List<HashtagDTO> getAllQuestionsToHashtag(Connection conn, long hashtagId) throws SQLException {
		
		List<HashtagDTO> hashtag = new ArrayList<HashtagDTO>();
		
		return hashtag;
	}
	

}
