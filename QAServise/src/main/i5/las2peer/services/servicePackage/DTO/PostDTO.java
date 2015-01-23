package i5.las2peer.services.servicePackage.DTO;

import java.util.Date;

/**
 * Created by Marv on 05.11.2014.
 */
public class PostDTO extends AbstractDTO{

    private Date timestamp;
    private String text;
    private long userId;

    public PostDTO(long id, Date date, String text, long userId) {
        super(id);
        this.timestamp = date;
        this.text = text;
        this.userId = userId;
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

    @Override
    public boolean equals(Object other) {
        PostDTO that = (PostDTO) other;
        return (this.getId() == that.getId())
                && (this.getText().equals(that.getText()))
                && (this.getTimestamp().equals(that.getTimestamp()))
                && (this.getUserId() == that.getUserId());
    }

    @Override
    public String toString() {
        return super.toString()+"{'timestamp':'"+getTimestamp()+"', 'text':'"+getText()+"', 'userId':'"+getUserId()+"'}";
    }
}
