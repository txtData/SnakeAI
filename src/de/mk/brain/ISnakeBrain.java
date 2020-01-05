package de.mk.brain;

import de.mk.environment.Coordinates;
import de.mk.environment.PlayingField;
import de.mk.environment.Snake;

public interface ISnakeBrain {
    ISnakeBrain initializeFirstGenerationBrain();
    ISnakeBrain clone();
    Coordinates planMove(Snake snake, PlayingField playingField);

}
