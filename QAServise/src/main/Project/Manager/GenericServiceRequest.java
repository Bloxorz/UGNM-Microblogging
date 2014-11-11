package Project.Manager;

/**
 * A wrapper for a ManagerFacade-function-call. To be used dynamically created.
 */
 public interface GenericServiceRequest {
    public String toJson() throws java.sql.SQLException, Project.Exceptions.HTTPNotFoundException, com.google.gson.JsonParseException;
 }