package de.mk.brain;

import de.mk.environment.Coordinates;
import de.mk.environment.Direction;
import de.mk.environment.PlayingField;
import de.mk.environment.Snake;
import de.mk.environment.Thing;

import java.util.Arrays;

/**
 * Implementation of a snake's brain that always goes straight for the food if it is in sight.
 */
public class SimpleBrain implements ISnakeBrain{

    public ISnakeSensors snakeSensors;

    private Direction direction = null;

    public SimpleBrain(ISnakeSensors snakeSensors){
        this.snakeSensors = snakeSensors;
    }

    public ISnakeBrain initializeFirstGenerationBrain(){
        return new SimpleBrain(this.snakeSensors);
    }

    public SimpleBrain clone(){
        return new SimpleBrain(this.snakeSensors);
    }

    public Coordinates planMove(Snake snake, PlayingField playingField) {
        if (this.direction==null){
            this.direction = Direction.randomDirection();
        }
        Direction d = this.lookForFood(playingField, snake);
        if (d!=null) this.direction = d;
        boolean goodPlan = false;
        int i = 0;
        while (!goodPlan && i<1000) {
            Coordinates plan = direction.apply(snake.getHead());
            if (playingField.is(plan, Thing.BACKGROUND) || playingField.is(plan, Thing.FOOD)){
                goodPlan = true;
            }else{
                this.direction = Direction.randomDirection();
            }
            i++;
        }
        return direction.apply(snake.getHead());
    }

    public Direction lookForFood(PlayingField playingField, Snake snake){
        double[] sensors = this.snakeSensors.getSensorSnapshot(playingField, snake);
        double[] foodArray = Arrays.copyOfRange(sensors,4, 8);
        System.out.println(SnakeVision.toString(sensors));
        if (foodArray[0]>0) return new Direction(Direction.UP);
        if (foodArray[1]>0) return new Direction(Direction.DOWN);
        if (foodArray[2]>0) return new Direction(Direction.LEFT);
        if (foodArray[3]>0) return new Direction(Direction.RIGHT);
        return null;
    }
}
