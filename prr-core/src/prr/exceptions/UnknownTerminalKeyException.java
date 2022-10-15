package prr.exceptions;

public class UnknownTerminalKeyException extends Exception {
    private static final long serialVersionUID = 202210151609L;

    private String _key;

    public UnknownTerminalKeyException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
    
}
