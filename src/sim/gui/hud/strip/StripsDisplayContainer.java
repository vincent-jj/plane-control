package sim.gui.hud.strip;

import java.awt.Dimension;
import javax.swing.JScrollPane;
import sim.gui.hud.HUD;
import sim.util.Resizable;

/**
 *
 * @author clement
 */
class StripsDisplayContainer extends JScrollPane implements Resizable {

    static private StripsDisplayContainer instance = new StripsDisplayContainer();

    private StripsDisplayContainer() {
        super(StripsDisplay.getStripsDisplay());
        this.setPreferredSize(new Dimension(HUD.HUD_WIDTH, 200));
    }

    static public StripsDisplayContainer getStripsDisplayContainer() {
        return instance;
    }

    @Override
    public void resize() {
        this.setPreferredSize(new Dimension(HUD.HUD_WIDTH, 200));
        StripsDisplay.getStripsDisplay().resize();
    }

}
