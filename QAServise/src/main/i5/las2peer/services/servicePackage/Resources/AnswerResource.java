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
}
