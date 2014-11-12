package Project.Manager;

import Project.DTO.HashtagDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marv on 12.11.2014.
 */

//Singleton
public class ManagerFacade {

    private static ManagerFacade instance;

    private ManagerFacade() {}

    public static ManagerFacade getInstance() {
        if(instance == null)
            ManagerFacade.instance = new ManagerFacade();
        return instance;
    }

    public List<HashtagDTO> getHashtags() {
        List<HashtagDTO> result = new ArrayList<HashtagDTO>();
        //TODO
        return result;
    }
}
