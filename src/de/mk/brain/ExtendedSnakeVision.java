package de.mk.brain;

import de.mk.environment.Direction;
import de.mk.environment.PlayingField;
import de.mk.environment.Snake;
import de.mk.environment.Thing;

/*
 * Alternative version of the snake's vision. The snake also knows about the absolute positions of its head and the food.
 */
public class ExtendedSnakeVision extends SnakeVision{

    private static final int sensorArraySize = 16;

    public ExtendedSnakeVision(){}

    public int getSensorArraySize(){
        return sensorArraySize;
    }

    public double[] getSensorSnapshot(PlayingField playingField, Snake snake){
        double[] results = new double[sensorArraySize];
        results[0] = playingField.snake.getHead().x/(double)playingField.getWidth();
        results[1] = playingField.snake.getHead().y/(double)playingField.getHeight();
        results[2] = playingField.getFoodPosition()[0]/(double)playingField.getWidth();
        results[3] = playingField.getFoodPosition()[1]/(double)playingField.getHeight();
        for (int j=0; j<4; j++) {
            results[j+4] = SnakeVision.lookFor(playingField, snake.getHead(), new Direction(j), new Thing(Thing.SNAKE_BODY));
        }
        for (int j=0; j<4; j++) {
            results[j+8] = SnakeVision.lookFor(playingField, snake.getHead(), new Direction(j), new Thing(Thing.WALL));
        }
        for (int j=0; j<4; j++) {
            results[j+12] = SnakeVision.lookFor(playingField, snake.getHead(), new Direction(j), new Thing(Thing.FOOD));
        }
        return results;
    }


}
