package prr.communications;

import prr.TarrifPlans.TarrifPlan;
import prr.terminals.Terminal;

public class InteractiveCommunication extends Communication{
    private String _type;
    private Double _duration;

    public InteractiveCommunication(int id, Terminal sender, Terminal receiver, String type){
        super(id, sender, receiver);
        _type = type;
    }

    public String getType() {
        return _type;
    }

    public void setDuration(Double duration) {
        _duration = duration;
    }

    public double calculatePrice() {
        TarrifPlan tarrifPlan = getSender().getClient().getTarrifPlan();
        return tarrifPlan.getCommunicationPrice(this);
    }

    
    @Override
    public String toString() {
        if (isFinished())
            return String.format("%s|%d|%s|%s|%d|%d|%s", _type, getId(), getSender().getKey(),
                    getReceiver().getKey(), Math.round(getUnits()), Math.round(getPrice()), "FINISHED");
        else
            return String.format("%s|%s|%s|%s|%d|%d|%s", _type, getId(), getSender().getKey(),
                    getReceiver().getKey(), 0, 0, "ONGOING");
    }

}
