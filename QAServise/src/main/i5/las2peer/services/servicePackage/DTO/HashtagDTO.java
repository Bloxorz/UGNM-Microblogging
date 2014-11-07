package i5.las2peer.services.servicePackage.DTO;

import i5.las2peer.services.servicePackage.General.Rating;

/**
 * Created by Marv on 05.11.2014.
 */
public class HashtagDTO extends AbstractDTO{

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
