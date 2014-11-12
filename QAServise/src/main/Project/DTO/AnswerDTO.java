package Project.DTO;

import Project.General.Rating;

import java.sql.Timestamp;

/**
 * Created by Marv on 05.11.2014.
 */
public class AnswerDTO extends PostDTO {

    private Rating rating;
    private long questionId;

    public AnswerDTO(long id, Timestamp timestamp, String text, long userId, Rating rating, long questionId) {
        super(id, timestamp, text, userId);
        this.rating = rating;
        this.questionId = questionId;
    }

    public AnswerDTO(){

    }

    @Override
    public boolean wellformed() {
        return false;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating  rating) {
        this.rating = rating;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }
}
