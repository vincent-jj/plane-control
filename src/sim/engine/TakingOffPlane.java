package sim.engine;

import java.lang.RuntimeException;

import sim.engine.commandReturn.*;

import sim.util.PositionUnits;

enum TakingOffPlaneState{
    PARKED,
    LINING,
    WAITING,
    TAKINGOFF,//can be aborted
    FLYING,//too late for abort
}

public class TakingOffPlane extends Plane {

    private int TakingOffAccelartion = 5;//en mili_miles/2sec/2sec ~ 0-250km/h en 30s
    private Station destination;
    private Runway runway;
    private int stepsSinceLineUp;
    private TakingOffPlaneState state;
    private boolean takeOffAsked;
    private boolean destinationReached;
    private static final int stepsToLineUp = 10; //en 2sec ~ 20 secondes arbitraire
    private static final int MIN_TAKEOFFF_SPEED = 80;//en mili_miles/2sec ~250km/h
    private static final int MAX_SPEEED_BEFORE_ABORT = 45;//en mili_miles/2sec

    /**
     * Creates a plane that can only take off and has to go to a specific station to leave the map. It cannot land.
     * @param id defines the identifier of the Plane.
     * @param dest defines where the Plane has to go to exit the Map.
     * @param r the runway wher the plan has to take off
     */
    public TakingOffPlane(String id, Station dest, Runway r) {
        super(id,0,0,r.getHeading().clone());
        this.destination = dest;
        this.runway = r;
        this.state = TakingOffPlaneState.PARKED;
        this.takeOffAsked = false;
        destinationReached = false;
    }

    public boolean isParked() {
        return this.state == TakingOffPlaneState.PARKED ||
            this.state == TakingOffPlaneState.LINING;
    }

    /**
     * 
     * @return the destination that the plane has to reach to leave the map.
     */
    public Station getDestination() {
        return this.destination;
    }

    /**
     * say to the plane he is allowed to take off
     * @return The pilot response
     */
    public PlaneCommandReturn takeOff() {
        if(state != TakingOffPlaneState.PARKED &&
                state != TakingOffPlaneState.LINING &&
                state != TakingOffPlaneState.WAITING){
            return new TakeOffFlyingCommandReturn();
                }
        if (this.wantedAltitude == 0){
            return new TakeOffNoAltitudeCommandReturn();
        }
        this.takeOffAsked = true;
        if (state == TakingOffPlaneState.PARKED){
            lineUp();
        }
        return new TakeOffOkCommandReturn();
    }

    public void doStep() {
        switch (state){
            case PARKED :
                break;
            case LINING :
                stepsSinceLineUp ++;
                if (stepsSinceLineUp >= stepsToLineUp && runway.isFree()){
                    state = TakingOffPlaneState.WAITING;
                    runway.hasPlane();
                    super.pos = runway.getPosition().getPosition(PositionUnits.MILI_MILE);
                }
                break;
            case WAITING :
                if (takeOffAsked){
                    state = TakingOffPlaneState.TAKINGOFF;
                    setHeading(runway.getHeading());
                    runway.planeLeft();
                }
                break;
            case TAKINGOFF :
                setHeading(runway.getHeading());
                this.speed = this.speed + this.TakingOffAccelartion;
                this.wantedSpeed=this.speed;
                super.doStep();
                if (speed < MIN_TAKEOFFF_SPEED){
                    altitude=0;
                }
                if (speed >= MAX_SPEEED_BEFORE_ABORT){
                    state = TakingOffPlaneState.FLYING;
                    this.wantedSpeed = 304; //en mili_mile/2sec  correspond à 900kms/h soit 500 miles/h environ
                }
                break;
            case FLYING :
                super.doStep();
                if (!destinationReached){
                    if (pos.getDistance(destination.getPosition().getPosition(PositionUnits.MILI_MILE)) < 1000){
                        destinationReached = true;
                    }
                }
                break;
            default:
                throw new RuntimeException("State plane problem " + id + " is " + state + " .");
        }
    }

    public PlaneCommandReturn lineUp() {
        if(state != TakingOffPlaneState.PARKED){
        return new LiningCommandReturn(true);
        }
        stepsSinceLineUp = 0;
        state = TakingOffPlaneState.LINING;
        return new LiningCommandReturn(false);
    }

    protected String stripGlobalDirection(){
        return destination.toString();
    }

    public boolean isTakingOffPlane() {
        return true;
    }

    protected String stripFlyDirection(){
        if (state == TakingOffPlaneState.PARKED)
            return runway.getId();
        if (state == TakingOffPlaneState.LINING)
            return "lining";
        if (state == TakingOffPlaneState.WAITING)
            return "ready for takeoff";
        return super.stripFlyDirection();
    }

    public boolean mustBeDisplayed(){
        return state != TakingOffPlaneState.PARKED && state != TakingOffPlaneState.LINING;
    }

    public boolean hasReachedDestination(){
        return destinationReached;
    }
}

