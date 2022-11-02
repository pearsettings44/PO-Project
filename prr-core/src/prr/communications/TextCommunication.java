package prr.communications;

import prr.terminals.Terminal;

public class TextCommunication extends Communication{
    private String _text;
    private String _type = "TEXT";

    public TextCommunication(int id, Terminal sender, Terminal receiver, String text){
        super(id, sender, receiver);
        _text = text;
        setFinished();
    }

    @Override
    public String toString() {
        if (isFinished())
            return String.format("%s|%s|%s|%s|%d|%d|%s", _type, getId(), getSender().getKey(),
                    getReceiver().getKey(), (int)getUnits(), Math.round(getPrice()), "FINISHED");
        else
            return String.format("%s|%s|%s|%s|%d|%d|%s", _type, getId(), getSender().getKey(),
                    getReceiver().getKey(), 0, 0, "ONGOING");
    }
}
