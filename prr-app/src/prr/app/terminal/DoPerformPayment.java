package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

	DoPerformPayment(Network context, Terminal terminal) {
		super(Label.PERFORM_PAYMENT, context, terminal);
		addIntegerField("id", Prompt.commKey());
	}

	@Override
	protected final void execute() throws CommandException {
        Integer id = integerField("id");
		try {
			_receiver.performPayment(id);
		} catch (prr.exceptions.InvalidCommunicationException e) {
			_display.popup(Message.invalidCommunication());
		}
	}
}
