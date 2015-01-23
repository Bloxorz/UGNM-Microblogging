package i5.las2peer.services.servicePackage.DTO;

import i5.las2peer.services.servicePackage.General.Rating;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Marv on 05.11.2014.
 */
public class AnswerDTO extends PostDTO {

    private Rating rating;
    private long questionId;

    public AnswerDTO(long id, Date timestamp, String text, long userId, Rating rating, long questionId) {
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

    @Override
    public boolean equals(Object other) {
        AnswerDTO that = (AnswerDTO) other;
        return super.equals(other)
                && (this.getQuestionId() == that.getQuestionId())
                && (this.getRating().equals(that.getRating()));
    }

    @Override
    public String toString() {
        return super.toString()+"{'rating':'"+getRating()+"', 'idQuestion':'"+getQuestionId()+"'}";
    }
}
