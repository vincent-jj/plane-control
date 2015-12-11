package sim.gui.menubar;

import java.awt.event.KeyEvent;
import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import sim.gui.Window;

/**
 *
 * @author clement
 */
public class MainMenuBar extends JMenuBar {

    public MainMenuBar(Window window) {
        this.add(Box.createHorizontalGlue());

        JMenu helpMenu = new HelpMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        this.add(helpMenu);
    }
}
