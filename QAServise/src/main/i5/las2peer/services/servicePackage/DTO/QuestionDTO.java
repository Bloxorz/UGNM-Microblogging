package i5.las2peer.services.servicePackage.DTO;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Marv on 05.11.2014.
 */
public class QuestionDTO extends PostDTO {

    public QuestionDTO(long id, Date date, String text, long userId) {
        super(id, date, text, userId);
    }

    public QuestionDTO() {
        super();
    }

    @Override
    public boolean wellformed() {
        return false;
    }


}
