package Project.DTO;

import java.sql.Timestamp;

/**
 * Created by Marv on 05.11.2014.
 */
public class PostDTO {
    private Timestamp timestamp;
    private String text;
    private long userId;

    public PostDTO(Timestamp timestamp, String text, long userId) {
        this.timestamp = timestamp;
        this.text = text;
        this.userId = userId;
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
}
