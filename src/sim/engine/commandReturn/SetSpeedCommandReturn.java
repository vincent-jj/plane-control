package sim.engine.commandReturn;

/**
 * A class to represent the Plane response to a speed command
 **/
public class SetSpeedCommandReturn extends PlaneCommandReturn{
    int speed;

    public SetSpeedCommandReturn(int speed){
        super(false);
        this.speed = speed;
    }

    protected String toFrench(){
        return "atteint la vitesse " + speed + "noeuds";
    }

}
