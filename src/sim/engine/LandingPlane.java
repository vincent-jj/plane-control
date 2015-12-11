package sim.engine;

import sim.util.Heading;
import sim.util.Position;
import sim.util.UniversalPosition;
import sim.util.PositionUnits;
import sim.engine.commandReturn.*;

enum LandingPlaneState{
    FLYING,
    APROCHING,
    LANDING,
    PARKED,
}

public class LandingPlane extends Plane {

    private Runway runway;//the runway to land
    private LandingPlaneState state;
    static final int MAX_ALTITUDE = 3000;//the maximum allowed altitude to start a landing
    static final int APROCHING_ALT = 400;//the plane try to be below this altitude before landing.
    static final int APROCHING_DIST = 1000;//in MILI_MILES; when dist(plane,runway < APROCHING_DIST, the plane pass in LANDINg state
    static final int APROCHING_SPEED = 90;

    /**
     * Creates a Plane that cannot take-off, it can only land.
     * @param id defines the identifier of the Plane.
     * @param alt the initial altitude of the plane (initialy, the plane stay at the same altitude)
     * @param speed the initial speed of the plane (initialy, the plane keeps the same speed)
     * @param angle the initial heading of the plane (initialyy the plane keep the same heading)
     * @param p the initial position of the plane
     */
    public LandingPlane(String id, int alt, int speed, Heading angle, UniversalPosition p) {
        super(id,alt,speed,angle,p);
        state = LandingPlaneState.FLYING;
    }

    /**
     * say to the plane to land. The plane may not execute the order if it is too far, too high, ...
     * @param r the runway wher the plane is supposed to land.
     * @return The pilot response
     */
    public PlaneCommandReturn land(Runway r){
        if (altitude > MAX_ALTITUDE){
            return new LandCommandReturn(LandError.TOO_HIGH,r);
        }
        if (! ang.isNear(r.getHeading(), new Heading(30))){
            return new LandCommandReturn(LandError.PLANE_NOT_ALIGNED,r);
        }
        Position runwayPos = r.getPosition().getPosition(PositionUnits.MILI_MILE);
        Heading runwayDirection = new Heading(pos, runwayPos);
        if (! r.getHeading().isNear(runwayDirection, new Heading(30))){
            return new LandCommandReturn(LandError.RUNWAY_NOT_ALIGNED,r);
        }
        runway = r;
        wantedAltitude = Math.min(APROCHING_ALT, altitude);
        wantedSpeed = 14;//TODO choose units and adjust
        setHeading(runwayDirection);
        state = LandingPlaneState.APROCHING;
        return new LandCommandReturn(r);
    }

    /**
     * a way to know the states of the plane
     * @return a boolean defining if the plane has landed.
     */
    public boolean isLanded() {
        return state == LandingPlaneState.PARKED;
    }

    /**
     * move the plane and actualise it's state.
     */
    public void doStep() {
        //System.out.println("state:"+state);

        Position runwayPos;
        switch (state){
            case FLYING:
                super.doStep();
                break;
            case APROCHING:
                runwayPos = runway.getPosition().getPosition(PositionUnits.MILI_MILE);
                if (pos.getDistance(runwayPos) < APROCHING_DIST){
                    if (altitude > APROCHING_ALT){
                        System.out.println("L'avion est trop haut pour atterir");//TODO se mettre d'accord pour les notifications
                        state = LandingPlaneState.FLYING;
                        wantedSpeed = 60;
                    } else if (speed > APROCHING_SPEED){
                        System.out.println("L'avion va trop vite pour atterir");//TODO se mettre d'accord pour les notifications
                        state = LandingPlaneState.FLYING;
                        wantedSpeed = 60;
                    }else {
                        state = LandingPlaneState.LANDING;
                        wantedAltitude = 0;
                    }
                }
                super.doStep();
                break;
            case LANDING:
                runwayPos = runway.getPosition().getPosition(PositionUnits.MILI_MILE);
                if (pos.getDistance(runwayPos) < 250){//Erk ! une constante inconue...
                    wantedSpeed = 0;
                    wantedAng = runway.getHeading();
                }
                if (speed == 0){
                    state = LandingPlaneState.PARKED;
                }
                super.doStep();
                break;
            case PARKED:
                //Do nothing, the plane will be removed later
        }
    }

    protected String stripGlobalDirection(){
        if (state == LandingPlaneState.FLYING){
            return "arrival";
        }
        return runway.getId();
    }

    protected String stripFlyDirection(){
        if (state != LandingPlaneState.FLYING){
            return runway.getId();
        }
        return super.stripFlyDirection();
    }

}
