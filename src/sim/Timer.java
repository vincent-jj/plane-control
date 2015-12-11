package sim;

import javax.swing.SwingUtilities;

import sim.engine.Engine;
import sim.gui.GUI;

/**
 *
 * @author clement
 */
public class Timer extends Thread {

    int interlude;
    Engine engine;
    GUI gui;

    public Timer(Engine e, GUI g, int i) {
        engine = e;
        gui = g;
        interlude = i;
    }

    @Override
    public void run() {

        engine.createTakingOffPlane();
        while (true) {
            if (this.gui.getMap().getDoubleClickDone()) {
                this.gui.getHud().flush();
                this.gui.getHud().writeToCommand(this.gui.getMap().getCommandStrip());
            }
            engine.update();
            if(engine.finish()) {
            	System.out.println("Vous avez fait un score de " + engine.getScore() +" points");
            	return;
            }
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    GUI.getGUI().update();
		}
            });
            //gui.update();
            try {
                Timer.sleep(interlude * 1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
