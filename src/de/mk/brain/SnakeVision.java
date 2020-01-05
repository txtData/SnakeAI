package de.mk.brain;

import de.mk.environment.Coordinates;
import de.mk.environment.Direction;
import de.mk.environment.PlayingField;
import de.mk.environment.Snake;
import de.mk.environment.Thing;

public class SnakeVision implements ISnakeSensors {

    private static int sensorArraySize = 12;

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

    private static double lookFor(PlayingField playingField,  Coordinates position, Direction direction, Thing lookFor){
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
        for (int i=0; i<vision.length; i++){
            sb.append("[").append(String.format("%+.3f",vision[i])).append("]");
        }
        return sb.toString();
    }

}
