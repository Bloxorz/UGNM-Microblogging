package Project.DTO;

/**
 * Created by Marv on 05.11.2014.
 */
public class AbstractDTO implements IDTO {

    private long id;

    @Override
    public void setId() {

    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }
}
