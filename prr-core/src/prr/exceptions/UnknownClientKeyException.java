package prr.exceptions;

public class UnknownClientKeyException extends Exception {
    private static final long serialVersionUID = 202210121819L;

    private String _key;

    public UnknownClientKeyException(String key) {
        _key = key;
    }

    public String getKey() {
        return _key;
    }
}