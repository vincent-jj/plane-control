package sim.gui.hud;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import sim.engine.Plane;
import sim.gui.GUI;
import sim.gui.hud.button.SpeedButtons;
import sim.gui.hud.command.CommandGetterContainer;
import sim.gui.hud.strip.StripsHandler;
import sim.util.Resizable;

import sim.util.Updatable;

/**
 *
 * @author clement
 */
public class HUD extends JPanel implements Updatable, Resizable {

    public static int HUD_WIDTH = GUI.getGUI().getContentPane().getWidth() / 4;
    private static HUD instance = new HUD();

    private StripsHandler stripsHandler;
    private CommandGetterContainer command;

    private HUD() {
        super();

        stripsHandler = StripsHandler.getStripsHandler();
        command = CommandGetterContainer.getCommandGetterContainer();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(stripsHandler);
        this.add(command);
        this.add(Box.createVerticalGlue());
        this.add(new SpeedButtons());
    }

    static public HUD getHUD() {
        return instance;
    }

    public void addStrip(Plane plane) {
        stripsHandler.addStrip(plane);
    }

    public void removeStrip(Plane plane) {
        stripsHandler.removeStrip(plane);
    }

    public void writeToCommand(String string) {
        command.addToCommand(string);
    }

    public void commandSent(boolean checkCmd) {
        command.commandSent(checkCmd);
    }

    @Override
    public void update() {
        stripsHandler.update();
    }
    
    public void flush() {
        stripsHandler.flush();
        command.flush();
    }
    
    @Override
    public void resize() {
        HUD.HUD_WIDTH = GUI.getGUI().getContentPane().getWidth() / 5;
        command.resize();
        stripsHandler.resize();
    }
}
