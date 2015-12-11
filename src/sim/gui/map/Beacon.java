package sim.gui.map;

import sim.gui.map.shape.Triangle;
import sim.gui.map.shape.Shape;
import sim.engine.*;
import sim.util.PositionUnits;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import java.awt.Rectangle;

public class Beacon extends JComponent {

    private final static Color BEACON_COLOR = new Color(204, 0, 51);
    private final static Dimension BEACON_DIMENSION = new Dimension(10, 10);
    private final static Shape BEACON_SHAPE = new Triangle(BEACON_COLOR, BEACON_DIMENSION);

    private Station station;
    private int positionX;
    private int positionY;
    private Rectangle rect;

    public Beacon(Station station) {
        this.positionX = station.getPosition().getPosition(PositionUnits.PIXEL).getX();
        this.positionY = station.getPosition().getPosition(PositionUnits.PIXEL).getY();
        this.station = station;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(this.positionX, this.positionY);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(new Color(204, 0, 51));
        g2d.drawString(this.station.getId(), -(int) BEACON_DIMENSION.getWidth(), 10);
        BEACON_SHAPE.drawShape(g2d);

    }

    public int getX() {
        return this.positionX;
    }

    public int getY() {
        return this.positionY;
    }

    public Station getStation() {
        return this.station;
    }

    public String printInfos() {
        return this.getStation().getId() + " " + this.getX() + " " + this.getY();
    }

    public String sendToCommand() {
        return " " + this.getStation().getId();
    }

    public void updatePosition() {
        this.positionX = station.getPosition().getPosition(PositionUnits.PIXEL).getX();
        this.positionY = station.getPosition().getPosition(PositionUnits.PIXEL).getY();
    }

    public void createRect() {
        int w = (int) this.BEACON_DIMENSION.getWidth(), h = (int) this.BEACON_DIMENSION.getHeight();
        this.rect = new Rectangle(this.getX() - (w / 2), this.getY() - (h / 2), w, h);
    }

    public Rectangle getRect() {
        return this.rect;
    }

}
