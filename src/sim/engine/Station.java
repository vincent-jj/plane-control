package sim.engine;

import java.lang.String;
import sim.util.*;
/**
 * Class implementing a VOR station
 */
public class Station {
	private String id;
	private Position position;
	private boolean electable;
	
	public Station(String id, UniversalPosition p) {
		this.id = id;
		this.position = p.getPosition(PositionUnits.MILI_MILE);
	}

	public String getId(){
		return this.id;
	}
	public Station(String id, UniversalPosition p, boolean status) {
		this.id = id;
		this.position = p.getPosition(PositionUnits.MILI_MILE);
		electable = status;
	}
	
	public UniversalPosition getPosition() {
		return new UniversalPosition(position,PositionUnits.MILI_MILE);
	}
	
	protected boolean isElectable() {
		return this.electable;
	}	
	
	public String toString(){
            return this.id;
        }
}
 
