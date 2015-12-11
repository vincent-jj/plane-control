package sim.gui.map.shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Triangle extends Shape {

    public Triangle(Dimension size) {
        this(Color.black, size);
    }

    public Triangle(Color color, Dimension size) {
        super(color, size);
    }

    @Override
    public void drawShape(Graphics2D g2d) {
        int width = getWidth() / 2;
        int height = getHeight() / 2;

        Polygon poly = new Polygon();
        poly.addPoint(-width, 0);
        poly.addPoint(width, 0);
        poly.addPoint(0, -height);

        g2d.setColor(this.getColor());
        g2d.drawPolygon(poly);
    }

    @Override
    public String toString() {
        return "triangle set";
    }

}
