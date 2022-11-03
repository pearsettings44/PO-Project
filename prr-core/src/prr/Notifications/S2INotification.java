package prr.Notifications;

import prr.terminals.Terminal;

public class S2INotification extends Notification {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202211031606L;

    public S2INotification(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String toString() {
        return "S2I|" + super.toString();
    }
}