package de.mk.environment;

public class Coordinates {
    public int x;
    public int y;

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coordinates clone(){
        return new Coordinates(x,y);
    }

    public String toString(){
        return "["+x+","+y+"]";
    }
}
