package de.mk.brain;

import de.mk.environment.Coordinates;
import de.mk.environment.Direction;
import de.mk.environment.PlayingField;
import de.mk.environment.Snake;
import de.mk.environment.Thing;

/*
 * Implements the snake's vision. The snake can look in all four directions, and will see for each direction how far
 * away the wall, the snake's body or the food is.
 */
public class SnakeVision implements ISnakeSensors {

    private static final int sensorArraySize = 12;

    public SnakeVision(){}

    public int getSensorArraySize(){
        return sensorArraySize;
    }

    public double[] getSensorSnapshot(PlayingField playingField, Snake snake){
        double[] results = new double[sensorArraySize];
        for (int i=1; i<=3; i++){
            for (int j=0; j<4; j++){
                results[(i-1)*4+j] = SnakeVision.lookFor(playingField, snake.getHead(), new Direction(j) , new Thing(i));
            }
        }
        return results;
    }

    protected static double lookFor(PlayingField playingField,  Coordinates position, Direction direction, Thing lookFor){
        int lookingAt = Thing.BACKGROUND;
        double distance = 0;
        while(lookingAt==Thing.BACKGROUND){
            position = direction.apply(position);
            lookingAt = playingField.getAt(position);
            distance++;
        }
        if (lookingAt==lookFor.thing) return 1.0/distance;
        return -1.0;
    }

    public static String toString(double[] vision){
        StringBuilder sb = new StringBuilder();
        for (double v : vision) {
            sb.append("[").append(String.format("%+.3f", v)).append("]");
        }
        return sb.toString();
    }

}
