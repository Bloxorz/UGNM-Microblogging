package Project.DTO;

/**
 * Created by Marv on 05.11.2014.
 */
public interface IDTO {

    public void setId(long id);
    public long getId();

    /**
     * Describes wether or not a DTO has at least it's minimum parameters set
     * @return
     */
    public boolean wellformed();
}
