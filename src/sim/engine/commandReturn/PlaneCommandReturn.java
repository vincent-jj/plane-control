package sim.engine.commandReturn;

/**
 * A class to represent the Plane response to a command
 * This class must be used for language traduction and/or vocal synthesis
 **/
public abstract class PlaneCommandReturn{
    protected boolean isError;

    /**
     * to know if the command was accepted
     * @return true if the plane do not considerate the order
     **/
    public boolean isError(){
    return isError;
    }

    /**
     * A simple constructor
     * @param isError indicate if the plane has executed the order or not
     **/
    protected PlaneCommandReturn(boolean isError){
    this.isError = isError;
    }

    /**
     * get the pilot response in French
     * @return only the pilot statement. Does not include the plane name
     **/
    protected abstract String toFrench();

    /**
     * a way to get a String of the response.
     * TODO let user choose the language
     **/
    public String toString(){
        return toFrench();
    }

}
