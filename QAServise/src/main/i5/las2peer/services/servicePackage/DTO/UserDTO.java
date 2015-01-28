package i5.las2peer.services.servicePackage.DTO;

/**
 * Created by Marv on 05.11.2014.
 */
public class UserDTO {
    private long idUser;
    private int elo;
    private String imagePath, contactInfo,email, pass;

    public UserDTO(long idUser, int elo, String imagePath, String contactInfo, String email) {
        this.idUser = idUser;
        this.elo = elo;
        this.imagePath = imagePath;
        this.contactInfo = contactInfo;
        this.email = email;
    }

    public UserDTO() {
        this(0, 0, null, null, null);
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

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

}
