package Project.DTO;

import java.sql.Timestamp;

/**
 * Created by Marv on 05.11.2014.
 */
public class QuestionDTO extends PostDTO {

    public QuestionDTO(Timestamp timestamp, String text, long userId) {
        super(timestamp, text, userId);
    }
}
