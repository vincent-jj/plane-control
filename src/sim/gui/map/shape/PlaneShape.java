package sim.gui.map.shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class PlaneShape extends Shape {

    private Polygon poly;

    public PlaneShape(Color color, Dimension size) {
        super(color, size);
    }

    @Override
    public void drawShape(Graphics2D g2d) {
        int width = getWidth() / 2;
        int height = getHeight() / 2;

        poly = new Polygon();
        poly.addPoint(-width, 0);
        poly.addPoint(-6 * width, -height / 4);
        poly.addPoint(-6 * width, height / 4);
        poly.addPoint(-width, height);
        poly.addPoint(-width, 3 * height);
        poly.addPoint(0, 35 * height / 10);

        poly.addPoint(width, 3 * height);
        poly.addPoint(width, height);
        poly.addPoint(6 * width, height / 4);
        poly.addPoint(6 * width, -height / 4);
        poly.addPoint(width, 0);

        poly.addPoint(width, -height);
        poly.addPoint(2 * width, -height * 12 / 10);
        poly.addPoint(2 * width, -height * 14 / 10);

        poly.addPoint(0, -height * 13 / 10);

        poly.addPoint(-2 * width, -height * 14 / 10);
        poly.addPoint(-2 * width, -height * 12 / 10);
        poly.addPoint(-width, -height);

        g2d.setColor(this.getColor());
        g2d.drawPolygon(poly);
        g2d.fillPolygon(poly);

    }

    public Polygon getPolygon() {
        return this.poly;
    }

    @Override
    public String toString() {
        return "PlaneShape set";
    }
}
