package sim.gui.map;

import sim.gui.map.shape.Shape;
import sim.engine.*;
import sim.util.PositionUnits;
import sim.util.UniversalPosition;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import sim.gui.map.shape.Rect;
import java.awt.Rectangle;

public class RunwayDraw extends JComponent {

    private Shape shape;
    private Runway runway;
    private int positionX;
    private int positionY;
    private Rectangle rect;

    public RunwayDraw(Runway runway) {
        try{
        //this.shape = new Rect(Color.BLACK, new Dimension(runway.getLength(), 5));
        this.positionX = runway.getPosition().getPosition(PositionUnits.PIXEL).getX();
        this.positionY = runway.getPosition().getPosition(PositionUnits.PIXEL).getY();
        }
        catch (NullPointerException e){}
        this.shape = new Rect(Color.BLACK, new Dimension(runway.getLength()/UniversalPosition.getScale(), 2));
        this.runway = runway;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(this.positionX, this.positionY);
        g2d.setStroke(new BasicStroke(1));
        g2d.rotate(Math.toRadians(runway.getHeading().toInt()+180));
        g2d.setColor(new Color(0, 0, 0));
        this.shape.drawShape(g2d);//, runway.getLength()
        g2d.translate(0,-15);//move to the alignement of the runway
        g2d.rotate(-Math.toRadians(runway.getHeading().toInt()+180));
        g2d.translate(-20,0);//center the text
        g2d.drawString(this.runway.getId(), 10, 5);

    }

    public Runway getRunway(){
        return this.runway;
    }

    public int getX(){
        return this.positionX;
    }

    public int getY(){
        return this.positionY;
    }

    public String printInfos(){
        return this.getRunway().getId()+" "+this.getRunway().getHeading().toInt()+" "+(this.getRunway().getInverseHeading());
    }

    public String sendToCommand(){
        return " "+this.getRunway().getId();
    }

    public void updatePosition(){
        this.positionX = runway.getPosition().getPosition(PositionUnits.PIXEL).getX();
        this.positionY = runway.getPosition().getPosition(PositionUnits.PIXEL).getY();
        shape = new Rect(Color.BLACK, new Dimension(runway.getLength()/UniversalPosition.getScale(), 2));
    }

    public void createRect(){
        this.rect = new Rectangle();
        this.rect=this.shape.getBounds();
        this.updatePosition();
        this.rect.setLocation(positionX, positionY);
        this.rect.grow(this.getRunway().getLength(), 1);
    }

    public Rectangle getRect(){
        return this.rect;
    }

}
