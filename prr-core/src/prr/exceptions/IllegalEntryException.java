package prr.exceptions;

/**
 * Exception for known import file entries that do not provide the correct
 * fields.
 */
public class IllegalEntryException extends Exception {

    /**
     * Class serial number.
     */
    private static final long serialVersionUID = 202210141627L;

    /**
     * Illegal entry fields.
     */
    private String[] _entryFields;

    /**
     * @param entryFields
     */
    public IllegalEntryException(String[] entryFields) {
        _entryFields = entryFields;
    }

    /**
     * @param entryFields
     * @param cause
     */
    public IllegalEntryException(String[] entryFields, Exception cause) {
        super(cause);
        _entryFields = entryFields;
    }

    /**
     * @return the illegal entry specification.
     */
    public String[] getEntrySpecification() {
        return _entryFields;
    }

}