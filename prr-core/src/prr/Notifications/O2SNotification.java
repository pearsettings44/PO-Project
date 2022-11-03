package prr.Notifications;

import prr.terminals.Terminal;

public class O2SNotification extends Notification {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202211031556L;


    public O2SNotification(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String toString() {
        return "O2S|" + super.toString();
    }
}