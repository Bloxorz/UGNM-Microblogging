package i5.las2peer.services.servicePackage.Exceptions;

/**
 * Helping to process errors that should result in a http-status-code of 404
 */
public class CantFindException extends Exception {
    public CantFindException() {
    }
    public CantFindException(String s) {
        super(s);
    }
}