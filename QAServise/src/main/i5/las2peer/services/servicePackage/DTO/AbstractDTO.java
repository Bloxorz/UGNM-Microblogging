package i5.las2peer.services.servicePackage.DTO;

/**
 * Created by Marv on 05.11.2014.
 */
public abstract class AbstractDTO implements IDTO {

    public long id;

    public AbstractDTO(long id) {
        this.id = id;
    }

    public AbstractDTO() {

    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean wellformed() {
        return false;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
