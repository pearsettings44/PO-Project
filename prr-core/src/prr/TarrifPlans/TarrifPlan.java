package prr.TarrifPlans;

import java.io.Serializable;

import prr.communications.Communication;

abstract public class TarrifPlan implements Serializable{
    
    private static final long serialVersionUID = 202211021617L;

    abstract public double getCommunicationPrice(Communication communication);
}
