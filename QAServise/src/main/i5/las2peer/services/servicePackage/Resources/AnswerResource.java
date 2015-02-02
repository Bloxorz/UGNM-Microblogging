package i5.las2peer.services.servicePackage.Resources;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import i5.las2peer.restMapper.HttpResponse;
import i5.las2peer.services.servicePackage.DTO.AnswerDTO;
import i5.las2peer.services.servicePackage.Manager.ManagerFacade;
import java.sql.Connection;
import java.sql.SQLException;
import i5.las2peer.services.servicePackage.Exceptions.*;

/**
 * Created by Marv on 12.11.2014.
 */
public class AnswerResource extends AbstractResource {

	public AnswerResource(Connection conn) {
		super(conn);
	}

	public HttpResponse upvoteAnswer(long answerId, long userId) {
		if (userId == 0)
			return new HttpResponse("You have to be logged in to upvote an answer.", 401);
		try {
			ManagerFacade.getInstance().upvoteAnswer(conn, userId, answerId);
			return new HttpResponse("Answer upvoted.", 201);
		} catch (SQLException e) {
			return new HttpResponse(e.toString(), 500);
		} catch (CantInsertException e) {
			return new HttpResponse(e.toString(), 500);
		} catch (CantUpdateException e) {
			return new HttpResponse(e.toString(), 500);
		} catch (CantFindException e) {
			return new HttpResponse(e.toString(), 500);
		}
	}
}
