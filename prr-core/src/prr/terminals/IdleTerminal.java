package prr.terminals;

public class IdleTerminal extends Terminal.State {
    private static final long serialVersionUID = 202210151203L;

    public IdleTerminal(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getState() {
        return "IDLE";
    }
}
