package sim.engine;

import sim.util.*;
import sim.engine.commandReturn.*;

enum LeadingSystem{
    HEADING,
    POSITION,
}

/**
 * Class implementing a plane
 */
public abstract class Plane {

    private static final int altitudeDif = 94;
    private static final int speedDif = 2;//en mili_miles/2sec/2sec plus ou moins arbitraire
    private static final int turnDif = 6;//en °/2sec ~ correspond au taux de virage 1
    protected String id;
    protected Position pos;//in mili_miles
    private Position wantedPos;
    protected Heading ang;
    protected Heading wantedAng;
    protected LeadingSystem leadingSystem;
    protected int speed;
    protected int wantedSpeed;
    protected int altitude; //in feet
    protected int wantedAltitude;
    protected Station wantedStation;
    private String model;//A380 is good

    /**
     * initialize a Plane which is not on the map
     * Note that position is not initialized
     * @param id the name of the plane
     * @param alt the initial altitude of the plane (initialy, the plane stay at the same altitude)
     * @param speed the initial speed of the plane (initialy, the plane keeps the same speed)
     * @param angle the initial heading of the plane (initialyy the plane keep the same heading)
     */
    public Plane(String id, int alt, int speed, Heading angle) {
        this.id = id;
        this.altitude = alt;
        this.wantedAltitude = alt;
        this.speed = speed;
        this.wantedSpeed = speed;
        setHeading(angle);//this initialize also the LeadingSystem
        this.ang = angle;
        model = "A380";
    }

    /**
     * initialize a plane with a fixed Heading.
     * @param id the name of the plane
     * @param alt the initial altitude of the plane (initialy, the plane stay at the same altitude)
     * @param speed the initial speed of the plane (initialy, the plane keeps the same speed)
     * @param angle the initial heading of the plane (initialyy the plane keep the same heading)
     * @param p the initial position of the plane
     */
    public Plane(String id, int alt, int speed, Heading angle, UniversalPosition p) {
        this(id, alt, speed, angle);
        this.pos = p.getPosition(PositionUnits.MILI_MILE);
    }

    /**
     * move the plane and actualise it's state.
     */
    protected void doStep() {
        //Modification de l'altitude
        if(this.altitude<(this.wantedAltitude-altitudeDif)) {
            this.altitude = this.altitude + altitudeDif;
        }
        else if(this.altitude>(this.wantedAltitude+altitudeDif)) {
            this.altitude = this.altitude - altitudeDif;
        }
        else {
            this.altitude=this.wantedAltitude;
        }

        //Modification de la vitesse
        if(this.speed<(this.wantedSpeed-speedDif)) {
            this.speed = this.speed + speedDif;
        }
        else if(this.speed>(this.wantedSpeed+speedDif)) {
            this.speed = this.speed - speedDif;
        }
        else {
            this.speed = this.wantedSpeed;
        }

        if (leadingSystem == LeadingSystem.POSITION){
            wantedAng = new Heading(pos, wantedPos);
        }
        //Modification de l'angle
        Heading angleDif = new Heading(turnDif);
        if(this.ang.isNear(this.wantedAng,angleDif)){
            this.ang  = this.wantedAng;
        }
        else {
            this.ang.add(angleDif, this.ang.nearestSide(this.wantedAng));
        }
        //Modification de la position
        this.pos.setX((int) (this.pos.getX() + this.speed*this.ang.cos()));
        this.pos.setY((int) (this.pos.getY() - this.speed*this.ang.sin()));
    }

    /**
     * say to the plane the altitude he is allowed to fly
     * @param altitude defines the altitude (in feet) wanted for the plane.
     * @return The pilot response
     */
    public PlaneCommandReturn setAltitude(int altitude){
        this.wantedAltitude=altitude;
        return new SetAltitudeCommandReturn(this.wantedAltitude);
    }

    /**
     * say to the plane where he has to fly
     * @param h defines the heading wanted for the plane.
     * @return The pilot response
     */
    public PlaneCommandReturn setHeading(Heading h){
        leadingSystem = LeadingSystem.HEADING;
        this.wantedAng = h.clone();
        return new SetHeadingCommandReturn(h);
    }

    /**
     * say to the plane where he has to fly
     * @param s defines the position wanted for the plane.
     * @return The pilot response
     */
    public PlaneCommandReturn setStation(Station s){
        wantedPos = s.getPosition().getPosition(PositionUnits.MILI_MILE);
        leadingSystem = LeadingSystem.POSITION;
        wantedStation = s;
        return new SetStationCommandReturn(s);
    }

    /**
     * say to the plane the speed he is supposed to fly
     * @param speed defines the speed wanted for the plane.
     * @return The pilot response
     */
    public PlaneCommandReturn setSpeed(int speed){
        this.wantedSpeed = speed;
        return new SetSpeedCommandReturn(speed);
    }

    /**
     * get the Heading of the plane
     * @return the current heading even if the plane is turning until another heading is reached.
     */
    public Heading getHeading(){
        return this.ang;
    }

    public int getCurrentSpeed(){
        return ((this.speed*36)/10);
    }

    /**
     * @return The altitude (in feet) which correspond to the wanted altitude
     */
    public int getAltitude(){
        return this.altitude;
    }

    /**
     * a way to know if the plane is rising or falling
     * @return UP,DOWN or STABLE if the plane is rising, falling or horizontal
     */
    public VerticalDirection getVerticalDirection(){
        if (altitude < wantedAltitude)
            return VerticalDirection.UP;
        if (altitude > wantedAltitude)
            return VerticalDirection.DOWN;
        return VerticalDirection.STABLE;
    }


    /**
     * must return the goal of the plane
     * @return a Station for TakingOffPlane and a Landway for arrivals
     */
    protected abstract String stripGlobalDirection();

    /**
     * must return the actual direction of the plane
     * @return a Station, a heading, a runway, ...
     */
    protected String stripFlyDirection(){
        if (leadingSystem == LeadingSystem.HEADING)
            return wantedAng.toString();
        return wantedStation.toString();
    }

    public String makeStrip() {
        return id + "\t" + this.stripFlyDirection() + "\t" + wantedAltitude + getVerticalDirection().toString() +
            "\n" + model + "\tTo:" + stripGlobalDirection() + "\n";
    }
    /**
     * get the actual position of the plane
     * Please note that this position may not be shared so call this functiun each time you need the position
     * @return the actual position of the plane
     * @see UniversalPosition
     */
    public UniversalPosition getPosition() {
        return new UniversalPosition(pos,PositionUnits.MILI_MILE);
    }

    protected Position getMiliPosition()  {
        return pos;
    }

    public String getId(){
        return this.id;
    }

    /**
     * @return true if the plane is a TakingOff, false if the plane is a LandingPlane
     */
    public boolean isTakingOffPlane() {
        return false;
    }

    public int getWantedAltitude() {
        return wantedAltitude;
    }

    /**
     * indicate if the plane must be displayed on the map
     * (Takingoff planes have no position when linig)
     * @return true if the plane muste be displayed
     */
    public boolean mustBeDisplayed(){
        return true;
    }
}
