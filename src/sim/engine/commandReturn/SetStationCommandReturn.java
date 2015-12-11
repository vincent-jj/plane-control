package sim.engine.commandReturn;

import sim.engine.Station;

/**
 * A class to represent the Plane response to a clearance command for station
 **/
public class SetStationCommandReturn extends PlaneCommandReturn{
    Station station;

    public SetStationCommandReturn(Station s){
        super(false);
        station = s;
    }

    protected String toFrench(){
        return "se dirgie vers "+station.toString();
    }

}
