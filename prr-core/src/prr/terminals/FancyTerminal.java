package prr.terminals;

public class FancyTerminal extends Terminal {
    private String _type = "FANCY";

    public FancyTerminal(String key, String clientKey) {
        super(key, clientKey);
    }

    public FancyTerminal(String key, String clientKey, String state) {
        super(key, clientKey, state);
    }

    /**
     * Get the terminal's type
     * 
     * @return the terminal's type
     */
    public String getType() {
        return _type;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%d|%d", getType(), getKey(), getClient(),
                getState(), Math.round(getPayments()),
                Math.round(getDebts()));
    }
}
