package sim.gui.map;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.LinkedList;
import sim.util.Updatable;
import sim.util.UniversalPosition;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.TextArea;
import java.awt.Point;
import java.util.Iterator;
import sim.engine.Plane;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;
import java.net.URL;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/**
 * @TODO : make this class a singleton
 *
 */
public class Map extends JPanel implements Updatable {

    //TODO Duplication au niveau de url, voir lequel garder si il y a des différences.
    private int mapWidth;
    private int mapHeight;
    private Color mapColor = new Color(36, 68, 92);

    private LinkedList<Beacon> beaconList;
    private LinkedList<RunwayDraw> runwayList;
    private LinkedList<PlaneDraw> planeList;

    private int maxInt = 200;
    private TextArea textArea;
    private Dimension textAreaDimension;
    private boolean componentClicked; //true if one component has already been clicked
    private Point mouseLocation;
    private String idObjectClicked; // this is the id of the last object clicked 
    private String commandStrip; // this is the String to send to the strip of the last doubleClicked object
    private String planeStrip;
    private boolean somethingInCommand; // true if plane's informations are in the command strip
    private int altitudeStrip;

    private double toRemove;


    private BufferedImage googleBuffer = null;
    private boolean doubleClickDone;

    private Image googleMap = null;

    public Map(Dimension d) {
	
        this.mapWidth = d.width*4/ 5;
	    this.mapHeight = d.height;
UniversalPosition.setScreenSize(mapWidth,mapHeight);
        d.setSize(mapWidth, mapHeight);
        this.setPreferredSize(d);
	

	String url = "http://maps.googleapis.com/maps/api/staticmap?center=";
	url += UniversalPosition.getCenterLatitude() + "," + UniversalPosition.getCenterLongitude(); //"41.974054,-87.907353";
	url += "&zoom=10";
	
	url += "&size=" + Math.min(this.mapWidth, this.mapHeight) + "x" + Math.min(this.mapWidth, this.mapHeight); // x not * ...
	url += "&maptype=terrain";
	url += "&style=feature:all|element:labels|visibility:off"; // labels off
	url += "&scale=2"; // more pixels
	System.out.println(url);
	try {
	    URL con = new URL(url);
	    ImageIcon mapIcon = new ImageIcon(con);
	    googleMap = mapIcon.getImage();
	    double squareSize = 40000.0*Math.cos(UniversalPosition.getCenterLatitude()*Math.PI/180)/410.0;
	    if(googleMap == null)
		System.out.println("img null");

	    else if (squareSize > 25.0*1.609) {
		int min = Math.min(mapHeight, mapWidth), max = Math.max(mapHeight, mapWidth);
		toRemove = ((squareSize-(25.0*1.609))/2.0)/squareSize; // percentage of the map to remove in each side
		System.out.println("toremove: " + toRemove + "square size: " + squareSize + "widht, height: " + mapWidth + mapHeight);
		//googleMap = (BufferedImage)googleMap;
		googleBuffer = new BufferedImage(max, max, BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = googleBuffer.createGraphics();
		bGr.drawImage(googleMap, (int)(toRemove*mapWidth), (int)(toRemove*mapHeight), null); 
		bGr.dispose();

		//googleBuffer = (BufferedImage)googleMap;
		//googleBuffer = googleBuffer.getSubimage((int)((this.mapWidth*40/squareSize)/2), this.mapHeight-(int)((this.mapHeight*25/squareSize)/2), (int)(this.mapWidth*25/squareSize), (int)(this.mapHeight*25/squareSize));
		int sc =(int)(squareSize*1.609*1000*toRemove);
		System.out.println(sc);
		System.out.println(UniversalPosition.getScale());
		int scPixel=(int)(sc/UniversalPosition.getScale()*25.0*1.609/squareSize*min/max);
		System.out.println("corner: "+ scPixel+ ", new square size: "+ (int)(min*(1.0-toRemove)) + "  " + (mapHeight-scPixel)  );
		googleBuffer = googleBuffer.getSubimage(2*(int)(max*toRemove),(int)(1.69*max*toRemove), (int)(min*(1.0-toRemove)), (int)(min*(1.0-toRemove)));
		
		googleMap = (Image)googleBuffer;
	    }
	    
	    //JLabel picLabel = new JLabel(new ImageIcon(map));
	    //this.add(picLabel);
	}

	catch (MalformedURLException e) { 
	    System.out.println("Bad URL: " + e);
	} 

	catch (Exception e){
	    System.out.println("Exception: " + e);
	}
     

        this.beaconList = new LinkedList<Beacon>();
        this.runwayList = new LinkedList<RunwayDraw>();
        this.planeList = new LinkedList<PlaneDraw>();
<<<<<<< HEAD
        this.componentClicked=false;
        this.idObjectClicked="";
        this.commandStrip="";
        this.somethingInCommand=false;
        this.doubleClickDone=false;
        this.altitudeStrip=0;
        this.planeStrip="";
	this.addMouseListener(this);
	this.addMouseWheelListener(this);
        this.mouseLocation = new Point();



        /*String url2 = "http://maps.googleapis.com/maps/api/staticmap?center=";
	url2 += UniversalPosition.getCenterLatitude() + "," + UniversalPosition.getCenterLongitude(); //"41.974054,-87.907353";
=======
        this.componentClicked = false;
        this.idObjectClicked = "";
        this.commandStrip = "";
        this.somethingInCommand = false;
        this.doubleClickDone = false;
        this.altitudeStrip = 0;
        this.planeStrip = "";


        /*
         textArea=new TextArea();
    
         this.textArea.setLocation(this.mapWidth, this.mapHeight);
         this.textAreaDimension=new Dimension(mapWidth/100, mapHeight/100);
         this.textArea.setSize(textAreaDimension);
         this.add(textArea);*/
        //textArea = new TextArea();
        //textArea.setFocusable(false);
        //this.add(textArea);

        this.addMouseListener(new MouseListener() {
            //where initialization occurs:
            //Register for mouse events on blankArea and the panel.
            @Override
            public void mousePressed(MouseEvent e) {
                boolean objectFound = false;
                Map.this.mouseLocation = Map.this.getMousePosition(true);
                for (PlaneDraw p : Map.this.planeList) {
                    System.out.println(beaconList + " \n");
                    p.createRect();
                    if (p.getRect().contains(mouseLocation)) {
                        objectFound = true;
                        saySomething("Object Clicked", e, p.printInfos(), p.getPlane().getId());
                        System.out.println("plane= " + objectFound);
                        if (doubleClicked(e)) {
                            somethingInCommand = true;
                            doubleClickDone = true;
                            System.out.println("doubleClicked on plane");
                            Map.this.planeStrip = p.sendToCommand();
                            Map.this.altitudeStrip = p.getPlane().getAltitude();
                        }
                        if (somethingInCommand == true) {
                            Map.this.updateCommandStrip(p.sendToCommand());
                        }

                    }
                }
                if (objectFound == false) {
                    for (Beacon b : Map.this.beaconList) {
                        b.createRect();
                        if (b.getRect().contains(mouseLocation)) {
                            objectFound = true;
                            saySomething("Object Clicked", e, b.printInfos(), b.getStation().getId());
                            System.out.println("beacon= " + objectFound);
                            if (doubleClicked(e)) {
                                doubleClickDone = true;
                                Map.this.addCommandStrip(b.sendToCommand());
                                System.out.println("doubleClicked on beacon");
                            }

                        }
                    }
                    if (objectFound == false) {
                        for (RunwayDraw r : Map.this.runwayList) {
                            r.createRect();
                            if (r.getRect().contains(mouseLocation)) {
                                System.out.println("Dans le for runway");
                                r.printInfos();
                                objectFound = true;
                                saySomething("Object Clicked", e, r.printInfos(), r.getRunway().getId());
                                System.out.println("runway= " + objectFound);
                                if (doubleClicked(e)) {
                                    doubleClickDone = true;
                                    Map.this.addCommandStrip(r.sendToCommand());
                                    System.out.println("doubleClicked on runway");
                                }
                            }
                        }
                    }
                    if (objectFound == false) {
                        System.out.println("else= " + objectFound);
                        if (componentClicked) {
                            Map.this.remove(textArea);
                        }
                        Map.this.componentClicked = false;
                        Map.this.idObjectClicked = "";
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                /*saySomething("Mouse released; # of clicks: "
                 + e.getClickCount(), e);*/
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //saySomething("Mouse entered", e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //saySomething("Mouse exited", e);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                /*saySomething("Mouse clicked (# of clicks: "
                 + e.getClickCount() + ")", e);*/
            }
        });
        this.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                System.out.println("Wheel rotated");
                if (somethingInCommand) {
                    e.getScrollType();
                    Map.this.altitudeStrip = Map.this.altitudeStrip + (e.getWheelRotation() * 100);
                    changeAltiStrip(String.valueOf(Map.this.altitudeStrip) + "ft");
                }

            }
        });
        this.mouseLocation = new Point();
        this.altitudeStrip = 0;

    }
    /*
     public void setDrawing(Shape shape){
     this.beaconShape=shape;
     }
     */

    public void resizeMap(Dimension bounds) {
        this.setPreferredSize(bounds);
        this.revalidate();
    }

    public void addBeacon(Beacon beacon) {
        this.beaconList.add(beacon);
    }

    public void addRunway(RunwayDraw runway) {
        this.runwayList.add(runway);
    }

    public void addPlane(PlaneDraw plane) {
        this.planeList.add(plane);
    }

    public void removePlane(PlaneDraw plane) {
        this.planeList.remove(plane);
    }

    public void removePlane(Plane plane) {
        Iterator i = this.planeList.iterator();
        PlaneDraw p;
        while (i.hasNext()) {
            if (((PlaneDraw) i.next()).getPlane().equals(plane)) {
                i.remove();
                return;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
	int size = Math.min(this.mapHeight, this.mapWidth);
	int v = (mapHeight-size)/2;
	int h = (mapWidth-size)/2;
	g.translate((int)toRemove*mapWidth,0);
	g.drawImage(googleMap, h, v, Math.min(this.mapHeight, this.mapWidth), Math.min(this.mapHeight, this.mapWidth), null);

	for (Beacon b : this.beaconList) {
            b.paintComponent(g);
        }

        for (RunwayDraw r : this.runwayList) {
            r.paintComponent(g);
        }

        for (PlaneDraw p : this.planeList) {
            p.paintComponent(g);
        }

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.mapWidth, this.mapHeight);
    }

   @Override
    public void setPreferredSize(Dimension d) {
        super.setPreferredSize(d);
        this.mapWidth = d.width;
        this.mapHeight = d.height;
    }

    @Override
    public void update() {
        Dimension d = getSize();
        mapWidth = d.width;
        mapHeight = d.height;
        UniversalPosition.setScreenSize(mapWidth, mapHeight);
        for (PlaneDraw plane : this.planeList) {
            plane.update();
        }
        for (Beacon b : this.beaconList) {
            b.updatePosition();
        }
        for (RunwayDraw r : this.runwayList) {
            r.updatePosition();
        }
        this.repaint();
    }

    void saySomething(String eventDescription, MouseEvent e, String infos, String id) {

        if ((componentClicked == true) && (id.equals(idObjectClicked))) {
            this.remove(textArea);
            this.idObjectClicked = "";
            this.componentClicked = false;
        } else {
            if (componentClicked == true) {
                this.remove(textArea);
            }
            textArea = new TextArea();
            this.textAreaDimension = new Dimension(mapWidth / 10, mapHeight / 10);
            this.textArea.setSize(textAreaDimension);

            int mx = (int) this.mouseLocation.getX(),
                    my = (int) this.mouseLocation.getY(),
                    mw = (int) this.textAreaDimension.getWidth(),
                    mh = (int) this.textAreaDimension.getHeight();
            this.textArea.setLocation(mx + 1, my + 1);
            textArea.append(infos);
            this.add(textArea);
            this.idObjectClicked = id;
            this.componentClicked = true;
        }
        //textArea.setCaretPosition(maxInt);

    }

    public void updateCommandStrip(String newCommand) {
        this.commandStrip = commandStrip + " " + newCommand;
    }

    public void addCommandStrip(String msg) {
        this.commandStrip = this.commandStrip + " " + msg;
    }

    public void changeAltiStrip(String newAlti) {
        this.commandStrip = this.planeStrip + " " + newAlti;
    }

    public boolean doubleClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            return true;
        }
        return false;
    }

    public String getCommandStrip() {
        return this.commandStrip;
    }

    public boolean getSomethingInCommand() {
        return somethingInCommand;
    }

    public boolean getDoubleClickDone() {
        return doubleClickDone;
    }

}
