package Project.DTO;

import Project.General.Rating;
/**
 * Created by Marv on 05.11.2014.
 */
public class UserDTO extends AbstractDTO {
    private int elo;
    private String imagePath, contactInfo,email, pass;

    public UserDTO(long id, int elo, String imagePath, String contactInfo, String email, String pass) {
        super(id);
        this.elo = elo;
        this.imagePath = imagePath;
        this.contactInfo = contactInfo;
        this.email = email;
        this.pass = pass;
    }

    public UserDTO() {
        super();
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
