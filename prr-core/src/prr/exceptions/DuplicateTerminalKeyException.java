package prr.exceptions;

public class DuplicateTerminalKeyException extends Exception {
    private static final long serialVersionUID = 202210151109L;

    private String _key;

    public DuplicateTerminalKeyException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}
