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
import prr.exceptions.UnknownTerminalKeyException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import prr.terminals.Terminal;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;

/**
 * Class Network implements a network.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/* Clients */
	private Map<String, Client> _clients = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	/* Terminals */
	private Map<String, Terminal> _terminals = new TreeMap<>();

	/**
	 * Network's dirty state, which represents if the network was modified since
	 * the last time it was saved/created.
	 */
	private boolean _dirty = false;

	/**
	 * Get the network's dirty state.
	 * 
	 * @return the network's dirty state
	 */
	public boolean isDirty() {
		return _dirty;
	}

	/**
	 * Set the dirty flag to false, representing that the network has been saved
	 */
	public void clean() {
		_dirty = false;
	}

	/**
	 * Set the dirty flag to true, representing that the network has been modified
	 */
	private void dirty() {
		_dirty = true;
	}

	/**
	 * Get a certain terminal
	 * 
	 * @param key the terminal's key
	 * @return the terminal
	 * @throws UnknownTerminalKeyException if the key does not belong to any
	 *                                     terminal
	 */
	public Terminal getTerminal(String key) throws UnknownTerminalKeyException {
		if (!_terminals.containsKey(key))
			throw new UnknownTerminalKeyException(key);
		else
			return _terminals.get(key);
	}

	/**
	 * Get a certain client
	 * 
	 * @param key the client's key
	 * @return the client
	 * @throws UnknownClientKeyException if the key does not belong to any client
	 */
	public Client getClient(String key) throws UnknownClientKeyException {
		if (!_clients.containsKey(key))
			throw new UnknownClientKeyException(key);
		else
			return _clients.get(key);
	}

	/**
	 * @return all clients
	 */
	public Collection<Client> clients() {
		return _clients.values();
	}

	/**
	 * @return all terminals
	 */
	public Collection<Terminal> terminals() {
		return _terminals.values();
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
			this.registerClient(fields[1], fields[2], Integer.parseInt(fields[3]));
			this.dirty();
		} catch (DuplicateClientKeyException e) {
			throw new IllegalEntryException(fields);
		}
	}

	/**
	 * Parse and import a terminal entry from a plain text file.
	 * A correct terminal entry has the following format:
	 * {@code TYPE|key|ClientKey|State}
	 *
	 * @param fields The fields of the terminal to import
	 * @throws IllegalEntryException if the entry contains an illegal field
	 */
	private void importTerminal(String[] fields) throws IllegalEntryException {
		try {
			this.registerTerminal(fields[1], fields[0], fields[2], fields[3]);
			this.dirty();
		} catch (DuplicateTerminalKeyException | InvalidTerminalKeyException
				| UnknownClientKeyException e) {
			throw new IllegalEntryException(fields);
		}
	}

	/**
	 * Parse and import a friend terminal entry from a plain text file.
	 * A correct friend terminal entry has the following format:
	 * {@code FRIEND|TerminalKey|<FriendKey1>,<FriendKey2>,...}
	 * 
	 * @param fields The fields of the friends to import
	 * @throws IllegalEntryException if the entry contains an illegal field
	 */
	private void importFriends(String[] fields) throws IllegalEntryException {
		String[] friendsKeys = fields[2].split(",");
		try {
			Terminal terminal = getTerminal(fields[1]);
			for (String friendKey : friendsKeys) {
				terminal.insertFriend(friendKey, getTerminal(friendKey));
				this.dirty();
			}
		} catch (UnknownTerminalKeyException e) {
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
			case "FRIENDS" -> this.importFriends(fields);
			default -> throw new UnrecognizedEntryException(String.join("|", fields));
		}
	}

	/**
	 * Read text input file and create corresponding entries.
	 * 
	 * @param filename name of the text input file
	 * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException                if there is an IO error while processing
	 *                                    the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, IllegalEntryException {
		try (BufferedReader s = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = s.readLine()) != null)
				importFromFields(line.split("\\|"));
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
	public void registerClient(String key, String name, int taxID) throws DuplicateClientKeyException {
		if (_clients.containsKey(key))
			throw new DuplicateClientKeyException(key);
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
		if (_terminals.containsKey(key))
			throw new DuplicateTerminalKeyException(key);
		if (!_clients.containsKey(clientKey))
			throw new UnknownClientKeyException(clientKey);
		if (key.matches("[0-9]+") && key.length() != 6)
			throw new InvalidTerminalKeyException(key);
		if (type.equals("BASIC")) {
			Terminal terminal = new BasicTerminal(key, clientKey);
			this._terminals.put(key, terminal);
			_clients.get(clientKey).addTerminal(terminal);
		} else if (type.equals("FANCY")) {
			Terminal terminal = new FancyTerminal(key, clientKey);
			this._terminals.put(key, terminal);
			_clients.get(clientKey).addTerminal(terminal);
		} else
			throw new InvalidTerminalKeyException(key);
		this.dirty();
	}

	/**
	 * Register new terminal from an import file (contains the state).
	 * 
	 * @param key       terminal key
	 * @param type      terminal type
	 * @param clientKey client key
	 * @throws DuplicateClientKeyException if client key already exists
	 */
	public void registerTerminal(String key, String type, String clientKey, String state)
			throws DuplicateTerminalKeyException, InvalidTerminalKeyException, UnknownClientKeyException {
		if (_terminals.containsKey(key))
			throw new DuplicateTerminalKeyException(key);
		if (!_clients.containsKey(clientKey))
			throw new UnknownClientKeyException(clientKey);
		if (key.length() != 6)
			throw new InvalidTerminalKeyException(key);
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
			} else
				throw new ClientNotificationsAlreadyDisabledException();
		} else
			throw new UnknownClientKeyException(key);
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
			} else
				throw new ClientNotificationsAlreadyEnabledException();
		} else
			throw new UnknownClientKeyException(key);
	}

	/**
	 * Adds a certain terminal to the terminal friend list
	 * 
	 * @param terminal Terminal to accept a new friend
	 * @param key      Key of the new friend
	 * @throws UnknownTerminalKeyException if the terminal key does not exist
	 */
	public void addFriend(Terminal terminal, String key) throws UnknownTerminalKeyException {
		if (!terminal.getKey().equals(key))
			terminal.insertFriend(key, getTerminal(key));
		this.dirty();
	}

	/**
	 * Removes a certain terminal from the terminal friend list
	 * 
	 * @param terminal Terminal to remove a friend
	 * @param key      Key of the friend to remove
	 * @throws UnknownTerminalKeyException if the terminal key does not exist
	 */
	public void removeFriend(Terminal terminal, String key) throws UnknownTerminalKeyException {
		terminal.deleteFriend(key);
		this.dirty();
	}

	/**
	 * Gets all the terminals without any debts
	 * 
	 * @return List of terminals without any debts
	 */
	public List<Client> getClientsWithoutDebt() {
		List<Client> clients = new ArrayList<Client>();
		for (Client client : _clients.values()) {
			if (client.getDebts() == 0)
				clients.add(client);
		}
		return clients;
	}

	/**
	 * Gets all the Clients with debts, ordered.
	 * 
	 * @return all the clients with debts
	 */
	public List<Client> getClientsWithDebt() {
		List<Client> clients = new ArrayList<Client>();
		for (Client client : _clients.values()) {
			if (client.getDebts() > 0)
				clients.add(client);
		}
		Collections.sort(clients, new Comparator<Client>() {
			@Override
			public int compare(Client c1, Client c2) {
				return (int) Math.ceil(c2.getDebts() - c1.getDebts());
			}
		});
		return clients;
	}

	/**
	 * Get all the terminals with a positive balance
	 * 
	 * @return all the terminals with a positive balance
	 */
	public List<Terminal> getTerminalsWithPositiveBalance() {
		List<Terminal> terminals = new ArrayList<Terminal>();
		for (Terminal terminal : _terminals.values())
			if (terminal.getPayments() > terminal.getDebts())
				terminals.add(terminal);
		return terminals;
	}

	/**
	 * Get the total amount of payments made in the network
	 * 
	 * @return the total amount of payments made in the network
	 */
	public long getTotalPayments() {
		double total = 0;
		for (Client client : _clients.values())
			total += client.getPayments();
		return (long) total;
	}

	/**
	 * Get the total amount of debts in the network
	 * 
	 * @return the total amount of debts in the network
	 */
	public long getTotalDebts() {
		double total = 0;
		for (Client client : _clients.values())
			total += client.getDebts();
		return (long) total;
	}

	/**
	 * Get the amount of debt from a client
	 * 
	 * @param key Client key
	 * @return the amount of debt from the client
	 */
	public long getClientDebts(String key) {
		return (long) _clients.get(key).getDebts();
	}

	/**
	 * Get the amount of payments made by a client
	 * 
	 * @param key Client key
	 * @return the amount of payments made by the client
	 */
	public long getClientPayments(String key) {
		return (long) _clients.get(key).getPayments();
	}
}