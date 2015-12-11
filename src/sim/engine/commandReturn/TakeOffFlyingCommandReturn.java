package sim.engine.commandReturn;

/**
 * A class to represent the Plane response to a TakeOff command
 **/
public class TakeOffFlyingCommandReturn extends TakeOffCommandReturn{

    public TakeOffFlyingCommandReturn(){
        super(true);
    }
    protected String toFrench(){
        return "a déjà décollé";
    }
}
