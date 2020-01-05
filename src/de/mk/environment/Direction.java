package de.mk.environment;

public class Direction {

    public int direction = -1;

    public static int UP = 0;
    public static int DOWN = 1;
    public static int LEFT = 2;
    public static int RIGHT = 3;

    public Direction(int direction){
        this.direction = direction;
    }

    public static Direction randomDirection(){
        return new Direction((int) (Math.random() * 4));
    }

    public Coordinates apply(Coordinates coordinates){
        Coordinates result = coordinates.clone();
        if (this.direction==UP){
            result.x--;
        }else if (this.direction==DOWN){
            result.x++;
        }else if (this.direction==LEFT){
            result.y--;
        }else if (this.direction==RIGHT){
            result.y++;
        }
        return result;
    }

    public String toString(){
        return this.direction+"";
    }

}
