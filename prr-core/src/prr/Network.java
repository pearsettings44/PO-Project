package prr;

import java.io.Serializable;
import java.io.IOException;

import prr.clients.Client;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.UnrecognizedEntryException;

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
	}

	/**
	 * 
	 * @return all clients
	 */
	public Collection<Client> clients() {
		return _clients.values();
	}
}