package tst;

import sim.util.Heading;
import sim.util.Position;
import sim.util.LeftRight;
class TestHeading{
    private boolean isNear(double a, double b){
        final int PRECISION = 1;//in percentage
        double epsilon = Math.max(Math.abs(a), Math.abs(b)) * PRECISION / 100;
        if (a == 0 || b == 0)
            epsilon = 0.01;
        return Math.abs(a-b) <= epsilon;
    }
    public void testIsNearEquality(){
        assert new Heading(20).isNear(new Heading(20), new Heading(0));
        assert !new Heading(20).isNear(new Heading(21), new Heading(0));
    }
    public void testConstructorCircular(){
        assert new Heading(720).isNear(new Heading(-720), new Heading(0));
    }
    public void testIsNearSimple(){
        assert new Heading(20).isNear(new Heading(30), new Heading(15));
        assert new Heading(20).isNear(new Heading(10), new Heading(15));
        assert ! new Heading(20).isNear(new Heading(50), new Heading(15));
    }
    public void testIsNearCircular(){
        assert new Heading(20).isNear(new Heading(350), new Heading(35)) ;
        assert new Heading(350).isNear(new Heading(10), new Heading(25)) ;
        assert ! new Heading(350).isNear(new Heading(10), new Heading(15)) ;
    }
    public void testNearestSideSimple(){
        assert (new Heading(10).nearestSide(new Heading(20)) == LeftRight.RIGHT);
        assert (new Heading(20).nearestSide(new Heading(10)) == LeftRight.LEFT);
    }
    public void testNearestSideCircular(){
        assert (new Heading(350).nearestSide(new Heading(20)) == LeftRight.RIGHT);
        assert (new Heading(20).nearestSide(new Heading(350)) == LeftRight.LEFT);
    }
    public void testAddSimple(){
        assert Heading.add(new Heading(10),new Heading(10)).
            isNear(new Heading(20), new Heading(0));
        assert Heading.add(new Heading(50),new Heading(30),LeftRight.LEFT).
            isNear(new Heading(20), new Heading(0));
    }
    public void testAddCircular(){
        assert Heading.add(new Heading(350),new Heading(350)).
            isNear(new Heading(340), new Heading(0));
        assert Heading.add(new Heading(340),new Heading(330),LeftRight.LEFT).
            isNear(new Heading(10), new Heading(0));
    }
    public void testHeadingFromPosition(){
        Position origin = new Position(50,50);
        Position destination = new Position(75,75);
        assert new Heading(origin,destination).isNear(new Heading(135), new Heading(1));

        destination = new Position(75,25);
        assert new Heading(origin,destination).isNear(new Heading(45), new Heading(1));

        destination = new Position(25,25);
        assert new Heading(origin,destination).isNear(new Heading(315), new Heading(1));

        destination = new Position(25,75);
        assert new Heading(origin,destination).isNear(new Heading(225), new Heading(1));

        destination = new Position(25,50);
        assert new Heading(origin,destination).isNear(new Heading(270), new Heading(1));

        destination = new Position(50,75);
        assert new Heading(origin,destination).isNear(new Heading(180), new Heading(1));

        destination = new Position(75,50);
        assert new Heading(origin,destination).isNear(new Heading(90), new Heading(1));

        destination = new Position(50,25);
        assert new Heading(origin,destination).isNear(new Heading(0), new Heading(1));
    }

    public void testSin(){
    assert isNear(new Heading(0).sin(),1);
    assert isNear(new Heading(60).sin(),0.5);
    assert isNear(new Heading(180).sin(),-1);
    assert isNear(new Heading(300).sin(),0.5);
    assert isNear(new Heading(270).sin(),0);
    }
    public void testCos(){
    assert isNear(new Heading(330).cos(),-0.5);
    assert isNear(new Heading(0).cos(),0);
    assert isNear(new Heading(270).cos(),-1);
    assert isNear(new Heading(90).cos(),1);
    assert isNear(new Heading(210).cos(),-0.5);
    }
}
