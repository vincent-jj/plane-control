package sim.engine;

public class Airport {
    private String id;
    private double latitude;
    private double longitude;

    public Airport(String id) {
	this.id = id;
    }

    public Airport(String id, double lat, double longi) {
	this.id = id;
	latitude = lat;
	longitude = longi;
    }

    public String getID() {
	return id;
    }

    public double getLatitude() {
	return latitude;
    }
    public double getLongitude() {
	return longitude;
    }

}
