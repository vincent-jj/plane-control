package sim.gui.hud.strip;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import sim.engine.Plane;
import sim.util.Resizable;
import sim.util.Updatable;

/**
 *
 * @author clement
 */
public class StripsHandler extends JPanel implements Updatable, Resizable {

    private static StripsHandler instance = new StripsHandler();
    private static int OFFSET_STRIP_MODULE = 5;

    private StripsDisplay strips;
    private List<StripContainer> stripsList;
    private StripsDisplayContainer spc;

    private StripsHandler() {
        stripsList = new LinkedList<StripContainer>();
        strips = StripsDisplay.getStripsDisplay();
        spc = StripsDisplayContainer.getStripsDisplayContainer();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(spc);
        this.add(Box.createRigidArea(
                new Dimension(OFFSET_STRIP_MODULE, OFFSET_STRIP_MODULE)));
    }

    static public StripsHandler getStripsHandler() {
        return instance;
    }

    public void addStrip(Plane plane) {
        StripContainer sc = new StripContainer(plane);
        strips.addStrip(sc);
        stripsList.add(sc);
        this.repaint();
    }

    public void removeStrip(Plane plane) {

        int scIndex = -1;
        int currentIndex = 0;
        StripContainer sc = null;
        Iterator<StripContainer> i = stripsList.iterator();
        while (i.hasNext()) {
            sc = i.next();
            if (sc.equals(plane)) {
                scIndex = currentIndex;
                break;
            }
            currentIndex++;
        }
        //stripsList.indexOf(plane);
        if (scIndex < 0) {
            System.out.println("not removed");//for debug TO BE REMOVED
            return;
        }

        stripsList.remove(scIndex);
        strips.removeStrip(sc);
        spc.revalidate();
    }

    @Override
    public void update() {
        for (StripContainer sc : this.stripsList) {
            sc.update();
        }
    }

    public void flush() {
        strips.flush();
    }

    @Override
    public void resize() {
        this.spc.resize();
    }
}
