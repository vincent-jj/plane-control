package sim.util;

import java.io.BufferedReader;
import java.io.FileReader;

import sim.engine.Station;
import sim.engine.MapObjects;
import sim.engine.Runway;
import sim.engine.Airport;
import sim.gui.GUI;

/*
 * This class parses a map file to fill the Map object with all the stations and runways of the file
 */
public class Parser {
    /*
     * defines the height and the length of the map
     * 1 unit = 0.001 mile
     */

    private static int _height = 25000;
    private static int _width = 25000;
    public static MapObjects m = new MapObjects(_height, _width);

    // private static void parseRunway(Airport a) {
    // 	String s;
    // 	try {
    // 	    BufferedReader f = new BufferedReader(new FileReader(a.getID()+"_Runways"));
    // 	    while((s = f.readLine()) != null) {
    // 		String[] split = s.split(",");
    // 		Runway r = new Runway(split[0],new UniversalPosition(Float.parseFloat(split[3]),Float.parseFloat(split[4])),new Heading(Integer.parseInt(split[1])),Integer.parseInt(split[2]),true);
    // 		m.addRunway(r);
    // 		map.addRunway(new RunwayDraw(r));
    // 	    }
    // 	} catch (IOException e) {
    // 	    System.out.println(e.getMessage());
    // 	}
    // }
    // /*
    //  * Parses a file of station associated to 1 airport
    //  */ 
    // public static void parseStation(Airport a) {
    // 	String s;
    // 	try {
    // 	    BufferedReader f = new BufferedReader(new FileReader(a.getID()+"_Stations"));
    // 	    while((s = f.readLine()) != null) {
    // 		String[] split = s.split(",");
    // 		Station st = new Station(split[1],new UniversalPosition(Float.parseFloat(split[2]),Float.parseFloat(split[3])));
    // 		m.addStation(st);
    // 		map.addBeacon(new Beacon(st));
    // 	    }
    // 	} catch (IOException e) {
    // 	    System.out.println(e.getMessage());
    // 	}
    // }
    /*
     * Get the displayable stations from a point
     */
    public static void getStations(Airport a) {
        try {
            BufferedReader f = new BufferedReader(new FileReader("data/fixes.csv"));
            Double[] edges = UniversalPosition.edges(a.getLatitude(), a.getLongitude());
            String s;

            f.readLine();
            f.readLine();
            UniversalPosition universalMapCenter = new UniversalPosition((float) a.getLatitude(), (float) a.getLongitude());
            Position mapCenter = universalMapCenter.getPosition(PositionUnits.MILI_MILE);
            while ((s = f.readLine()) != null) {
                String[] split = s.split(",");
                if (Double.parseDouble(split[2]) > edges[0] 
                        && Double.parseDouble(split[2]) < edges[2] 
                        && Double.parseDouble(split[3]) > edges[1] 
                        && Double.parseDouble(split[3]) < edges[3]) {
                    UniversalPosition pos = new UniversalPosition(Float.parseFloat(split[2]), Float.parseFloat(split[3]));
                    if (pos.getPosition(PositionUnits.MILI_MILE).getDistance(mapCenter) > 2000.) {//remove stations near the center of the map
                        Station st = new Station(split[1], pos);
                        m.addStation(st);
                        GUI.getGUI().addBeacon(st);
                    } 
                }
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Get the runways of an airport
     */
    public static void getRunways(Airport a) {
        try {
            BufferedReader f = new BufferedReader(new FileReader("data/runways.csv"));
            String s;

            f.readLine();
            f.readLine();
            while ((s = f.readLine()) != null) {
                String[] split = s.split(",");
                if (split[0].equals(a.getID()) && !split[3].isEmpty()) {
                    int angle = 0;
                    char[] angle_char = split[1].toCharArray();
                    if (!Character.isDigit(angle_char[0])) {
                        if (split[1].equals("N")) {
                            angle = 0;
                        } else if (split[1].equals("W")) {
                            angle = 90;
                        } else if (split[1].equals("S")) {
                            angle = 180;
                        } else if (split[1].equals("E")) {
                            angle = 270;
                        } else if (split[1].equals("NW")) {
                            angle = 45;
                        } else if (split[1].equals("SW")) {
                            angle = 135;
                        } else if (split[1].equals("SE")) {
                            angle = 225;
                        } else if (split[1].equals("NE")) {
                            angle = 315;
                        }

                    } else {
                        angle = 100 * (angle_char[0] - '0') + 10 * (angle_char[1] - '0');
                    }
                    Runway r = new Runway(split[1], new UniversalPosition(Float.parseFloat(split[3]), Float.parseFloat(split[4])), new Heading(angle), (int) (Float.parseFloat(split[2]) * 0.189394), true);
                    m.addRunway(r);
                    GUI.getGUI().addRunway(r);
                }
            }
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Airport getAirport(String icao) {
        String s;
        Airport a = null;
        try {
            BufferedReader f = new BufferedReader(new FileReader("data/airports.csv"));
            f.readLine();
            f.readLine();

            while ((s = f.readLine()) != null) {
                String[] split = s.split(",");
                if (!split[1].isEmpty() && split[1].equals(icao)) {
                    a = new Airport(split[1], Double.parseDouble(split[4]), Double.parseDouble(split[5]));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public static void parse(Airport a) {
        getRunways(a);
        getStations(a);
    }

}
