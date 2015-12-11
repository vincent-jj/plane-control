package sim.engine.commandReturn;


/**
 * A class to represent the Plane response to a LineUp command
 **/
public class LiningCommandReturn extends PlaneCommandReturn{
    public LiningCommandReturn(boolean isError){
        super(isError);
    }

    protected String toFrench(){
        if (isError()){
            return "n'est pas garé";
        }
        return "se place sur la piste";
    }

}
