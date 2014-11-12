package Project.DTO;

import Project.General.Rating;

import java.sql.Timestamp;

/**
 * Created by Marv on 05.11.2014.
 */
public class AnswerDTO extends PostDTO {

    private Rating rating;

    public AnswerDTO(Timestamp timestamp, String text, long userId, Rating rating) {
        super(timestamp, text, userId);
        this.rating = rating;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
