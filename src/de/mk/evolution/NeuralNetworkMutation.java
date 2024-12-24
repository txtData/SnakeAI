package de.mk.evolution;

import de.mk.environment.Snake;
import de.mk.brain.NNBrain;
import de.mk.brain.nn.*;

import java.util.Random;

/**
 * Implements the mutations in snake brains that happen from one generation to the next.
 * Modifies neurons in the neural network of the snakes brain to achieve this.
 */
public class NeuralNetworkMutation {

    /**
     * Mutates s snake.
     * @param snake The snake to which to apply the mutation.
     * @param mutationRate The chance of a mutation happening to each neuron in the brain. (0.1 represents a 10% chance.)
     * @param initializer Initializer used to set the new values of a mutated weight.
     * @return The number of neurons that have been mutated. -1 if the attempt failed.
     */
    public static int mutate(Snake snake, double mutationRate, RandomInitializer initializer){
        if (snake.snakeBrain instanceof NNBrain){
            return NeuralNetworkMutation.mutate(((NNBrain) snake.snakeBrain).getNeuralNetwork(), mutationRate, initializer);
        }
        return -1;
    }

    private static int mutate(FeedforwardNeuralNetwork nn, double mutationRate, RandomInitializer initializer){
        if (mutationRate==0.0) return 0;
        int weights = nn.countWeights();
        int mutations = (int)(weights * mutationRate)+1;
        int[] ids = new Random().ints(0, weights-1).distinct().limit(mutations).toArray();
        for (int id : ids){
            if (Math.random()*2.0>0.5) {
                nn.modifyWeight(id, initializer.getRandom());
            }else{
                nn.modifyBias(id, initializer.getRandom());
            }

        }
        return mutations;
    }

}
