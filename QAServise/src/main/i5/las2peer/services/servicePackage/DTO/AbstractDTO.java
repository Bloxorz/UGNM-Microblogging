package i5.las2peer.services.servicePackage.DTO;

import com.google.gson.Gson;

/**
 * Created by Marv on 05.11.2014.
 */
public abstract class AbstractDTO {

    @Override
    public boolean equals(Object other) {
        Gson g = new Gson();
        return g.toJson(this).equals(g.toJson(other));
    }

    @Override
    public String toString() {
        return (new Gson()).toJson(this);
    }
}
