package prr;

import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import prr.clients.Client;
import prr.exceptions.ClientNotificationsAlreadyEnabledException;
import prr.exceptions.ClientNotificationsAlreadyDisabledException;
import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.IllegalEntryException;
import prr.exceptions.InvalidTerminalKeyException;
import prr.exceptions.UnrecognizedEntryException;
import prr.exceptions.UnknownClientKeyException;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import prr.terminals.Terminal;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;

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

	/**
	 * Parse and import a client entry from a plain text file.
	 * A correct client entry has the following format:
	 * {@code CLIENT|key|name|taxID}
	 *
	 * @param fields The fields of the client to import
	 * @throws IllegalEntryException if the entry contains an illegal field
	 */
	private void importClient(String[] fields) throws IllegalEntryException {
		try {
			this.registerClient(fields[1], fields[2], fields[3]);
		} catch (DuplicateClientKeyException e) {
			throw new IllegalEntryException(fields);
		}
	}

	/**
	 * Parse and import a basic terminal entry from a plain text file.
	 * A correct basic terminal entry has the following format:
	 * {@code BASIC|key|ClientKey|State}
	 *
	 * @param fields The fields of the client to import
	 * @throws IllegalEntryException if the entry contains an illegal field
	 */
	private void importTerminal(String[] fields) throws IllegalEntryException {
		try {
			this.registerTerminal(fields[1], fields[0], fields[2], fields[3]);
		} catch (DuplicateTerminalKeyException | InvalidTerminalKeyException
				| UnknownClientKeyException e) {
			throw new IllegalEntryException(fields);
		}
	}

	/**
	 * Parse and import an entry (line) from a plain text file.
	 *
	 * @param fields The fields of the entry to import
	 * @throws UnrecognizedEntryException if the entry type is unknown and not
	 *                                    supported by the program
	 * @throws IllegalEntryException      if the entry contains an illegal field
	 */
	private void importFromFields(String[] fields)
			throws UnrecognizedEntryException, IllegalEntryException {
		switch (fields[0]) {
			case "CLIENT" -> this.importClient(fields);
			case "BASIC" -> this.importTerminal(fields);
			case "FANCY" -> this.importTerminal(fields);
			/*
			 * case "FRIENDS" -> this.FIX_ME(fields);
			 */
			default -> throw new UnrecognizedEntryException(String.join("|", fields));
		}
	}

	/**
	 * Read text input file and create corresponding entities.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO erro while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, IllegalEntryException {
		try (BufferedReader s = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = s.readLine()) != null) {
				importFromFields(line.split("\\|"));
			}
		}
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
	 * Register new terminal.
	 * 
	 * @param key       terminal key
	 * @param type      terminal type
	 * @param clientKey client key
	 * @throws DuplicateClientKeyException if client key already exists
	 */
	public void registerTerminal(String key, String type, String clientKey)
			throws DuplicateTerminalKeyException, InvalidTerminalKeyException, UnknownClientKeyException {
		if (_terminals.containsKey(key)) {
			throw new DuplicateTerminalKeyException(key);
		} else {
			if (type.equals("BASIC")) {
				Terminal terminal = new BasicTerminal(key, clientKey);
				this._terminals.put(key, terminal);
				_clients.get(clientKey).addTerminal(terminal);
			} else {
				Terminal terminal = new FancyTerminal(key, clientKey);
				this._terminals.put(key, terminal);
				_clients.get(clientKey).addTerminal(terminal);
			}
			this.dirty();
		}
	}

	/**
	 * Register new terminal.
	 * 
	 * @param key       terminal key
	 * @param type      terminal type
	 * @param clientKey client key
	 * @throws DuplicateClientKeyException if client key already exists
	 */
	public void registerTerminal(String key, String type, String clientKey, String state)
			throws DuplicateTerminalKeyException, InvalidTerminalKeyException, UnknownClientKeyException {
		if (_terminals.containsKey(key)) {
			throw new DuplicateTerminalKeyException(key);
		} else {
			if (type.equals("BASIC")) {
				Terminal terminal = new BasicTerminal(key, clientKey, state);
				this._terminals.put(key, terminal);
				_clients.get(clientKey).addTerminal(terminal);
			} else {
				Terminal terminal = new FancyTerminal(key, clientKey, state);
				this._terminals.put(key, terminal);
				_clients.get(clientKey).addTerminal(terminal);
			}
			this.dirty();
		}
	}

	/**
	 * 
	 * @return all clients
	 */
	public Collection<Client> clients() {
		return _clients.values();
	}

	/**
	 * 
	 * @return all terminals
	 */
	public Collection<Terminal> terminals() {
		return _terminals.values();
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
			if (client.getNotifiable() == true) {
				client.disableNotifiable();
				this.dirty();
			} else {
				throw new ClientNotificationsAlreadyDisabledException();
			}
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
			if (client.getNotifiable() == false) {
				client.enableNotifiable();
				this.dirty();
			} else {
				throw new ClientNotificationsAlreadyEnabledException();
			}
		} else {
			throw new UnknownClientKeyException(key);
		}
	}
}