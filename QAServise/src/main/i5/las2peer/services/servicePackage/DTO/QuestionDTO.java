package i5.las2peer.services.servicePackage.DTO;

import java.sql.Timestamp;

/**
 * Created by Marv on 05.11.2014.
 */
public class QuestionDTO extends PostDTO {

    public QuestionDTO(long id, Timestamp timestamp, String text, long userId) {
        super(id, timestamp, text, userId);
    }

    public QuestionDTO() {
        super();
    }

    @Override
    public boolean wellformed() {
        return false;
    }


}
