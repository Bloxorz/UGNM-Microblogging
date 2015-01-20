package i5.las2peer.services.servicePackage.Manager;

import i5.las2peer.services.servicePackage.Exceptions.CantFindException;

/**
 * A wrapper for a ManagerFacade-function-call. To be used dynamically created.
 */
 public interface GenericServiceRequest {
    public String toJson() throws java.sql.SQLException, CantFindException, com.google.gson.JsonParseException;
 }