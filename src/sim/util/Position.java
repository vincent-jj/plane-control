package sim.util;


/**
 * A class implementing a position. (0,0) being the top-left of the map
 */
public class Position {
	private int x;
	private int y;

	/**
	 * 
	 * @param i x position
	 * @param j y position
	 */
	public Position(int i, int j) {
		this.x = i;
		this.y = j;
	}
	
	/**
	 * @return The position on the x-axis
	 */
	public int getX() {
		return x;	
	}
	
	/**
	 * @return The position on the y-axis
	 */
	public int getY() {
		return y;
	}
	/**
	 * 
	 * @param p
	 * @return
	 */
	public double getDistance(Position p) {
		return Math.sqrt(Math.pow(this.getY() - p.getY(),2) + Math.pow(this.getX() - p.getX(),2));
	}
	
	public void setX(int i) {
		this.x = i;
	}
	
	public void setY(int i) {
		this.y = i;
	}
}
