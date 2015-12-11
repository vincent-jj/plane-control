package sim.engine;

import java.util.ArrayList;
import java.util.Iterator;

import sim.util.Functions;
import sim.util.Position;
import sim.util.UniversalPosition;

public class MapObjects {

	private ArrayList<Runway> runways = new ArrayList<Runway>();
	private ArrayList<Station> stations = new ArrayList<Station>();
	private int height;//in MILI_MILES
	private int width;//in MILI_MILES
	
	/**
	 * @param h the map height in MILI_MILES
	 * @param w the map width in MILI_MILES
	 */
	public MapObjects(int h, int w) {
		this.height=h;
		this.width=w;
		UniversalPosition.setMapSize(h,w);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	/**
	 * Allow to add a station to the map.
	 * @param station defines the station to add.
	 */
	public void addStation(Station station) {
		stations.add(station);
	}
	
	/**
	 * Allow to add a runway to the map.
	 * @param runway defines the runway to add.
	 */
	public void addRunway(Runway runway) {
		runways.add(runway);
	}
	
	/**
	 * search non-case sensitive in the stations
	 * @param name the name of the wanted station
	 * @return the station which match the name from the map or null if no object is found.
	 */
        public Station getStation(String name) {
            name = name.toUpperCase();
            Station element;
            Iterator<Station> it = stations.iterator();
            while(it.hasNext()) {
                element = it.next();
                if(element.getId().toUpperCase().equals(name)) {
                    return element;
                }
            }
            return null;
        }

	/**
	 * 
	 * @return a random station from the map.
	 */
        public Station getStation() {
            return stations.get(Functions.randInt(0, stations.size()-1));
        }
	
	/**
	 * 
	 * @return a random runway from the map.
	 */
        public Runway getRunway() {
            return runways.get(Functions.randInt(0, runways.size()-1));
        }

	/**
	 * @param name the name of wanted runway
	 * @return a runway which match name (non case sensitive)
	 */
	public Runway getRunway(String name) {
            name = name.toUpperCase();
            Runway element;
            Iterator<Runway> it = runways.iterator();
            while(it.hasNext()) {
                element = it.next();
                if(element.getId().toUpperCase().equals(name)) {
                    return element;
                }
            }
            return null;
	}
	
	protected Position getSide() {
		int side = Functions.randInt(1, 4);
		switch(side) {
		case 1:
			return new Position(-width/2, Functions.randInt(-height/4, height/4));
		case 2:
			return new Position(Functions.randInt(-width/4, width-width/4), -height/2);
		case 3:
			return new Position(width/2, Functions.randInt(-height/4, height/4));
		case 4:
			return new Position(Functions.randInt(-width/4, width/4), height/2);
		}
		return null;
		
	}
	
	/**
	 * Specifies if the plane is out of the map or not
	 * @param p the position of the plane
	 * @return true is the plane is out of the map, false if not
	 */
	protected boolean isOut(Position p) {
		if(p.getX()<(-width/2) || p.getX()>(width/2) || p.getY()<(-height/2) || p.getY()>(height/2) ) return true;
		return false;
	}
	/**
	 * Returns the list of elements (station or runway) of the map.
	 */
	public String toString() {
		String response = "Runways:\n" ;
		Iterator<Runway> it = runways.iterator();
		while(it.hasNext()) {
			Runway element = it.next();
			response = response + element.getId() + "\n";
		}
		Iterator<Station> it2 = stations.iterator();
		response = response + "Stations:\n";
		while(it2.hasNext()) {
			Station element2 = it2.next();
			response = response + element2.getId() + "\n";
		}
		return response;
	}
	
}
