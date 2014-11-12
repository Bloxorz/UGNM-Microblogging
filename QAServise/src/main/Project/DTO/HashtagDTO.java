package Project.DTO;

/**
 * Created by Marv on 05.11.2014.
 */
public class HashtagDTO extends AbstractDTO{

    private String text;

    public HashtagDTO(long id) {
        super(id);
    }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }
}
