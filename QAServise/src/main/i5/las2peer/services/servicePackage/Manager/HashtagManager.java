package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import i5.las2peer.services.servicePackage.Exceptions.CantDeleteException;
import i5.las2peer.services.servicePackage.Exceptions.CantInsertException;
import i5.las2peer.services.servicePackage.Exceptions.CantUpdateException;
import i5.las2peer.services.servicePackage.Exceptions.NotWellFormedException;

import com.mysql.jdbc.Statement;


public class HashtagManager extends AbstractManager {
	
	public List<HashtagDTO> getHashtagCollection(Connection conn) throws SQLException {
		
		List<HashtagDTO> hashtag = new ArrayList<HashtagDTO>();		
		
		final String sql = "SELECT * FROM ugnm1415g2.Hashtag";
		
		
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
		 
	     final String sql = "SELECT text FROM ugnm1415g2.Hashtag " + "Where idHashtag = ?";

	        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
	            pstmt.setLong(1, hashtagId);

	            ResultSet rs = pstmt.executeQuery();
	            
	            
	            if(rs.next()) {
	            	
	            	hashtag.setText(rs.getString("text"));
	                
	            }
	        }
	        return hashtag;
	        
	}
	
	public long addHashtag(Connection conn, String text) throws SQLException, CantInsertException, NotWellFormedException {
		

		final String sql = "INSERT INTO ugnm1415g2.Hashtag (text) value ('?');";
		
		if(text == null) {
            throw new NotWellFormedException("Missing text!");
        }
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); ) {
			
			pstmt.setString(1, text);

			pstmt.executeUpdate(sql);
            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()) {
				return rs.getLong(1);
            }
        }
		throw new CantInsertException("Could not insert Hashtag into DB");
	}
	
	public void updateHashtag(Connection conn, HashtagDTO hashtag) throws SQLException, CantUpdateException, NotWellFormedException {
				
		
		final String sql = "Update ugnm1415g2.Hashtag h SET h.text = ? WHERE h.idHashtag = ?;";
		
		if(hashtag.getText() == null) {
            throw new NotWellFormedException("Missing text!");
        }
		
		String text = hashtag.getText();
		Long id = hashtag.getId();
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
			
			pstmt.setString(1, text);
			pstmt.setLong(2, id);
			

			pstmt.executeUpdate(sql);
            
        }
		throw new CantUpdateException("Could not update Hashtag in DB!");
	}
	
	public void deleteHashtag(Connection conn, long hashtagId) throws SQLException, CantDeleteException {
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setId(hashtagId);
		
		final String delFirstFkey = "DELETE FROM ugnm1415g2.Hashtagtoexpertise WHERE idHashtag = ?;";
		final String delSecondFkey = "DELETE FROM ugnm1415g2.Questiontohashtag WHERE idHashtag = ?;";
		final String delFromHashtag = "DELETE FROM ugnm1415g2.Hashtag WHERE idHashtag = ?;";
		
		
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
		throw new CantDeleteException("Could not delete Hashtag from DB!");
	}
	
	public List<QuestionDTO> getAllQuestionsToHashtag(Connection conn, long hashtagId) throws SQLException {
		
		List<QuestionDTO> res = new ArrayList<QuestionDTO>();
		
		
		final String sql = "select fragenID, timestamp, text, userID From " + 
				"(select q.idQuestion as fragenID, timestamp as timestamp, text as text, idUser as userID From " + 
				"Post p join Question q on p.idPost = q.idQuestion) as t1 join " + 
				"( select idHashtag, idQuestion from QuestionToHashtag where idHashtag = ? ) as t2 " + 
				"on fragenID = idQuestion;";

        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
        	
        	
        	pstmt.setLong(1,hashtagId);

            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                QuestionDTO question = new QuestionDTO();
                question.setId(rs.getLong("fragenID"));
                question.setTimestamp(rs.getTimestamp("timestamp"));
                question.setText(rs.getString("text"));
                question.setUserId(rs.getLong("userID"));

                res.add(question);
            }
        }
        return res;
	}
	
	public List<ExpertiseDTO> getAllExpertiseToHashtag(Connection conn, long hashtagId) throws SQLException {
		
		List<ExpertiseDTO> res = new ArrayList<ExpertiseDTO>();
		
		final String sql = "select id , text From ( "+
				"select idExpertise as id, text as text from Expertise " +
    			") as t1 join " +
				"( select idExpertise from HashtagToExpertise WHERE idHashtag = ? " +
				") as t2 " +
				"on id = idExpertise;";
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
		        	
		        	
		        	pstmt.setLong(1,hashtagId);
		
		            ResultSet rs = pstmt.executeQuery();
		            
		            while (rs.next()) {
		            	ExpertiseDTO ex = new ExpertiseDTO();
		            	
		            	ex.setId(rs.getLong("id"));
		            	ex.setText(rs.getString("text"));
		            			                
		            	
		            	res.add(ex);
		            }
		        }
		
		return res;
		
		
	}

	public boolean existsHashtag(Connection conn, String text) throws SQLException {
		final String sql = "SELECT idHashtag FROM Hashtag h WHERE h.text = ?;";

		try(PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, text);

			ResultSet rs = pstmt.executeQuery();

			return rs.next();
		}

	}
	

}
