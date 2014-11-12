package Project.Manager;

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
}
