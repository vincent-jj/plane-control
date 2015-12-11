package sim.gui.map.shape;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Color;
import javax.swing.JComponent;

public abstract class Shape extends JComponent {

    private Color color;

    public Shape(Dimension size) {
        this(Color.black, size);
    }

    public Shape(Color color, Dimension size) {
        this.color = color;
        int x = -(int) size.getWidth() / 2;
        int y = -(int) size.getHeight() / 2;
        int h = -2 * y;
        int w = -2 * x;
        this.setBounds(x, y, h, w);
    }

    public Color setColor(Color c) {
        return this.color = c;
    }

    public Color getColor() {
        return this.color;
    }

    public abstract void drawShape(Graphics2D g2d);

}
