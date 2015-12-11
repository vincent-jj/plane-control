package sim.gui.map;

import java.awt.Dimension;
import java.awt.Color;

public class TestMap extends Map {

    private Color fillColor = new Color(51, 204, 255);

    public TestMap(Dimension d) {
        super(d);
/*
        // TEST BEACON
        boolean my_bool = false;
        Position pos1 = new Position(15, 15),
                pos2 = new Position(100, 100),
                pos3 = new Position(0, 15);
        Station station1 = new Station("beacon1", new UniversalPosition(pos1,PositionUnits.PIXEL), my_bool),
                station2 = new Station("beacon2", new UniversalPosition(pos2,PositionUnits.PIXEL), my_bool),
                station3 = new Station("beacon3", new UniversalPosition(pos3,PositionUnits.PIXEL), my_bool);
        Beacon beacon1 = new Beacon(station1);
        Beacon beacon2 = new Beacon(station2);
        Beacon beacon3 = new Beacon(station3);

        addBeacon(beacon1);
        addBeacon(beacon2);
        addBeacon(beacon3);

        // TEST RUNWAY
        Position posrun1 = new Position(50, 15),
                posrun2 = new Position(150, 150),
                posrun3 = new Position(230, 230); // position
        Heading heading1 = new Heading(0),
                heading2 = new Heading(45),
                heading3 = new Heading(90); // angle
        Runway  runway1 = new Runway("run1", new UniversalPosition(posrun1,PositionUnits.PIXEL), heading1, 10),
                runway2 = new Runway("run2", new UniversalPosition(posrun2,PositionUnits.PIXEL), heading2, 20),
                runway3 = new Runway("run3", new UniversalPosition(posrun3,PositionUnits.PIXEL), heading3, 100); // last arg = length

        RunwayDraw rd1 = new RunwayDraw(runway1);
        RunwayDraw rd2 = new RunwayDraw(runway2);
        RunwayDraw rd3 = new RunwayDraw(runway3);

        addRunway(rd1);
        addRunway(rd2);
        addRunway(rd3);

        // TEST PLANE
        Position posPlane1 = new Position(100, 200),
                posPlane2 = new Position(30, 180),
                posPlane3 = new Position(200, 47),
                posPlane4 = new Position(37, 278);
        Heading head1 = new Heading(30),
                head2 = new Heading(45),
                head3 = new Heading(46),
                head4 = new Heading(180);
        Plane plane1 = new LandingPlane("p1", 10000, 22, head1, new UniversalPosition(posPlane1,PositionUnits.PIXEL)),
                plane2 = new LandingPlane("p2", 10000, 22, head2,new UniversalPosition(posPlane2,PositionUnits.PIXEL)),
                plane3 = new LandingPlane("p3", 10000, 22, head3,new UniversalPosition(posPlane3,PositionUnits.PIXEL)),
                plane4 = new LandingPlane("p4", 10000, 22, head4,new UniversalPosition(posPlane4,PositionUnits.PIXEL));

        PlaneDraw pd1 = new PlaneDraw(plane1),
                pd2 = new PlaneDraw(plane2),
                pd3 = new PlaneDraw(plane3),
                pd4 = new PlaneDraw(plane4);

        addPlane(pd1);
        addPlane(pd2);
        addPlane(pd3);
*/
    }

}
