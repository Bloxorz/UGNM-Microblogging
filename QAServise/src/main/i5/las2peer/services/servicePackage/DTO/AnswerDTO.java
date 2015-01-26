package i5.las2peer.services.servicePackage.DTO;

import i5.las2peer.services.servicePackage.General.int;

import java.util.Date;

/**
 * Created by Marv on 05.11.2014.
 */
public class AnswerDTO extends PostDTO {

    private int rating;
    private long questionId;

    public AnswerDTO(long id, Date timestamp, String text, long userId, int rating, long questionId) {
        super(id, timestamp, text, userId);
        this.rating = rating;
        this.questionId = questionId;
    }

    public AnswerDTO(){
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int  rating) {
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
