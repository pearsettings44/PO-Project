package prr;

import java.io.Serializable;
import java.io.IOException;

import prr.Clients.Client;
import prr.exceptions.UnrecognizedEntryException;

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

	public void registerClient(String key, String name, String taxID){
		Client client = new Client(key, name, taxID);
	}
}
