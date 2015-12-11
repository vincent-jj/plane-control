package sim.engine;

import sim.gui.GUI;
import sim.util.Updatable;
import java.util.ArrayList;
import java.util.Iterator;

import java.sql.Time;

/**
 *
 * @author clement
 */

/**
 * A class to comunicate with the engine module. This class is a singleton.
 * This is not yet finshed to code and a lot of code must be import from Generator
 */
public class Engine implements Updatable {
    private static final int CRASH_ALTITUDE = 1000;//the minimal altitude diff allowed between two planes (in feet)
    private static final int CRASH_DIST = 3000;//the minimal horizontal distance allowed between two planes (in mili_miles)

    private static Engine instance;

    private MapObjects mapObjects;
    private Generator generator;
    private int speed;//the speed of the game (1X, 2X, 5X)
    private ArrayList<Plane> planes = new ArrayList<Plane>();
    private int lostPlanes;//planes which goes out of the map
    private int tokenOffPlanes;//planes which token off and goes to their station
    private int landedPlanes;//planes which land correctly
    private long startTime;
    private long score = 0;
    private boolean crash = false;

    private Engine(int _TIME, MapObjects m) {
        generator = new Generator(48000/(_TIME * 1000), m, GUI.getGUI(), this);
        speed = 1;
        mapObjects = m;
        lostPlanes = 0;
        tokenOffPlanes = 0;
        landedPlanes = 0;
        startTime = System.currentTimeMillis();
    }

    public static void init(int _TIME, MapObjects m) {
        instance = new Engine(_TIME, m);
    }

    public static Engine getEngine() {
        return instance;
    }

    public void changeSpeed(int s) {
        speed = s;
    }
    
    protected void planeLanded() {
    	score = score + 10;
    }
    
    protected void stationReached() {
    	score = score + 10;
    }
    
    protected void planeLeft() {
    	score = score - 10;
    }
    
    protected void planeCrash() {
    	score = score - 100;
    }
    
    public long getScore() {
    	return score;
    }

    public boolean finish() {
    	return crash;
    }
    
    /**
     * parse a command.
     * @param command the command to parse
     * @see CommandParser
     */
    public void handleCommand(String command) {
        GUI.getGUI().commandSent(CommandParser.processCommand(command));
    }

    /**
     * do a Engine step.
     * this include moving plane and creating new planes
     */
    @Override
    public void update() {
        //for debug:
        //System.out.println("Time:"+getEllapsedTime().toString()+"\tLand:"+landedPlanes+"\tTokenOffPlanes:"+tokenOffPlanes+"\tlost:"+lostPlanes+"\n");
        
        for(int i=0; i<speed ; i++) {
            generator.run();
            Iterator<Plane> it = planes.iterator();
            while(it.hasNext()) {
                Plane element = it.next();
                element.doStep();
                if(element.getMiliPosition()!=null) {
                    //check collision
                    Iterator<Plane> other = planes.iterator();
                    while(other.hasNext()) {
                        Plane otherPlane = other.next();
                        if (element == otherPlane){
                            break;//it's not important if we do not check the end of the array, it will be check by the other plane
                        }
                        if(Math.abs(otherPlane.getAltitude()-element.getAltitude()) < CRASH_ALTITUDE){
                           if (otherPlane.getMiliPosition()!=null &&
			       otherPlane.getMiliPosition().getDistance(element.getMiliPosition()) < CRASH_DIST){
			       System.out.println("CRASH "+ otherPlane.getId()+ " " + element.getId());
                                   planeCrash();
                                   crash = true;
                                   }

                        }
                    }
                    //check going out of the map
                    if (mapObjects.isOut(element.getMiliPosition())) {
                        it.remove();
                        GUI.getGUI().removePlane(element);
                        planeLeft();
                        System.out.println("l'avion " + element.getId() + " sort");
                        if (element.isTakingOffPlane() &&
                                ((TakingOffPlane)element).hasReachedDestination()){
                            tokenOffPlanes++;
                        }else{
                            lostPlanes++;
                        }
                    }
                }
                if (!element.isTakingOffPlane() && 
                        ((LandingPlane)element).isLanded()){
                    it.remove();
                    GUI.getGUI().removePlane(element);
                    planeLanded();
                    landedPlanes++;
                }
                if(element.isTakingOffPlane() && ((TakingOffPlane) element).hasReachedDestination()) {
                	it.remove();
                	GUI.getGUI().removePlane(element);
                	stationReached();
                }
            }
        }
    }


    public int getLandedPlanes(){
        return landedPlanes;
    }

    public int getLostPlanes(){
        return lostPlanes;
    }

    public int getTokenOffPlanes(){
        return tokenOffPlanes;
    }

    public long getEllapsedMillis(){
        return System.currentTimeMillis() - startTime;
    }

    public String getEllapsedTime(){
      int second = (int)getEllapsedMillis()/1000;
      int minutes = second/60;
      second -= minutes*60;
      int hours = minutes/60;
      minutes-= hours*60;
      StringBuffer buf= new StringBuffer();
      buf.append(hours);
      buf.append(":");
      if (minutes < 10){
          buf.append("0");
      }
      buf.append(minutes);
      buf.append(":");
      if (second < 10){
          buf.append("0");
      }
      buf.append(second);
        return buf.toString();
    }


    public void createTakingOffPlane() {
        generator.createPlane(true);
    }

    protected MapObjects getMapObjects(){
        return mapObjects;
    }

    protected ArrayList<Plane> getPlanes(){
        return planes;
    }

    /**
     * remove a specific plane from the game.
     * @param id defines the plane
     */
    protected void removePlane(String id) {

        Iterator<Plane> it = planes.iterator();
        while(it.hasNext()) {
            Plane element = it.next();
            if(element.id.equals(id)) {
                it.remove();
                GUI.getGUI().removePlane(element);
                System.out.println("L'avion FR" + id + " est bien arrivé à  destination !");
            }
        }

    }

    /**
     * search a specific plane.
     * @param id the name of the wanted plane
     * @return the plane or null if no plane is found
     */
    public Plane getPlane(String id) {
        Plane element;
        Iterator<Plane> it = planes.iterator();
        while(it.hasNext()) {
            element = it.next();
            if(element.id.equals(id)) {
                return element;
            }
        }
        System.out.println("l'avion d'identifiant " + id + " n'a pas été trouvé.");
        return null;
    }

    /**
     * 
     * @return the number of planes currently in game.
     */
    public int planeNumber() {
        return planes.size();
    }
}
