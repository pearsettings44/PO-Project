package prr.Clients;

import java.io.Serializable;

public class Client implements Serializable {

    /** Serial number for serialization. */
    // FIXME define serial number

    /** The client's id. */
    private String _id;

    /** The client's name. */
    private String _name;

    /** The client's tax id number. */
    private int _nif;

    /** Total payments made by this client. */
    private float _payments;

    /** Total debts of this client. */
    private float _debts;

    /** Notifications setting. */
    private boolean _notifiable;

    /** Client level */
    private Level _level;

        /**
     * Constructor.
     * 
     * @param id         the client's id
     * @param name       the client's name
     * @param nif        the client's tax id number
     * @param payments   total payments made by this client
     * @param debts      total debts of this client
     * @param notifiable notifications setting
     */
    Client(String id, String name, int nif, float payments, float debts, boolean notifiable) {
        _id = id;
        _name = name;
        _nif = nif;
        _payments = payments;
        _debts = debts;
        _notifiable = notifiable;
    }

    /**
     * @return the client's id
     */
    public String getId() {
        return _id;
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
    public int getNif() {
        return _nif;
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

    public abstract class Level implements Serializable {
        // FIXME define serial number

    }
}
