package sim.engine.commandReturn;

/**
 * A class to represent the Plane response to a TakeOff command
 **/
public class TakeOffNoAltitudeCommandReturn extends TakeOffCommandReturn{

    public TakeOffNoAltitudeCommandReturn(){
        super(true);
    }
    protected String toFrench(){
        return "ne peut pas décoller sans altitude prédefinie";
    }
}
