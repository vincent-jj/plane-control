package sim.gui.map;

import sim.gui.map.shape.Shape;
import sim.engine.Plane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import sim.gui.map.shape.PlaneShape;
import java.awt.Rectangle;
import sim.util.Updatable;
import java.awt.BasicStroke;
import sim.util.PositionUnits;


public class PlaneDraw extends JComponent implements Updatable {

    private Shape shape;
    private Rectangle rectangle;
    private Plane plane;
    private int positionX;
    private int positionY;

    public PlaneDraw(Plane plane) {
        try{
            this.positionX = plane.getPosition().getPosition(PositionUnits.PIXEL).getX();
            this.positionY = plane.getPosition().getPosition(PositionUnits.PIXEL).getY();}
        catch(NullPointerException e){}
        this.shape = new PlaneShape(Color.red, new Dimension(10, 2));
        this.plane = plane;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (!plane.mustBeDisplayed()){
            return;
        }
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(this.positionX, this.positionY);
        g2d.setStroke(new BasicStroke(1));
        g2d.rotate(Math.toRadians(+plane.getHeading().toInt()+180));
        g2d.setColor(new Color(139, 35, 35));
        this.shape.drawShape(g2d); // 10, 2 -> 10=width, 2=thickness, all planes same dimensions
        g2d.rotate(Math.toRadians(-plane.getHeading().toInt()-180));
        g2d.drawString(this.plane.getId(), 10, 5);
        g2d.translate(0,g2d.getFontMetrics().getHeight());
        g2d.drawString(plane.getAltitude()+plane.getVerticalDirection().toString()+this.plane.getCurrentSpeed(), 10, 5);
        this.createRect();
        
	//        int rectX=(int)this.rectangle.getX(),rectY=(int)this.rectangle.getY(),rectW=(int) this.rectangle.getWidth(), rectH=(int)this.rectangle.getHeight();
        //g2d.drawRect(0, 0, rectW, rectH);
        //g2d.draw(this.rectangle);
    }

    public int getX() {
        return this.positionX;
    }

    public int getY() {
        return this.positionY;
    }

    public void createRect() {
        this.rectangle = new Rectangle();
        PlaneShape polyShape = (PlaneShape) this.shape;
        this.rectangle = polyShape.getPolygon().getBounds();
        int recW = (int) this.rectangle.getWidth(), recH = (int) this.rectangle.getHeight();
        this.rectangle.setLocation(this.positionX - (recW / 4), this.positionY - (recH / 2));
//                System.out.println(this.rectangle);
    }

    public Rectangle getRect() {
        return this.rectangle;
    }

    public String printInfos() {
        return this.plane.getId() + "\n" + "posX=" + this.positionX + "\n" + "posY=" + this.positionY + "\n" + "alt=" + this.plane.getWantedAltitude() + "ft" + "\n" + "speed=" + this.plane.getCurrentSpeed() + "kts";
    }

    public String sendToCommand(){
        return this.plane.getId() + " clearance";
    }

    public Plane getPlane() {
        return this.plane;
    }

    @Override

    public void update() {
        if (!plane.mustBeDisplayed()){
            return;
        }
        this.positionX = plane.getPosition().getPosition(PositionUnits.PIXEL).getX();
        this.positionY = plane.getPosition().getPosition(PositionUnits.PIXEL).getY();
    }

}
