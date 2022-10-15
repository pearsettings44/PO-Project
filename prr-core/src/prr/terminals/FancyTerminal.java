package prr.terminals;

public class FancyTerminal extends Terminal {
    private String _type;

    public FancyTerminal(String key, String clientKey) {
        super(key, clientKey);
        _type = "FANCY";
    }

    public FancyTerminal(String key, String clientKey, String state) {
        super(key, clientKey, state);
        _type = "FANCY";
    }

    @Override
    public String toString() {
        return String.format("FANCY|%s|%s|%s|%d|%d", getKey(), getClient(),
                getState(), Math.round(getPayments()),
                Math.round(getDebts()));
    }
}
