package prr.Notifications;

import java.io.Serializable;

import prr.terminals.Terminal;

public abstract class Notification implements Serializable {
    /** Serial number for serialization. */
    private static final long serialVersionUID = 202211031554L;

    private Terminal _terminal;

    public Notification(Terminal terminal) {
        this._terminal = terminal;
    }

    public Terminal terminal() {
        return this._terminal;
    }

    @Override
    public String toString() {
        return this._terminal.getKey();
    }
}