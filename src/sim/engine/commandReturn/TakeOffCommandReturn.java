package sim.engine.commandReturn;

/**
 * A class to represent the Plane response to a TakeOff command
 **/
abstract class TakeOffCommandReturn extends PlaneCommandReturn{
    public TakeOffCommandReturn(boolean isError){
        super(isError);
    }
}
