package sim.gui.hud.command;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author clement
 */
class CommandNotifier extends JPanel {

    public CommandNotifier(String text) {
        this(text, false);
    }

    public CommandNotifier(String text, boolean startingVisibility) {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(Box.createHorizontalGlue());
        this.add(new JLabel(text));
        this.add(Box.createHorizontalGlue());

        this.setVisible(startingVisibility);
    }

    public void display() {
        this.setVisible(true);
    }

    public void remove() {
        this.setVisible(false);
    }
}
