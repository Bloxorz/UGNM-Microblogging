package Project.DTO;

import Project.Exceptions.NotWellFormedException;

/**
 * Created by Marv on 05.11.2014.
 */
public interface IDTO {

    public void setId(long id);

    /**
     * @return
     */
    public long getId();

    /**
     * Describes wether or not a DTO has at least it's minimum parameters set
     * @return
     */
    public boolean wellformed() throws NotWellFormedException;
}
