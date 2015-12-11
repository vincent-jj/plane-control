package sim.gui.hud.command;

import static java.lang.Thread.sleep;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author clement
 */
class CommandNotifierContainer extends JPanel {

    private CommandNotifier notifOk;
    private CommandNotifier notifNOk;

    private Thread commandTimer;

    public CommandNotifierContainer(String textOk, String textNOk) {
        this.notifOk = new CommandNotifier(textOk);
        this.notifNOk = new CommandNotifier(textNOk);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(notifOk);
        this.add(notifNOk);

        this.commandTimer = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(750);
                } catch (InterruptedException ex) {
                } finally {
                    notifOk.remove();
                    notifNOk.remove();
                }
            }
        };
    }

    public void commandSent(boolean checkCmd) {
        if (checkCmd) {
            notifNOk.remove();
            this.displayCommandOk();
        } else {
            notifOk.remove();
            this.displayCommandNotOk();
        }

        //Hack to avoid breaking on calling start
        if (!this.commandTimer.isAlive()) {
            try {
                this.commandTimer.start();
            } catch (IllegalThreadStateException e) {
            }
        }
    }

    private void displayCommandOk() {
        notifNOk.remove();
        notifOk.display();
    }

    private void displayCommandNotOk() {
        notifOk.remove();
        notifNOk.display();
    }
}
