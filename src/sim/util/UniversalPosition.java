package sim.util;

/**
 * A class to unify position information beetwen modules
 * You need to call setCenter and setScreenSize before converting posirtions.
 * For pixel coordinates, zero is at top left.
 * For Miles coordinates,  zero is at center, but the orientation is the same as for pixels
 */
public class UniversalPosition {
    private static int screenWidth;//in pixel
    private static int screenHeight;//in pixel
    private static int screenScale;
    private static int mapWidth;//in mili_miles
    private static int mapHeight;//in milimiles
    private static double centerLatitude;
    private static double centerLongitude;
    private static final int latitudeDegreeLength=24860000/360;//in mili_mile/°. This value may have to be checked (earth is not perfectly spherical)
    private static int longitudeDegreeLength;//in mili_miles/° . This value depends of latitude and must be calculated.

    private Position pos;
    private PositionUnits unit;

    private static void updateScreenScale(){
        screenScale=screenWidth;
        if (screenWidth*mapHeight > screenHeight*mapWidth){// > to see all map, < to cut the map and fill the screen
            screenScale = screenHeight;
        }
    }

    /**
     * set the screen size
     * it's important to call this function each time the window is resized
     */
    public static void setScreenSize(int width, int height){
        screenWidth = width;
        screenHeight = height;
        updateScreenScale();
    }

    /**
     * set the geographical coordinates of the center of the map.
     */
    public static void setCenter(double latitude, double longitude){
        centerLatitude = latitude;
        centerLongitude = longitude;
        longitudeDegreeLength = Math.round((int) (24880000*Math.cos(Math.toRadians(centerLatitude))))/360;
    }
    
    /**
     * set the size of the map.
     * @param width, height in mili_miles
     */
    public static void setMapSize(int width, int height){
        mapWidth = width;
        mapHeight = height;
    }

    /**
     * create an UniversalPosition corresponding of Position pos given in units
     */
    public UniversalPosition(Position pos, PositionUnits unit){
        this.pos=pos;
        this.unit=unit;
    }

    /**
     * create an UniversalPosition corresponding of the georaphical coordinates
     * Please note that setCenter must have been called before.
     */
    public UniversalPosition(float latitude, float longitude){
        int x=Math.round((int)((longitude - centerLongitude) * longitudeDegreeLength));
        int y=Math.round((int) ((centerLatitude - latitude) * latitudeDegreeLength));//In Miles, we use bottom-oriented coordinates
        pos = new Position(x,y);
        unit = PositionUnits.MILI_MILE;
    }

    /**
     * This function convert a position in the specified unit
     * please note that if you convert from differents units, setScreenSize must have been called before
     */
    public Position getPosition(PositionUnits wantedUnit){
        if (wantedUnit == unit){
            return new Position(pos.getX(),pos.getY());
        } else {
            return fromMiliMiles(toMiliMiles(),wantedUnit);
        }
    }

    private Position toMiliMiles(){
        Position res;
        switch (unit){
            case MILI_MILE:
                res = new Position(pos.getX(),pos.getY());
                break;
            case PIXEL:
                int x=(pos.getX()-screenWidth/2)*(mapWidth/screenScale);
                int y=(pos.getY()-screenHeight/2)*(mapHeight/screenScale);
                res = new Position(x,y);
                break;
            default:
                res = new Position(0,0);
        }
        return res;
    }

    private static Position fromMiliMiles(Position pos, PositionUnits wantedUnit){
        Position res;
        switch (wantedUnit){
            case MILI_MILE:
                res = new Position(pos.getX(), pos.getY());
                break;
            case PIXEL:
                int x=pos.getX()/(mapWidth/screenScale)+screenWidth/2;
                int y=pos.getY()/(mapHeight/screenScale)+screenHeight/2;
                res = new Position(x,y);
                break;
            default:
                res = new Position(0,0);
        }
        return res;
    }


    // this function computes the position on the map in pixel from the longitude and latitudeDegreeLength 
   /* public Position fromLongLat(double l1, double l2){
        return Position pixelPosition = new Position(0,0);
    }
*/

    public Position getPos(){
        return this.pos;
    }
    
    public static double getCenterLatitude() {
	return centerLatitude;
    }

    public static double getCenterLongitude() {
	return centerLongitude;
    }

    public static Double[] edges(double clat, double clong) {
         double lat1 = clat - (double) mapHeight/(2* (double)latitudeDegreeLength);
         double long1 = clong - (double)mapWidth/2/(double)longitudeDegreeLength;
         double lat2 = clat + (double)mapHeight/2/(double)latitudeDegreeLength;
         double long2 = clong + (double)mapWidth/2/(double)longitudeDegreeLength;
         Double[] result = new Double[4];
         result[0] = lat1;
         result[1] = long1;
         result[2] = lat2;
         result[3] = long2;
         return result;
     }

    /**
     * give a scale to convert distance in mili_miles to pixel
     * @return (mili_miles dist)/(pixel/dist)
     */
    public static int getScale(){
        if (screenScale==screenWidth){
            return mapWidth/screenScale;
        }
        return mapHeight/screenScale;
    }
}
