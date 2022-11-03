package prr.app.terminal;

import prr.Network;
import prr.app.exceptions.UnknownTerminalKeyException;
import prr.terminals.Terminal;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

	DoStartInteractiveCommunication(Network context, Terminal terminal) {
		super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
		addStringField("key", Prompt.terminalKey());
		addOptionField("type", Prompt.commType(), "VOICE", "VIDEO");
	}

	@Override
	protected final void execute() throws CommandException {
        String key = stringField("key");
		String type = stringField("type");
		try {
			_network.startInteractiveCommunication(_receiver, key, type);
		} catch (prr.exceptions.UnknownTerminalKeyException e) {
			throw new UnknownTerminalKeyException(e.getKey());
		} catch (prr.exceptions.UnsupportedAtOriginException e) {
			_display.popup(Message.unsupportedAtOrigin(_receiver.getKey(), type));
		} catch (prr.exceptions.UnsupportedAtDestinationException e) {
			_display.popup(Message.unsupportedAtDestination(key, type));
		} catch (prr.exceptions.DestinationIsOffException e) {
			_display.popup(Message.destinationIsOff(key));
		} catch (prr.exceptions.DestinationIsSilentException e) {
			_display.popup(Message.destinationIsSilent(key));
		} catch (prr.exceptions.DestinationIsBusyException e) {
			_display.popup(Message.destinationIsBusy(key));
		} catch (prr.exceptions.TerminalAlreadyBusyException e) {
			e.printStackTrace();
		}
	}
}
