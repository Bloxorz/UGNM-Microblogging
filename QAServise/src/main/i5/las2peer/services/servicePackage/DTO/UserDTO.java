package i5.las2peer.services.servicePackage.DTO;

import com.google.gson.Gson;

/**
 * Created by Marv on 05.11.2014.
 */
public class UserDTO extends  AbstractDTO {
    private long idUser;
    private int elo;
    private String image, contact,email;

    public UserDTO(long idUser, int elo, String image, String contact, String email) {
        this.idUser = idUser;
        this.elo = elo;
        this.image = image;
        this.contact = contact;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
