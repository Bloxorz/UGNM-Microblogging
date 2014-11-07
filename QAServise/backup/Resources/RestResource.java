package Project.Resources;

import i5.las2peer.services.servicePackage.Manager.ManagerFacade;
import i5.las2peer.api.Service;
import i5.las2peer.restMapper.RESTMapper;
import i5.las2peer.restMapper.tools.ValidationResult;
import i5.las2peer.restMapper.tools.XMLCheck;
import i5.las2peer.services.servicePackage.database.DatabaseManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Marv on 05.11.2014.
 */
public abstract class RestResource extends Service {

    protected DatabaseManager dbm;
    protected ManagerFacade mf;

    private String jdbcDriverClassName;
    private String jdbcLogin;
    private String jdbcPass;
    private String jdbcUrl;
    private String jdbcSchema;

    protected final Logger LOGGER;
    {
        LOGGER = Logger
                .getLogger(getClass().getSimpleName());
    }

    public RestResource() {
        try {
            setFieldValues();
            dbm = new DatabaseManager(jdbcDriverClassName, jdbcLogin, jdbcPass, jdbcUrl, jdbcSchema);
            mf = new ManagerFacade(dbm.getConnection());
        }catch(SQLException e) {
            LOGGER.log(Level.SEVERE, "Could NOT establish DB-Connection");
        }
    }

    /**
     * A {@link Logger} for this class with name for
     * {@link Class#getSimpleName()}.
     */


    protected Connection getConnection() {
        try {
            return dbm.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "dbm connection lost");
            return null;
        }
    }


    /**
     * Method for debugging purposes.
     * Here the concept of restMapping validation is shown.
     * It is important to check, if all annotations are correct and consistent.
     * Otherwise the service will not be accessible by the WebConnector.
     * Best to do it in the unit tests.
     * To avoid being overlooked/ignored the method is implemented here and not in the test section.
     * @return  true, if mapping correct
     */
    public boolean debugMapping() {
        String XML_LOCATION = "./restMapping.xml";
        String xml = getRESTMapping();

        try {
            RESTMapper.writeFile(XML_LOCATION, xml);
        } catch (IOException e) {
            e.printStackTrace();
        }

        XMLCheck validator = new XMLCheck();
        ValidationResult result = validator.validate(xml);

        if (result.isValid())
            return true;
        return false;
    }

    /**
     * This method is needed for every RESTful application in LAS2peer. There is no need to change!
     *
     * @return the mapping
     */
    public String getRESTMapping() {
        String result = "";
        try {
            result = RESTMapper.getMethodsAsXML(this.getClass());
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
}
