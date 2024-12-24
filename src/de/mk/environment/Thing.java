package de.mk.environment;

public class Thing {

    public int thing;

    public static final int BACKGROUND = 0;
    public static final int WALL = 1;
    public static final int FOOD = 2;
    public static final int SNAKE_BODY = 3;
    public static final int SNAKE_HEAD = 4;

    public Thing(int i){
        this.thing = i;
    }
}
