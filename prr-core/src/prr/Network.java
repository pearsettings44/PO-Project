package prr;

import java.io.Serializable;
import java.io.IOException;

import prr.clients.Client;
import prr.exceptions.ClientNotificationsAlreadyEnabledException;
import prr.exceptions.ClientNotificationsAlreadyDisabledException;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.UnknownClientKeyException;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import prr.terminals.Terminal;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/* Clients */
	private Map<String, Client> _clients = new TreeMap<>();

	/* Terminals */
	private Map<String, Terminal> _terminals = new TreeMap<>();

	/**
	 * Network's dirty state, which represents if the network was modified since
	 * the last time it was saved/created.
	 */
	private boolean dirty = false;

	/**
	 * Get the network's dirty state.
	 * 
	 * @return the network's dirty state
	 */
	public boolean isDirty() {
		return this.dirty;
	}

	/**
	 * Set the dirty flag to false, representing that the network has been saved
	 */
	public void clean() {
		this.dirty = false;
	}

	/**
	 * Set the dirty flag to true, representing that the network has been modified
	 */
	private void dirty() {
		this.dirty = true;
	}

	// FIXME define attributes
	// FIXME define contructor(s)
	// FIXME define methods

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO erro while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */ {
		// FIXME implement method
	}

	/**
	 * Register new client.
	 * 
	 * @param key   client key
	 * @param name  client name
	 * @param taxId client tax id
	 * @throws DuplicateClientKeyException if client key already exists
	 */
	public void registerClient(String key, String name, String taxID) throws DuplicateClientKeyException {
		if (_clients.containsKey(key)) {
			throw new DuplicateClientKeyException(key);
		}
		Client client = new Client(key, name, taxID);
		this._clients.put(key, client);
		this.dirty();
	}

	/**
	 * 
	 * @return all clients
	 */
	public Collection<Client> clients() {
		return _clients.values();
	}

	/**
	 * Disable client notifications.
	 * 
	 * @param key client key
	 * @throws UnknownClientKeyException                   if client key does not
	 *                                                     exist
	 * @throws ClientNotificationsAlreadyDisabledException if client notifications
	 *                                                     are already disabled
	 */
	public void disableClientNotifications(String key) throws UnknownClientKeyException,
			ClientNotificationsAlreadyDisabledException {
		Client client = _clients.get(key);
		if (client != null) {
			if (client.getNotifiable() == true)
				client.disableNotifiable();
			else
				throw new ClientNotificationsAlreadyDisabledException();
		} else {
			throw new UnknownClientKeyException(key);
		}
	}

	/**
	 * Enable client notifications.
	 * 
	 * @param key client key
	 * @throws UnknownClientKeyException                  if client key does not
	 *                                                    exist
	 * @throws ClientNotificationsAlreadyEnabledException if client notifications
	 *                                                    are already enabled
	 * 
	 */
	public void enableClientNotifications(String key)
			throws UnknownClientKeyException, ClientNotificationsAlreadyEnabledException {
		Client client = _clients.get(key);
		if (client != null) {
			if (client.getNotifiable() == false)
				client.enableNotifiable();
			else
				throw new ClientNotificationsAlreadyEnabledException();
		} else {
			throw new UnknownClientKeyException(key);
		}
	}
}