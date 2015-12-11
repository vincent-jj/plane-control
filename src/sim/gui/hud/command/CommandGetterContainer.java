package sim.gui.hud.command;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import sim.gui.hud.HUD;
import sim.util.Resizable;

/**
 *
 * @author clement
 */
public class CommandGetterContainer extends JPanel implements Resizable {

    static private CommandGetterContainer instance = new CommandGetterContainer();

    private CommandGetter commands = CommandGetter.getCommandGetter();
    private CommandNotifierContainer notif;

    private CommandGetterContainer() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel commandContainer = new JPanel();
        commandContainer.add(this.commands);
        commandContainer.add(new GoButton(this.commands));
        this.add(commandContainer);
        //this.add(commands);

        this.notif = new CommandNotifierContainer("Command received.", "Bad command.");
        JPanel notifXCenter = new JPanel();
        notifXCenter.setLayout(new BoxLayout(notifXCenter, BoxLayout.X_AXIS));
        notifXCenter.add(Box.createHorizontalGlue());
        notifXCenter.add(this.notif);
        notifXCenter.add(Box.createHorizontalGlue());
        this.add(notifXCenter);
        //this.add(this.notif);

        this.setPreferredSize(new Dimension(HUD.HUD_WIDTH, 30));
    }

    static public CommandGetterContainer getCommandGetterContainer() {
        return instance;
    }

    public void addToCommand(String string) {
        commands.requestFocus();
        commands.append(string);
    }

    public void commandSent(boolean checkCmd) {
        this.notif.commandSent(checkCmd);
    }

    public void flush() {
        commands.flush();
    }

    @Override
    public void resize() {
        this.setPreferredSize(new Dimension(HUD.HUD_WIDTH, 30));
    }

}
