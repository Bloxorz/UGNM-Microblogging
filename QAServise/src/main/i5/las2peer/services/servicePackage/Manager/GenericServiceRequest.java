package i5.las2peer.services.servicePackage.Manager;

/**
 * A wrapper for a ManagerFacade-function-call. To be used dynamically created.
 */
 public interface GenericServiceRequest {
    public String toJson() throws java.sql.SQLException, i5.las2peer.services.servicePackage.Exceptions.HTTPNotFoundException, com.google.gson.JsonParseException;
 }