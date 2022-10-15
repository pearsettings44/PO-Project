package prr.terminals;

public class BusyTerminal extends Terminal.State {
    private static final long serialVersionUID = 202210151202L;

    public BusyTerminal(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getState() {
        return "BUSY";
    }
}
