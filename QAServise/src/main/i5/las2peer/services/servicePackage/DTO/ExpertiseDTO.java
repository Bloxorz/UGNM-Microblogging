package i5.las2peer.services.servicePackage.DTO;

/**
 * Created by Marv on 05.11.2014.
 */
public class ExpertiseDTO extends AbstractDTO{

    private String text;

    public ExpertiseDTO() {
        super();
    }

    public ExpertiseDTO(long id) {
        super(id);
    }

    public ExpertiseDTO(long id, String text) { super(id); setText(text); }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    @Override
    public boolean equals(Object other) {
        return this.getText().equals(((ExpertiseDTO)other).getText())
                && (this.getId() == ((AbstractDTO)other).getId());
    }
}
