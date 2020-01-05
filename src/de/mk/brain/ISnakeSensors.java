package de.mk.brain;

import de.mk.environment.PlayingField;
import de.mk.environment.Snake;

public interface ISnakeSensors {

    /**
     * Returns the size of the sensor array that will be returned by getSensorSnapshot every time.
     */
    int getSensorArraySize();

    /**
     * Returns the snake's sensor input for a given point in a run.
     */
    double[] getSensorSnapshot(PlayingField playingField, Snake snake);
}
