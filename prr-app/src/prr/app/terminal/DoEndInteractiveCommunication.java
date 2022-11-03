package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for ending communication.
 */
class DoEndInteractiveCommunication extends TerminalCommand {

	DoEndInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.END_INTERACTIVE_COMMUNICATION, context, terminal,
				receiver -> receiver.canEndCurrentCommunication());
		addRealField("duration", Prompt.duration());
	}

	@Override
	protected final void execute() throws CommandException {
		double duration = realField("duration");
		try {
			_network.endInteractiveCommunication(_receiver, duration);
			_display.popup(Message.communicationCost(_receiver.getLastInteractiveCommunicationPrice()));
		} catch (prr.exceptions.NoOngoingCommunicationException e) {
			_display.popup(Message.noOngoingCommunication());
		} catch (prr.exceptions.TerminalAlreadyOnException e) {
			_display.popup(Message.alreadyOn());
		} catch (prr.exceptions.TerminalAlreadyOffException e) {
			_display.popup(Message.alreadyOff());
		} catch (prr.exceptions.TerminalAlreadySilentException e) {
			_display.popup(Message.alreadySilent());
		} catch (prr.exceptions.TerminalAlreadyBusyException e) {
			e.printStackTrace();
		}

	}
}
