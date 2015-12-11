package sim.util;

public enum VerticalDirection{
    UP("↑"),
    STABLE("="),
    DOWN("↓");

    private String symbol;
    VerticalDirection(String sym){
        symbol = sym;
    }
    public String toString(){
        return symbol;
    }
}

