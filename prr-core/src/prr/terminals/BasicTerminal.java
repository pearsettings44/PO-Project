package prr.terminals;

public class BasicTerminal extends Terminal {
    private String _type;

    public BasicTerminal(String key, String clientKey) {
        super(key, clientKey);
        _type = "BASIC";
    }

    public BasicTerminal(String key, String clientKey, String state) {
        super(key, clientKey);
        _type = "BASIC";
    }

    @Override
    public String toString() {
        return String.format("BASIC|%s|%s|%s|%d|%d",getKey(), getClient(),
                getState(), Math.round(getPayments()),
                Math.round(getDebts()));
    }

}
