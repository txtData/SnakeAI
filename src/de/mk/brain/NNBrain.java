package de.mk.brain;

import de.mk.environment.Coordinates;
import de.mk.environment.Direction;
import de.mk.environment.PlayingField;
import de.mk.environment.Snake;
import de.mk.brain.nn.*;

 /**
 * Implementation of a snake's brain using a three-layer feedforward neural network.
 */
public class NNBrain implements ISnakeBrain {

    private final ISnakeSensors snakeSensors;
    private FeedforwardNeuralNetwork neuralNetwork;

    /**
     * Constructs an NNBrain with the specified snake sensors.
     * @param snakeSensors the snake sensors
     */
    public NNBrain(ISnakeSensors snakeSensors) {
        this.snakeSensors = snakeSensors;
        this.neuralNetwork = new FeedforwardNeuralNetwork(this.snakeSensors.getSensorArraySize());
        this.neuralNetwork.addLayer(new FeedforwardLayer(this.snakeSensors.getSensorArraySize(), 30, null));
        this.neuralNetwork.addLayer(new FeedforwardLayer(30, 12, null));
        this.neuralNetwork.addLayer(new FeedforwardLayer(12, 4, null));
        this.neuralNetwork.initialize(new RandomInitializer());
    }

    /**
     * Initializes the first generation brain.
     * @return a new NNBrain instance
     */
    public NNBrain initializeFirstGenerationBrain() {
        return new NNBrain(this.snakeSensors);
    }

    /**
     * Creates a clone of this NNBrain.
     * @return a cloned NNBrain instance
     */
    @Override
    public NNBrain clone() {
        NNBrain clone = new NNBrain(this.snakeSensors);
        clone.neuralNetwork = this.neuralNetwork.copyThis();
        return clone;
    }

    /**
     * Plans the next move for the snake based on the current state of the playing field.
     * @param snake the snake
     * @param playingField the playing field
     * @return the coordinates of the next move
     */
    @Override
    public Coordinates planMove(Snake snake, PlayingField playingField) {
        double[] sensors = this.snakeSensors.getSensorSnapshot(playingField, snake);
        double[] actions = this.neuralNetwork.computeOutput(sensors);
        Direction direction = this.getDirection(actions);
        return direction.apply(snake.getHead());
    }

    /**
     * Determines the direction based on the neural network's output.
     * @param actions the neural network's output
     * @return the direction
     */
    private Direction getDirection(double[] actions) {
        int bestIndex = 0;
        double highest = actions[0];
        for (int i = 1; i < actions.length; i++) {
            if (actions[i] > highest) {
                highest = actions[i];
                bestIndex = i;
            }
        }
        return new Direction(bestIndex);
    }

    /**
     * Returns the neural network.
     * @return the neural network
     */
    public FeedforwardNeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }
}
