package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

	DoShowClient(Network receiver) {
		super(Label.SHOW_CLIENT, receiver);
		addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		String key = stringField("key");
		try {
			_display.popup(_receiver.getClient(key));
			_receiver.readClientInAppNotifications(stringField("key")).stream()
					.forEach(notification -> _display.popup(notification));
		} catch (prr.exceptions.UnknownClientKeyException e) {
			throw new UnknownClientKeyException(key);
		}
	}
}
