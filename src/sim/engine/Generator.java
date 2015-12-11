package sim.engine;



import java.util.ArrayList;

import sim.util.*;
import sim.gui.GUI;

/**
 * a class to generate randomly planes
 */
public class Generator {

    /**
     * The probability of a plane to be generated
     */
    private int Probability;
    private MapObjects mapObjects;
    private GUI gui;
    private Engine engine;
    private int speed = 1;

    public Generator(int p, MapObjects m, GUI gui, Engine e) {
        this.Probability = p;
        this.mapObjects = m;
        this.gui = gui;
        engine = e;
    }

    /**
     * Has a chance to generate a plane with a random ID that either wants to take-off or appears at a random station.
     * Here the probability for a plane to be generated is 1/Probability.
     */
    protected void run() {
        int max = 10000;
        int i = Functions.randInt(0,max);
        int rand = 1;
        rand = rand*Functions.randInt(1,Probability);	
        if ( rand == 1 ) {
            createPlane(i > 5*max/8);
        }
    }

    /**
     * create and initialize a plane
     * @param takingOff choose if createPlane create an TakingOff or a Landing Plane
     */
    public void createPlane(boolean takingOff) {
        String id = "FR" + Functions.randInt(0,1000);
        if (takingOff){
            TakingOffPlane p = new TakingOffPlane(id,this.mapObjects.getStation(),this.mapObjects.getRunway());
            engine.getPlanes().add(p);
            gui.addPlane(p);
            System.out.println("L'avion " + id + " a été créé. L'avion veut décoller.");
        }else{
            LandingPlane p = new LandingPlane(id, 
                    Functions.randInt(2000, 10000), 
                    Functions.randInt(100, 135), 
                    new Heading(Functions.randInt(0, 360)), new UniversalPosition(mapObjects.getSide(),PositionUnits.MILI_MILE));
            if (p.pos.getX() == mapObjects.getWidth()/2) {
                p.ang = new Heading(270);
                p.setHeading(p.ang);
            }
            else if(p.pos.getX() == -mapObjects.getWidth()/2) {
                p.ang = new Heading(90);
                p.setHeading(p.ang);
            }
            else if(p.pos.getY() == mapObjects.getHeight()/2) {
                p.ang = new Heading(0);
                p.setHeading(p.ang);
            }
            else {
                p.ang = new Heading(180);
                p.setHeading(p.ang);
            }
            engine.getPlanes().add(p);
            gui.addPlane(p);
            System.out.println("L'avion " + id + " a été créé. L'avion veut atterrir.");
        }
    }

	public void ChangeSpeed(int acceleration) {
		engine.changeSpeed(acceleration);		
	}

}
