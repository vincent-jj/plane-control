package sim.gui.hud.strip;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import sim.engine.Plane;
import sim.gui.hud.HUD;
import sim.util.Updatable;

/**
 *
 * @author clement
 */
class Strip extends JLabel implements Updatable {

    private Plane plane = null;
    private StripString labelText;

    public Strip(Plane plane) {
        this(plane.makeStrip());
        this.plane = plane;

        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Strip strip = (Strip) e.getSource();
                HUD.getHUD().writeToCommand(strip.plane.getId() + ' ');
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });
    }

    public Strip(String text) {
        super();
        this.labelText = new StripString(text);
        this.setText(labelText.toString());
        this.setPreferredSize(new Dimension(50, 30));
        this.setOpaque(false);
        this.setFocusable(false);
    }

    @Override
    public boolean equals(Object p) {
        return (p instanceof Plane && this.plane.equals(p));
    }

    @Override
    public void update() {
        if (plane != null) {
            this.labelText.setText(this.plane.makeStrip());
            this.setText(labelText.toString());
        }
    }
}
