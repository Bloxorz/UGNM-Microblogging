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
    private int favourCount;
    private boolean isFavourite;

    public QuestionDTO(long id, Date date, String text, long userId) {
        super(id, date, text, userId);
        isFavourite = false;
    }
    public QuestionDTO(long id, Date date, String text, long userId, List<HashtagDTO> hashtags) {
        this(id, date, text, userId);
        this.hashtags = hashtags;
    }
    public QuestionDTO(long id, Date date, String text, long userId, List<HashtagDTO> hashtags, int favourCount) {
        this(id, date, text, userId, hashtags);
        this.favourCount = favourCount;
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

    public int getFavourCount() {
        return favourCount;
    }

    public void setFavourCount(int favourCount) {
        this.favourCount = favourCount;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }
}
