package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.DTO.ExpertiseDTO;
import i5.las2peer.services.servicePackage.DTO.HashtagDTO;
import i5.las2peer.services.servicePackage.DTO.QuestionDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import i5.las2peer.services.servicePackage.Exceptions.*;

import com.mysql.jdbc.Statement;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;


public class HashtagManager extends AbstractManager {

	private QuestionManager qm = new QuestionManager();

	public List<HashtagDTO> getHashtagCollection(Connection conn) throws SQLException {
		
		List<HashtagDTO> hashtag = new ArrayList<HashtagDTO>();		
		
		final String sql = "SELECT * FROM ugnm1415g2.Hashtag";
		
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {

            ResultSet rs = pstmt.executeQuery();
            
            
            while(rs.next()) {
            	
            	HashtagDTO hashtagDTO = new HashtagDTO();
            	hashtagDTO.setIdHashtag(rs.getLong("idHashtag"));
            	hashtagDTO.setText(rs.getString("text"));
            	
            	hashtag.add(hashtagDTO);
            	            	  
            }
        }
		
		
		return hashtag;
		
	}
	
	public HashtagDTO getHashtag(Connection conn, long hashtagId) throws SQLException, CantFindException {
		
		 HashtagDTO hashtag = new HashtagDTO();
		 hashtag.setIdHashtag(hashtagId);
		 
	     final String sql = "SELECT text FROM ugnm1415g2.Hashtag " + "Where idHashtag = ?";

	        try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
	            pstmt.setLong(1, hashtagId);

	            ResultSet rs = pstmt.executeQuery();
	            
	            
	            if(rs.next()) {
	            	hashtag.setText(rs.getString("text"));
	            } else {
					throw new CantFindException();
				}
	        }
	        return hashtag;
	        
	}
	
	public long addHashtag(Connection conn, String text) throws SQLException, CantInsertException, NotWellFormedException {
		

		final String sql = "INSERT INTO ugnm1415g2.Hashtag (text) VALUES (?)";
		
		if(text == null) {
            throw new NotWellFormedException("Missing text!");
        }
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); ) {
			
			pstmt.setString(1, text);

			pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()) {
				return rs.getLong(1);
            }
        }
		throw new CantInsertException("Could not insert Hashtag into DB");
	}
	
	public void updateHashtag(Connection conn, HashtagDTO hashtag) throws SQLException, CantUpdateException, NotWellFormedException, CantFindException {
				
		
		final String sql = "UPDATE ugnm1415g2.Hashtag h SET h.text = ? WHERE h.idHashtag = ?;";
		
		if(hashtag.getText() == null) {
            throw new NotWellFormedException("Missing text!");
        }
		
		String text = hashtag.getText();
		Long id = hashtag.getIdHashtag();
		
		try(PreparedStatement pstmt = conn.prepareStatement(sql); ) {
			
			pstmt.setString(1, text);
			pstmt.setLong(2, id);
			

			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected == 0) {
				throw new CantFindException();
			}
        } catch (SQLException e) {
			throw new CantUpdateException(e.toString());
		}
	}
	
	public void deleteHashtag(Connection conn, long hashtagId) throws SQLException, CantDeleteException, CantFindException {
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setIdHashtag(hashtagId);

		final String delFromHashtag = "DELETE FROM ugnm1415g2.Hashtag WHERE idHashtag = ?;";
		
		
		try(PreparedStatement pstmt = conn.prepareStatement(delFromHashtag); ) {

			pstmt.setLong(1, hashtag.getIdHashtag());
			int rowsAffected = pstmt.executeUpdate();
			if( rowsAffected == 0 ) {
				throw new CantFindException();
			}
		} catch (SQLException e) {
			throw new CantDeleteException(e.toString());
		}
	}
	
	public List<QuestionDTO> getAllQuestionsToHashtag(Connection conn, long hashtagId) throws SQLException {
		QueryRunner qr = new QueryRunner();
		ResultSetHandler<List<QuestionDTO>> hq = new BeanListHandler<QuestionDTO>(QuestionDTO.class);
		List<QuestionDTO> questions = qr.query(conn, "SELECT idPost, timestamp, text, idUser FROM Post JOIN Question on idPost=idQuestion JOIN QuestionToHashtag ON idPost=QuestionToHashtag.idQuestion WHERE idHashtag=?", hq, hashtagId);

		if(questions == null) return new LinkedList<>();

		for(QuestionDTO question : questions) {
			question.setHashtags( qm.getHashtagsToQuestion(conn, question.getIdPost()) );
		}
		return questions;
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
		            	
		            	ex.setIdExpertise(rs.getLong("id"));
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
