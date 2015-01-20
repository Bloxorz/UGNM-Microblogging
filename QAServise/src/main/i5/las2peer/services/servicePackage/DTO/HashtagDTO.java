package i5.las2peer.services.servicePackage.DTO;

/**
 * Created by Marv on 05.11.2014.
 */
public class HashtagDTO extends AbstractDTO{

    private String text;
    
    public HashtagDTO() {
        super();
    }

    public HashtagDTO(long id) {
        super(id);
    }

    public HashtagDTO(long id, String text) { super(id); setText(text); }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    @Override
    public boolean equals(Object other) {
        return this.getText().equals(((HashtagDTO)other).getText())
                && (this.getId() == ((AbstractDTO)other).getId());
    }
}
