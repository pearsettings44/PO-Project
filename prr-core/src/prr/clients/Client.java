package prr.clients;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import prr.TarrifPlans.BaseTarrifPlan;
import prr.TarrifPlans.TarrifPlan;
import prr.communications.Communication;
import prr.terminals.Terminal;

public class Client implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202210121030L;

    /** The client's key. */
    private String _key;

    /** The client's name. */
    private String _name;

    /** The client's tax id number. */
    private int _taxId;

    /** Total payments made by this client. */
    private float _payments;

    /** Total debts of this client. */
    private float _debts;

    /** Notifications setting. */
    private boolean _notifiable;

    /** Client level */
    private Level _level;

    /** Client terminals */
    private Map<String, Terminal> _terminals;

    private TarrifPlan _tarrifPlan;

    /**
     * Constructor.
     * 
     * @param key        the client's key
     * @param name       the client's name
     * @param taxId      the client's tax id number
     * @param payments   total payments made by this client
     * @param debts      total debts of this client
     * @param notifiable notifications setting
     */
    public Client(String key, String name, int taxId) {
        _key = key;
        _name = name;
        _taxId = taxId;
        _level = new NormalLevelClient(this);
        _payments = 0;
        _debts = 0;
        _notifiable = true;
        _terminals = new TreeMap<>();
        _tarrifPlan = new BaseTarrifPlan();
    }

    /**
     * @return the client's key
     */
    public String getKey() {
        return _key;
    }

    public TarrifPlan getTarrifPlan() {
        return _tarrifPlan;
    }

    /**
     * @return the client's name
     */
    public String getName() {
        return _name;
    }

    /**
     * @return the client's tax id number
     */
    public int getTaxId() {
        return _taxId;
    }

    /**
     * @return the total payments made by this client
     */
    public float getPayments() {
        return _payments;
    }

    /**
     * @return the total debts of this client
     */
    public float getDebts() {
        return _debts;
    }

    /**
     * @return the notifications setting
     */
    public boolean getNotifiable() {
        return _notifiable;
    }

    /**
     * disable client notifications
     */
    public void disableNotifiable() {
        _notifiable = false;
    }

    /**
     * enable client notifications
     */
    public void enableNotifiable() {
        _notifiable = true;
    }

    /**
     * @return the client's number of terminals
     */
    public int numberOfTerminals() {
        return _terminals.size();
    }

    /**
     * Adds a terminal to the client's terminals
     */
    public void addTerminal(Terminal terminal) {
        _terminals.put(terminal.getKey(), terminal);
    }

    /**
     * Gets a terminal from the client's terminals
     */
    public Terminal getTerminal(String key) {
        return _terminals.get(key);
    }

    public Level getLevel() {
        return _level;
    }

    public Collection<Communication> getSentCommunications() {
        Map<Integer, Communication> communications = new TreeMap<>();
        for (Terminal terminal : _terminals.values()) {
            communications.putAll(terminal.getSentCommunications());
        }
        return communications.values();
    }

    public Collection<Communication> getReceivedCommunications() {
        Map<Integer, Communication> communications = new TreeMap<>();
        for (Terminal terminal : _terminals.values()) {
            communications.putAll(terminal.getReceivedCommunications());
        }
        return communications.values();
    }

    public abstract class Level implements Serializable {
        private static final long serialVersionUID = 202210121157L;

        public abstract String getLevelName();
    }

    @Override
    public String toString() {
        return String.format("CLIENT|%s|%s|%d|%s|%s|%d|%d|%d", _key, _name, _taxId,
                _level.getLevelName(), _notifiable ? "YES" : "NO",
                numberOfTerminals(), Math.round(_payments),
                Math.round(_debts));
    }
}
