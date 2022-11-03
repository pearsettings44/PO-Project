package prr.Notifications;

import prr.terminals.Terminal;

public class O2INotification extends Notification {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202211031603L;

    public O2INotification(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String toString() {
        return "O2I|" + super.toString();
    }
}