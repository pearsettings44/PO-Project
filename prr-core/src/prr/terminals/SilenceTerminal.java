package prr.terminals;

public class SilenceTerminal extends Terminal.State {
    private static final long serialVersionUID = 202210151236L;

    public SilenceTerminal(Terminal terminal) {
        terminal.super();
    }

    @Override
    public String getState() {
        return "SILENCE";
    }
}
