package prr.exceptions;

public class DuplicateClientKeyException extends Exception {
    private static final long serialVersionUID = 202210121015L;

    private String _key;

    public DuplicateClientKeyException(String key) {
    _key = key;
    }

    public String getKey() {
        return _key;
    }
}
