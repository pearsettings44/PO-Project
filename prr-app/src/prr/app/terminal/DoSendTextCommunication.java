package prr.app.terminal;

import prr.Network;
import prr.terminals.Terminal;
import prr.app.exceptions.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

        DoSendTextCommunication(Network context, Terminal terminal) {
                super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
                addStringField("key", Prompt.terminalKey());
                addStringField("message", Prompt.textMessage());
        }

        @Override
        protected final void execute() throws CommandException {
                String key = stringField("key");
                String message = stringField("message");
                try {
                        _network.sendTextCommunication(_receiver, key, message);
                } catch (prr.exceptions.UnknownTerminalKeyException e) {
                        throw new UnknownTerminalKeyException(e.getKey());
                } catch (prr.exceptions.DestinationIsOffException e) {
                        _display.popup(Message.destinationIsOff(key));
                }
        }
} 
