package i5.las2peer.services.servicePackage.Manager;

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

	private static QuestionManager qm = new QuestionManager();

	public List<HashtagDTO> getHashtagCollection(Connection conn) throws SQLException, CantFindException {
		QueryRunner qr = new QueryRunner();
		ResultSetHandler<List<HashtagDTO>> h = new BeanListHandler<HashtagDTO>(HashtagDTO.class);
		List<HashtagDTO> hashtags = qr.query(conn, "SELECT text FROM Hashtag ORDER BY idHashtag", h);
		if (hashtags.size() == 0) {
			throw new CantFindException("Can't find any hashtags.");
		}
		return hashtags;
	}
	public List<QuestionDTO> getAllQuestionsToHashtag(Connection conn, long hashtagId, long userThatAsksId) throws SQLException {
		QueryRunner qr = new QueryRunner();
		ResultSetHandler<List<QuestionDTO>> hq = new BeanListHandler<QuestionDTO>(QuestionDTO.class);
		List<QuestionDTO> questions = qr.query(conn, "SELECT idPost, timestamp, text, idUser FROM Post JOIN Question on idPost=idQuestion JOIN QuestionToHashtag ON idPost=QuestionToHashtag.idQuestion WHERE idHashtag=?", hq, hashtagId);

		if (questions == null) return new LinkedList<>();
		qm.fillOutHashtagsAndFavourCountAndIsFavourite(conn, questions, userThatAsksId);

		return questions;
	}
}
