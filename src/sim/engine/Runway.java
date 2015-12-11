package sim.engine;

import java.lang.String;
import sim.util.*;

public class Runway {
	private String id;
	private Position pos;
	private Heading ang;
	private int length; //in mili_miles
	private boolean free = true;
	private boolean open = true;
	
	public Runway(String id, UniversalPosition p, Heading a, int l){
	    this(id, p, a, l, true);
        }
	public Runway(String id, UniversalPosition p, Heading a, int l,boolean status){
		this.id = id;
		this.pos = p.getPosition(PositionUnits.MILI_MILE);
		this.ang = a;
		this.length = l;
		this.open = status;
	}

	public String getId(){
		return this.id;
	}
	
	/**
	 * 
	 * @return true if there is no plane on the runway, false if not.
	 */
	protected boolean isFree() {
		return this.free;
	}
	
	/**
	 * set the runway as not free.
	 */
	public void hasPlane() {
		this.free = false;
	}
	
	/**
	 *  set the runway as free.
	 */
	public void planeLeft() {
		this.free = true;
	}
	
	/**
	 * 
	 * @return true if the runway is open, false if not.
	 */
	public boolean isOpen() {
		return this.open;
	}
	
	/**
	 * set the runway as closed.
	 */
	public void close() {
		this.open = false;
	}
	
	/**
	 * set the runway as opened.
	 */
	public void open() {
		this.open = true;
	}
	
	/**
	 * 
	 * @return the position of the runway.
	 */
	public UniversalPosition getPosition() {
		return new UniversalPosition(pos,PositionUnits.MILI_MILE);
	}
	
	/**
	 * 
	 * @return the ID of the runway.
	 */
	
	/**
	 * 
	 * @return the angle of the runway.
	 */
	public Heading getHeading() {
		return this.ang;
	}
	

    public int getInverseHeading(){
        int inv;
        if((this.ang.toInt()<180)&&(this.ang.toInt()>0))
            inv=this.ang.toInt()+180;
        else
            inv=this.ang.toInt()-180;
        return inv;
}

	/**
	 * 
	 * @return the length of the runway in mili_miles.
	 */
	public int getLength() {
		return this.length;

	}	


	
}
