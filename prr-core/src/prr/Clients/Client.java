package prr.Clients;

import java.io.Serializable;

public class Client implements Serializable {

    /** Serial number for serialization. */
    private static final long serialVersionUID = 202210121030L;

    /** The client's key. */
    private String _key;

    /** The client's name. */
    private String _name;

    /** The client's tax id number. */
    private String _taxId;

    /** Total payments made by this client. */
    private float _payments;

    /** Total debts of this client. */
    private float _debts;

    /** Notifications setting. */
    private boolean _notifiable;

    /** Client level */
    /* private Level _level; */

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
    public Client(String key, String name, String taxId) {
        _key = key;
        _name = name;
        _taxId = taxId;
        /* _level = new NormalLevelClient(); */
        _payments = 0;
        _debts = 0;
        _notifiable = true;
    }

    /**
     * @return the client's key
     */
    public String getKey() {
        return _key;
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
    public String getTaxId() {
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

    /*
     * public abstract class Level implements Serializable {
     * // FIXME define serial number
     * 
     * }
     */
}
