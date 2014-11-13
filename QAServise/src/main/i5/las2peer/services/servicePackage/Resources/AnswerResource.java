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

	public HttpResponse getAnswer(String token, long answerID) {
        HttpResponse response = new HttpResponse("");
        try {
            AnswerDTO answers = ManagerFacade.getInstance().getAnswer(token, conn, answerID);

            Gson gson = new Gson();
            String json = gson.toJson(answers);
            response = new HttpResponse(json, 200);

        } catch (SQLException e) {
            response.setStatus(500);
        }
        return response;
	}

	public HttpResponse deleteAnswer(String token, long answerID) {
        HttpResponse response = new HttpResponse("");
        try {
            ManagerFacade.getInstance().deleteAnswer(token, conn, answerID);

            response.setStatus(200);

        } catch (CantDeleteException e) {
            response.setStatus(500);
        } catch (SQLException e) {
            response.setStatus(500);
        }
        return response;
	}

	public HttpResponse updateAnswer(String token, long answerID, String content) {
        HttpResponse response = new HttpResponse("");
        try {
            AnswerDTO answer = (AnswerDTO) new Gson().fromJson(content, AnswerDTO.class);
            ManagerFacade.getInstance().editAnswer(token, conn, answerID, answer.getText(), answer.getRating().getValue());

            response.setStatus(200);

        } catch (CantUpdateException e) {
            response.setStatus(500);
        } catch (JsonParseException e) {
            response.setStatus(400);
        } catch (SQLException e) {
            response.setStatus(500);
        }
        return response;
	}

}
