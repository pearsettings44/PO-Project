package prr.terminals;

public class OffTerminal extends Terminal.State {
    private static final long serialVersionUID = 202210151237L;

    public OffTerminal(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getStateName() {
        return "OFF";
    }
}
