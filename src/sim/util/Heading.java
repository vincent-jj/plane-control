package sim.util;

import java.lang.Boolean;
import java.lang.Math;
/**
 *This class is use to describe the orientation of a plane or a runway
 */
public class Heading{
    private static final int PRECISION = 100;
    private int angle;//this is the angular value in hundreadth of degree. 
    //It must be maintaned between 0*PRECISSION and 360*PRECISION

    /**
     * set the angle between 0 and 360*PRECISION
     */
    private void normalize(){
        angle = angle - ((angle/(360*PRECISION))*360*PRECISION);
    }
    public Heading(int h){
        angle=h*PRECISION;
        normalize();
    }

    public Heading(Position origin, Position destination){
        double dx = destination.getX() - origin.getX();
        double dy = origin.getY() - destination.getY(); // they are inversed because Y axe is inversed.
        if (dx == 0){
            if (dy > 0){
                angle=0*PRECISION;
            }else{
                angle=180*PRECISION;
            }
        }else{
            //the angle is first calculated in mathemathic standards (0 on right, counter-clockwise)
            double atan_f = Math.toDegrees(Math.atan(dy/dx));
            int atan_i = Math.round((int) (atan_f*PRECISION));
            if (dx < 0){
                atan_i = atan_i + 180*PRECISION;
            }
            //then we convert the angle into Heading standards (0 on top, clockwise)
            angle = - atan_i + 90*PRECISION;
        }
        normalize();
    }

    /**
     * @return a value between 0 and 359
     */
    public int toInt(){
        return angle/PRECISION;
    }

    public Heading clone(){
        Heading newHeading = new Heading(0);
        newHeading.angle = angle;
        return newHeading;
    }

    /**
     * compute the sinus in mathematic standards.
     * the sinus always represent the ordinate axe, oriented to top.
     * @return a value between -1 and 1
     */
    public double sin(){
        return Math.sin(Math.toRadians(90 - angle/PRECISION));
    }

    /**
     * compute the cosinus in mathematic standards.
     * the cosinus always represent the abscissa axe, oriented to right.
     * @return a value between -1 and 1
     */
    public double cos(){
        return Math.cos(Math.toRadians(90 - angle/PRECISION));
    }

    /**
     * compare two Heading
     *
     * @param delta the angular difference allowed between the two headings.
     * If this value is greater than 180°, the opposite is used (300°→60°)
     * @param other the second Haeding to compare
     * @return true if the angular difference is lower than precision.
     */
    public Boolean isNear(Heading other, Heading delta){
        int difference = angle - other.angle;
        if (difference < 0)
            difference = - difference;
        if (difference > 180 * PRECISION)
            difference = 360 * PRECISION - difference;
        return (difference <= delta.angle) &&
            (difference <= 360 * PRECISION - delta.angle);
    }

//<<<<<<< HEAD
//    public int getAngle(){
//        return this.angle;
//    }
//
//=======
    /**
     * give the better side to turn quikly to the other Heading
     * @param other the new wanted heading.
     * @return The direction is given for the moving plane : 
     * RIGHT means turn clockwise and LEFT means counter-clockwise.
     */
    public LeftRight nearestSide(Heading other){
        int difference = other.angle - angle;
        if (difference < 0)
            difference = difference + 360 * PRECISION;
        if (difference > 180 * PRECISION)
            return LeftRight.LEFT;
        return LeftRight.RIGHT;

    }

    /**
     * add two Heading.
     * The optional parameter side let you chose if you want add clockwise (default:RIGHT) or counter-clockwise (LEFT)
     * @param h1,h2 the two headings to add
     * @param side RIGHT means you add the two heading (the second is added clockwise).<br>
     * LEFT means substract the second heading of the first 
     * (the second is added counter-clockwise).
     *
     * @return the sum of the two params.
     */
    public static Heading add(Heading h1, Heading h2, LeftRight side){
        Heading newHeading = new Heading(0);
        if (side == LeftRight.RIGHT){
            newHeading.angle = h1.angle + h2.angle;
        }else{
            newHeading.angle = h1.angle - h2.angle;
        }
        newHeading.normalize();
        return newHeading;
    }

    public static Heading add(Heading h1, Heading h2){
        return add(h1,h2, LeftRight.RIGHT);
    }


    public void add(Heading other, LeftRight side){
        this.angle = add(this, other, side).angle;
    }
    public void add(Heading other){
        add(other, LeftRight.RIGHT);
    }

    public String toString(){
        String s = Integer.toString(angle/PRECISION) + "°";
        return s;

    }
}
