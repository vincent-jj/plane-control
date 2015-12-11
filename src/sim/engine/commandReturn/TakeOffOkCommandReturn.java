package sim.engine.commandReturn;

/**
 * A class to represent the Plane response to a TakeOff command
 **/
public class TakeOffOkCommandReturn extends TakeOffCommandReturn{
    public TakeOffOkCommandReturn(){
        super(false);
    }
    protected String toFrench(){
        return "decolle";
    }
}
