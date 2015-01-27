package i5.las2peer.services.servicePackage.DTO;

import com.google.gson.Gson;

import java.util.Date;

/**
 * Created by Marv on 05.11.2014.
 */
public class AnswerDTO extends PostDTO {

    private int rating;
    private long idQuestion;

    public AnswerDTO(long id, Date timestamp, String text, long userId, int rating, long idQuestion) {
        super(id, timestamp, text, userId);
        this.rating = rating;
        this.idQuestion = idQuestion;
    }

    public AnswerDTO(){
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int  rating) {
        this.rating = rating;
    }

    public long getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(long idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Override
    public boolean equals(Object other) {
        Gson g = new Gson();
        return g.toJson(this).equals(g.toJson(other));
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this);
    }
}
