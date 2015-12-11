package sim.engine.commandReturn;

/**
 * A class to represent the Plane response to a clearance command for altitude
 **/
public class SetAltitudeCommandReturn extends PlaneCommandReturn{
    int altitude;

    public SetAltitudeCommandReturn(int altitude){
        super(false);
        this.altitude = altitude;
    }

    protected String toFrench(){
        return "monte/descend à " + altitude + "pieds";
    }

}
