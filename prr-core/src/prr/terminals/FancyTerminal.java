package prr.terminals;

import prr.clients.Client;

public class FancyTerminal extends Terminal {
    private String _type = "FANCY";

    public FancyTerminal(String key, Client client) {
        super(key, client);
    }

    public FancyTerminal(String key, Client client, String state) {
        super(key, client, state);
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
        return String.format("%s|%s|%s|%s|%d|%d%s", getType(), getKey(), getClient().getKey(),
                getState(), Math.round(getPayments()),
                Math.round(getDebts()), hasFriends() ? "|" + friendsToString() : "");
    }
}
