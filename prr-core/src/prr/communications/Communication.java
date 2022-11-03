package prr.communications;

import java.io.Serializable;

import prr.terminals.Terminal;

abstract public class Communication implements Serializable{
    private static final long serialVersionUID = 202211021616L;

    private int _id;
    private Terminal _sender;
    private Terminal _receiver;
    private boolean _isFinished;
    private boolean _isPaid;
    private double _price;
    private double _units;

    public Communication(int id, Terminal sender, Terminal receiver){
        _id = id;
        _sender = sender;
        _receiver = receiver;
        _isFinished = false;
        _isPaid = false;
    }

    public void setFinished(){
        _isFinished = true;
    }

    public boolean isFinished(){
        return _isFinished;
    }

    public void setPaid(){
        _isPaid = true;
    }

    public boolean isPaid(){
        return _isPaid;
    }

    public int getId(){
        return _id;
    }

    public Terminal getSender(){
        return _sender;
    }

    public Terminal getReceiver(){
        return _receiver;
    }

    public double getPrice(){
        return _price;
    }
    
    public double getUnits(){
        return _units;
    }

    public void setUnits(double units){
        _units = units;
    }

    public void setPrice(double price) {
        _price = price;
    }

    public abstract String getType();
}
