package Project.DTO;

import java.util.List;

/**
 * Created by Marv on 05.11.2014.
 */
public class ExpertiseDTO extends AbstractDTO{

    private String text;

    public ExpertiseDTO(long id) {
        super(id);
    }

    public ExpertiseDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
