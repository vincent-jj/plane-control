package sim.gui;

/**
 *
 * @author clement
 */
public class GUI extends Window {

    private static GUI instance = new GUI();

    private GUI() {
        this.setVisible(false);
        /* Just for tests, to be removed */
        /*Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
         int mw = 3 * dim.width / 5;
         int mh = dim.height;
         this.remove(map);
         map = new TestMap(new Dimension(dim));
         add(map, BorderLayout.CENTER);
                
         UniversalPosition posPlane1 = new UniversalPosition(new Position(mw - 100, mh - 200), PositionUnits.PIXEL),
         posPlane2 = new UniversalPosition(new Position(mw - 30, mh - 180), PositionUnits.PIXEL),
         posPlane3 = new UniversalPosition(new Position(mw - 200, mh - 47), PositionUnits.PIXEL),
         posPlane4 = new UniversalPosition(new Position(mw - 37, mh - 278), PositionUnits.PIXEL);
         Heading head1 = new Heading(30),
         head2 = new Heading(45),
         head3 = new Heading(46),
         head4 = new Heading(180);
         /*
         Plane plane1 = new LandingPlane("p11", 10000, 22, head1, posPlane1),
         plane2 = new LandingPlane("p12", 10000, 22, head2, posPlane2),
         plane3 = new LandingPlane("p13", 10000, 22, head3, posPlane3),
         plane4 = new LandingPlane("p14", 10000, 22, head4, posPlane4);

         addPlane(plane1);
         addPlane(plane2);
         addPlane(plane3);
         addPlane(plane4);
         */
    }

    public static GUI getGUI() {
        return instance;
    }

    public static void displayGame() {
        instance.setVisible(true);
    }
}
