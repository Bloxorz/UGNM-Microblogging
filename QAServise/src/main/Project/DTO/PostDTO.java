package Project.DTO;

import java.sql.Timestamp;

/**
 * Created by Marv on 05.11.2014.
 */
public class PostDTO extends AbstractDTO{

    private Timestamp timestamp;
    private String text;
    private long userId;

    public PostDTO(long id, Timestamp timestamp, String text, long userId) {
        super(id);
        this.timestamp = timestamp;
        this.text = text;
        this.userId = userId;
    }

    public PostDTO() {

    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean wellformed() {
        return false;
    }
}
