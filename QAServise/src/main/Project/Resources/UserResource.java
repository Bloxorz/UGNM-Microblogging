package Project.Resources;

import Project.DTO.UserDTO;
import i5.las2peer.restMapper.HttpResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;

/**
 * Created by Marv on 12.11.2014.
 */
public class UserResource extends AbstractResource {

    public UserResource(Connection conn) {
        super(conn);
    }

    public HttpResponse getUserCollection(String token) {
        throw new NotImplementedException();
    }

    public HttpResponse addUser(String token, UserDTO user) {
        throw  new NotImplementedException();
    }

    public HttpResponse getUser(String token, long userId) {
        throw  new NotImplementedException();
    }

    public HttpResponse editUser(String token, UserDTO user) {
        throw  new NotImplementedException();
    }

    public HttpResponse deleteUser(String token, long UserId) {
        throw  new NotImplementedException();
    }

    public HttpResponse questionBookmarks(String token, long UserId) {
        throw  new NotImplementedException();
    }

    public HttpResponse addBookmark(String token, long QuestionId) {
        throw  new NotImplementedException();
    }
}
