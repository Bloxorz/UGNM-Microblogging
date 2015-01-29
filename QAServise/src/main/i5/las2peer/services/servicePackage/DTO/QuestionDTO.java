package i5.las2peer.services.servicePackage.DTO;

import com.google.gson.Gson;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Marv on 05.11.2014.
 */
public class QuestionDTO extends PostDTO {

    private List<HashtagDTO> hashtags;

    public QuestionDTO(long id, Date date, String text, long userId) {
        super(id, date, text, userId);
    }
    public QuestionDTO(long id, Date date, String text, long userId, List<HashtagDTO> hashtags) {
        this(id, date, text, userId);
        this.hashtags = hashtags;
    }

    public QuestionDTO() {
        super();
    }

    public List<HashtagDTO> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<HashtagDTO> hashtags) {
        this.hashtags = hashtags;
    }
}
