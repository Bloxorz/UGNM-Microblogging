package i5.las2peer.services.servicePackage.DTO;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Marv on 05.11.2014.
 */
public class UserDTO extends  AbstractDTO {
    @Expose
    private List<HashtagDTO> hashtags;
    private long idUser;
    @Expose
    private int elo;

    public UserDTO() {

    }
    public UserDTO(long idUser, int elo) {
        this(idUser, elo, null);
    }
    public UserDTO(long idUser, int elo, List<HashtagDTO> hashtags) {
        this.idUser = idUser;
        this.elo = elo;
        this.hashtags = hashtags;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public List<HashtagDTO> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<HashtagDTO> hashtags) {
        this.hashtags = hashtags;
    }
}
