package de.mk.brain;

import de.mk.environment.Coordinates;
import de.mk.environment.Direction;
import de.mk.environment.PlayingField;
import de.mk.environment.Snake;

public class RandomBrain implements ISnakeBrain{

    public ISnakeBrain initializeFirstGenerationBrain(){
        return new RandomBrain();
    }

    public RandomBrain clone(){
        return new RandomBrain();
    }

    public Coordinates planMove(Snake snake, PlayingField playingField) {
        Coordinates head = snake.body.get(0);
        Direction direction = Direction.randomDirection();
        return direction.apply(head);
    }

}
