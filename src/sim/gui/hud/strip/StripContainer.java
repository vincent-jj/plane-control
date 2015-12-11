package sim.gui.hud.strip;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import sim.engine.Plane;
import sim.util.Updatable;

/**
 *
 * @author clement
 */
class StripContainer extends JPanel implements Updatable {

    private static int STRIPS_PADDING_SIZE = 1;
    private static Color landingStripColor = new Color(248, 222, 126);
    private static Color takingOffStripColor = new Color(180, 190, 255);
    private static Color stripColor = new Color(248, 222, 126);
    private static Color borderColor = Color.black;

    private Strip strip;

    public StripContainer(Plane plane) {
        strip = new Strip(plane);

        JPanel stripBackground = new JPanel();
        stripBackground.setLayout(new BoxLayout(stripBackground, BoxLayout.X_AXIS));
        stripBackground.setOpaque(true);
        stripBackground.setVisible(true);
        if (plane.isTakingOffPlane()) {
            stripBackground.setBackground(takingOffStripColor);
        } else {
            stripBackground.setBackground(landingStripColor);
        }
        stripBackground.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        stripBackground.add(strip);
        stripBackground.add(Box.createHorizontalGlue());

        JPanel stripBgXCenter = new JPanel();
        stripBgXCenter.setOpaque(false);
        stripBgXCenter.setLayout(new BoxLayout(stripBgXCenter, BoxLayout.X_AXIS));
        stripBgXCenter.add(Box.createHorizontalStrut(3));
        stripBgXCenter.add(stripBackground);
        stripBgXCenter.add(Box.createHorizontalStrut(3));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        this.add(stripBgXCenter);
        this.add(Box.createVerticalStrut(STRIPS_PADDING_SIZE));
    }

    public StripContainer(String text) {
        strip = new Strip(text);

        JPanel stripBackground = new JPanel();
        stripBackground.setLayout(new BoxLayout(stripBackground, BoxLayout.X_AXIS));
        stripBackground.setOpaque(true);
        stripBackground.setVisible(true);
        stripBackground.setBackground(stripColor);
        stripBackground.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        stripBackground.add(strip);
        stripBackground.add(Box.createHorizontalGlue());

        JPanel stripBgXCenter = new JPanel();
        stripBgXCenter.setOpaque(false);
        stripBgXCenter.setLayout(new BoxLayout(stripBgXCenter, BoxLayout.X_AXIS));
        stripBgXCenter.add(Box.createHorizontalStrut(3));
        stripBgXCenter.add(stripBackground);
        stripBgXCenter.add(Box.createHorizontalStrut(3));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setOpaque(false);
        this.add(stripBgXCenter);
        this.add(Box.createVerticalStrut(STRIPS_PADDING_SIZE));
    }

    @Override
    public boolean equals(Object p) {
        return this.strip.equals(p);
    }

    @Override
    public int hashCode() {
        return this.strip.hashCode();
    }

    @Override
    public void update() {
        this.strip.update();
    }

}
