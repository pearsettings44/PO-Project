package prr.app.lookups;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Show communications from a client.
 */
class DoShowCommunicationsFromClient extends Command<Network> {

	DoShowCommunicationsFromClient(Network receiver) {
		super(Label.SHOW_COMMUNICATIONS_FROM_CLIENT, receiver);
		addStringField("key", Prompt.clientKey());
	}

	@Override
	protected final void execute() throws CommandException {
        String key = stringField("key");
		try {
			_display.popup(_receiver.communicationsFromClient(key));
		} catch (prr.exceptions.UnknownClientKeyException e) {
			throw new UnknownTerminalKeyException(e.getKey());
		}
	}
}
