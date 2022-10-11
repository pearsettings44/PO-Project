package prr.app.clients;

import prr.Network;
import prr.app.exceptions.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

	DoRegisterClient(Network receiver) {
		super(Label.REGISTER_CLIENT, receiver);
                //FIXME add command fields
			addStringField("key", Prompt.key());
			addStringField("name", Prompt.name());
			addStringField("taxId", Prompt.taxId());
	}

	@Override
	protected final void execute() throws CommandException {
		String key = stringField("key");
		String name = stringField("name");
		String taxId = stringField("taxId");
		_receiver.registerClient(key, name, taxId);

	}

}
