package sim.gui.map.shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public class Rect extends Shape {

    public Rect(Color color, Dimension size) {
        super(color, size);
    }

    @Override
    public void drawShape(Graphics2D g2d) {
        //int thickness=l;
        //int length= w;

        g2d.setColor(this.getColor());
        //AffineTransform old = g2d.getTransform();
        //g2d.rotate(Math.toRadians(angle));
        //draw shape/image (will be rotated)
        //g2d.setTransform(old);

        g2d.drawRect(0, 0, getWidth(), getHeight());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        //System.out.println("angle=" + angle);
        //g2d.rotate(Math.toRadians(angle));

        /*
         int width =w/2;
         int height= l/2;

         Polygon poly = new Polygon();
         poly.addPoint(-width,-height);
         poly.addPoint(-width,height);
         poly.addPoint(width, height);
         poly.addPoint(width, -height);

         g2d.setColor(this.getColor());
         g2d.drawPolygon(poly);
         g2d.fillPolygon(poly);
         g2d.rotate(Math.toRadians(angle));*/
    }

    @Override
    public String toString() {
        return "rect set";
    }

}
