package prr.terminals;

public class BasicTerminal extends Terminal {
    private String _type = "BASIC";

    public BasicTerminal(String key, String clientKey) {
        super(key, clientKey);
    }

    public BasicTerminal(String key, String clientKey, String state) {
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
        return String.format("%s|%s|%s|%s|%d|%d%s", getType(), getKey(), getClient(),
                getState(), Math.round(getPayments()),
                Math.round(getDebts()), hasFriends() ? "|" + friendsToString() : "");
    }

}
