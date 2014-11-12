package i5.las2peer.services.servicePackage.Resources;

import i5.las2peer.restMapper.HttpResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;

/**
 * Created by Marv on 12.11.2014.
 */
public class AnswerResource extends AbstractResource {

	public AnswerResource(Connection conn) {
		super(conn);
	}

	public HttpResponse getAnswer(String token, long answerID) {
		
		throw new NotImplementedException();
		
	}
	
	public HttpResponse deleteAnswer(String token, long answerID) {
		
		throw new NotImplementedException();
	}
	
	public HttpResponse updateAnswer(String token, long answerID) {
		
		throw new NotImplementedException();
	}
	
	
	
}
