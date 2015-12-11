package sim.gui;

import sim.gui.menubar.MainMenuBar;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.Rectangle;
import java.awt.Toolkit;
import sim.engine.Plane;
import sim.engine.Runway;
import sim.engine.Station;
import sim.gui.hud.HUD;
import sim.gui.map.Beacon;
import sim.gui.map.Map;
import sim.gui.map.PlaneDraw;
import sim.gui.map.RunwayDraw;
import sim.util.Updatable;
import sim.util.UniversalPosition;

public class Window extends JFrame implements Updatable, ComponentListener {

    private int screenWidth;
    private int screenHeight;

    protected Map map;
    protected HUD hud;
    private Rectangle bounds;

    protected Window() {
        this.setTitle("Application contrôle aérien");
        // incoming

        Dimension bounds = Toolkit.getDefaultToolkit().getScreenSize();

        this.screenWidth = bounds.width;
        this.screenHeight = bounds.height;
        this.setSize(screenWidth, screenHeight);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        //this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.map = new Map(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));


        /*StripsDisplay strips = new StripsDisplay();
         this.setLayout(new BorderLayout());*/
        this.add(map, BorderLayout.CENTER);

        this.setJMenuBar(new MainMenuBar(this));
        
        this.setLocationRelativeTo(null);
        this.pack();

        this.setVisible(true);
    }

    public Map getMap() {
        return map;
    }

    public void writeToCommand(String string) {
        hud.writeToCommand(string);

    }

    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("Coucou\n");
        Dimension rec = this.getContentPane().getSize();
	System.out.println(rec);
        //Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());

        //rec.height -= scnMax.top; // scnMax.top = taskBarSize
        map.resizeMap(rec);
        UniversalPosition.setScreenSize(rec.width, rec.height); //bounds.width, bounds.height);
        this.revalidate();

    }

    @Override
    public void componentShown(ComponentEvent e) {
        //System.out.println(e.getComponent().getClass().getName() + " --- Shown");
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        //System.out.println(e.getComponent().getClass().getName() + " --- Moved");
    }

    public void commandSent(boolean checkCmd) {
        hud.commandSent(checkCmd);
    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public void addPlane(Plane plane) {
        map.addPlane(new PlaneDraw(plane));
        hud.addStrip(plane);
    }

    public void removePlane(Plane plane) {
        hud.removeStrip(plane);
        map.removePlane(plane);
    }

    public void addBeacon(Station station) {
        map.addBeacon(new Beacon(station));
    }

    public void addRunway(Runway runway) {
        map.addRunway(new RunwayDraw(runway));
    }
    /*
     public static void main(String[] args)
     {
     Window myWind =new Window();
     }
     */

    @Override
    public void update() {
        map.update();
        hud.update();
    }

    public void changeMap(Map map) {
        this.map = map;
        hud.flush();
    }

    public void addHUD(HUD hud) {
        //ADD HUD ON THE EAST
        this.hud = HUD.getHUD();
        this.add(hud, BorderLayout.EAST);

        this.revalidate();
    }

    public HUD getHud() {
        return this.hud;
    }

}
