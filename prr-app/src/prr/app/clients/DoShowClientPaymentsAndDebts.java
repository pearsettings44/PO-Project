package prr.app.clients;

import prr.Network;
import prr.app.exceptions.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

	DoShowClientPaymentsAndDebts(Network receiver) {
		super(Label.SHOW_CLIENT_BALANCE, receiver);
		addStringField("key", Prompt.key());
	}

	@Override
	protected final void execute() throws CommandException {
		String key = stringField("key");
		try {
			_display.popup(Message.clientPaymentsAndDebts(_receiver.getClient(key).getKey(),
					(long) _receiver.getClient(key).getPayments(),
					(long) _receiver.getClient(key).getDebts()));
		} catch (prr.exceptions.UnknownClientKeyException e) {
			throw new UnknownClientKeyException(key);
		}
	}
}
