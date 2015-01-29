package i5.las2peer.services.servicePackage.DTO;

/**
 * Created by Marv on 05.11.2014.
 */
public class ExpertiseDTO extends AbstractDTO {

    private  long idExpertise;
    private String text;

    public ExpertiseDTO() {
    }

    public ExpertiseDTO(long id, String text) { setIdExpertise(id); setText(text); }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    public long getIdExpertise() {
        return idExpertise;
    }

    public void setIdExpertise(long idExpertise) {
        this.idExpertise = idExpertise;
    }
}
