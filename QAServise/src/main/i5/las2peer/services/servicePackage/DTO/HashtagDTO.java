package i5.las2peer.services.servicePackage.DTO;

import com.google.gson.Gson;

/**
 * Created by Marv on 05.11.2014.
 */
public class HashtagDTO extends AbstractDTO {

    private String text;
    private long idHashtag;
    
    public HashtagDTO() {
    }

    public HashtagDTO(long idHashtag) {
        setIdHashtag(idHashtag);
    }

    public HashtagDTO(long idHashtag, String text) { this(idHashtag); setText(text); }

    public String getText() {
        return text;
    }

    public void setText(String name) {
        this.text = name;
    }

    public long getIdHashtag() {
        return idHashtag;
    }

    public void setIdHashtag(long idHashtag) {
        this.idHashtag = idHashtag;
    }
}
