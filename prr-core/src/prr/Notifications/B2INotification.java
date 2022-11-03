package prr.Notifications;

import prr.terminals.Terminal;

public class B2INotification extends Notification {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202211031604L;


    public B2INotification(Terminal terminal) {
        super(terminal);
    }

    @Override
    public String toString() {
        return "B2I|" + super.toString();
    }
}
