package sim.engine.commandReturn;

import sim.util.Heading;

/**
 * A class to represent the Plane response to a clearance command for heading
 **/
public class SetHeadingCommandReturn extends PlaneCommandReturn{
    private Heading heading;

    public SetHeadingCommandReturn(Heading h){
        super(false);
        heading = h;
    }

    protected String toFrench(){
        return "tourne jusqu'à "+heading.toString();
    }

}
