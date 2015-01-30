package i5.las2peer.services.servicePackage.DTO;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Marv on 05.11.2014.
 */
public class PostDTO extends AbstractDTO{

    @Expose
    private Date timestamp;
    @Expose
    private String text;
    private long idUser;
    @Expose
    private long idPost;

    public PostDTO(long idPost, Date date, String text, long idUser) {
        this.idPost = idPost;
        this.timestamp = date;
        this.text = text;
        this.idUser = idUser;
    }

    public PostDTO() {

    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date date) {
        this.timestamp = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getIdPost() {
        return idPost;
    }

    public void setIdPost(long idPost) {
        this.idPost = idPost;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

}
