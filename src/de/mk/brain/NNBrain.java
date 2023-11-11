package de.mk.brain;

import de.mk.environment.Coordinates;
import de.mk.environment.Direction;
import de.mk.environment.PlayingField;
import de.mk.environment.Snake;
import de.mk.brain.nn.*;

/**
 * Implementation of a snake's brain using a three layer feedforward neural network.
 */
public class NNBrain implements ISnakeBrain{

    public ISnakeSensors snakeSensors;
    public FeedforwardNeuralNetwork neuralNetwork;

    public NNBrain(ISnakeSensors snakeSensors){
        this.snakeSensors = snakeSensors;
        this.neuralNetwork = new FeedforwardNeuralNetwork(this.snakeSensors.getSensorArraySize());
        this.neuralNetwork.addLayer(new FeedforwardLayer(this.snakeSensors.getSensorArraySize(), 30, null));
        this.neuralNetwork.addLayer(new FeedforwardLayer(30, 12, null));
        this.neuralNetwork.addLayer(new FeedforwardLayer(12,4, null));
        this.neuralNetwork.initialize(new RandomInitializer());
    }

    public NNBrain initializeFirstGenerationBrain(){
        return new NNBrain(this.snakeSensors);
    }

    public NNBrain clone(){
        NNBrain clone = new NNBrain(this.snakeSensors);
        clone.neuralNetwork = this.neuralNetwork.copyThis();
        return clone;
    }

    public Coordinates planMove(Snake snake, PlayingField playingField) {
        double[] sensors = this.snakeSensors.getSensorSnapshot(playingField, snake);
        double[] actions = this.neuralNetwork.computeOutput(sensors);
        Direction direction = this.getDirection(actions);
        return direction.apply(snake.getHead());
    }

    private Direction getDirection(double[] actions){
        Direction direction = new Direction(Direction.UP);
        double highest = actions[0];
        for (int i=1; i<actions.length; i++){
            if (actions[i]>highest){
                highest = actions[i];
                direction = new Direction(i);
            }
        }
        return direction;
    }

}
