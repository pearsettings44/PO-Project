package prr.communications;

import prr.terminals.Terminal;

public class InteractiveCommunication extends Communication{
    private String _type;
    private Double _duration;

    public InteractiveCommunication(int id, Terminal sender, Terminal receiver, String type){
        super(id, sender, receiver);
        _type = type;
    }
    
    @Override
    public String toString() {
        if (isFinished())
            return String.format("%s|%s|%s|%s|units|price|status", _type, getId(), getSender().getKey(),
                    getReceiver().getKey(), getUnits(), getPrice(), "FINISHED");
        else
            return String.format("%s|%s|%s|%s|%d|%d|status", _type, getId(), getSender().getKey(),
                    getReceiver().getKey(), 0, 0, "ONGOING");
    }
}
