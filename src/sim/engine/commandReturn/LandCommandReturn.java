package sim.engine.commandReturn;

import sim.engine.Runway;
/**
 * A class to represent the Plane response to a Land command
 **/
public class LandCommandReturn extends PlaneCommandReturn{
    LandError error;
    private Runway runway;

    public LandCommandReturn(Runway r){
        super(false);
        runway = r;
    }
    public LandCommandReturn(LandError e, Runway r){
        super(true);
        error = e;
        runway = r;
    }

    protected String toFrench(){
        if (error != null){
            switch (error){
                case TOO_HIGH :
                    return "trop haut pour commencer atterissage";
                case PLANE_NOT_ALIGNED:
                    return "angle trop important par rapport à la piste " + runway.getId();
                case RUNWAY_NOT_ALIGNED:
                    return "l'avion n'est pas dans l'alignement de la piste " + runway.getId();
            }
        }
        return "ok pour atterissage sur " + runway.getId();
    }
}
