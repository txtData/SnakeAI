package de.mk.evolution;

import de.mk.environment.Snake;
import de.mk.brain.NNBrain;
import de.mk.brain.nn.*;

import java.util.Random;

public class NNMutation {

    public static int mutate(Snake snake, double mutationRate, RandomInitializer initializer){
        if (snake.snakeBrain instanceof NNBrain){
            return NNMutation.mutate(((NNBrain) snake.snakeBrain).neuralNetwork, mutationRate, initializer);
        }
        return -1;
    }

    public static int mutate(FeedforwardNeuralNetwork nn, double mutationRate, RandomInitializer initializer){
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
