package de.mk.evolution;

import de.mk.brain.NNBrain;
import de.mk.brain.SnakeVision;
import de.mk.environment.Snake;
import de.mk.brain.nn.RandomInitializer;
import de.mk.environment.PlayingField;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a population of snakes/one generation of snakes.
 */
public class MutatingPopulation implements IPopulation{

    //** The number of snakes the population/generation. **/
    private static final int POPULATION_SIZE = 500;

    //** Fraction of genes/neurons that will be mutated in each generation. **/
    private static final double MUTATION_RATE = 0.005;

    //** New snakes that are added in each generation. **//
    private static final double NEW_SNAKES_PER_POPULATION = 0;

    //** How many times the performance of a snake in the game is tested to determine its fitness. **//
    private static final int EVALUATION_RUNS_PER_SNAKE = 25;

    //** Number of snakes that are used to compute the fitness of the whole population. **//
    private static final int USE_BEST_X_SNAKES_TO_COMPUTE_POPULATION_FITNESS = 50;

    private Snake prototypeSnake;
    private List<Snake> snakes;
    private int generation = 0;
    private double populationFitness;


    public MutatingPopulation(){
    }

    /**
     * Creates a first generation of snakes, whose brains are randomly initialized.
     * (Typically, these snakes are rather stupid.)
     **/
    public void createFirstGeneration(){
        this.prototypeSnake = new Snake("Prototpye", new NNBrain(new SnakeVision()));
        System.out.print("Creating first generation...");
        this.snakes = this.createFirstGeneration(POPULATION_SIZE, 3, 100);
        System.out.println(" Done.");
        computeSnakesFitness(EVALUATION_RUNS_PER_SNAKE);
        this.sortSnakes();
        this.populationFitness = this.computePopulationFitness();
        this.generation = 1;
    }

    public int evolve(){
        double[] mutation_multipliers = {.0, .0, .1, .2, .5, 1.0, 1.0, 2.0, 5.0, 10.0};
        this.generation++;
        List<Snake> newSnakes = new ArrayList<>();
        for (int i=0; i<POPULATION_SIZE/10; i++) {
            Snake snake = snakes.get(i);
            String id = (i + 1) + "";
            for (double mutation_multiplier : mutation_multipliers){
                newSnakes.add(this.mutate(snake, id, MUTATION_RATE * mutation_multiplier));
                if (newSnakes.size()>=POPULATION_SIZE-NEW_SNAKES_PER_POPULATION) break;
            }
            if (newSnakes.size()>=POPULATION_SIZE-NEW_SNAKES_PER_POPULATION) break;
        }
        while (newSnakes.size()<POPULATION_SIZE){
            Snake snake = this.createSnakeFitterThan(3, 500);
            if (snake!=null){
                newSnakes.add(snake);
            }
        }
        this.snakes = newSnakes;
        computeSnakesFitness(EVALUATION_RUNS_PER_SNAKE);
        this.sortSnakes();
        this.populationFitness = this.computePopulationFitness();
        return generation+1;
    }

    public double getPopulationFitness(){
        return this.populationFitness;
    }

    public Snake getFittestSnake(){
        return this.snakes.get(0);
    }

    public String toString(){
        return this.toString(5);
    }

    /**
     * Returns a string that helps to understand the fitness of the current population.
     * @param include_best_x_snakes The number of snakes to include.
     * @return A string that helps to understand the fitness of the current population.
     */
    public String toString(int include_best_x_snakes){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Population Fitness: %6.3f\t\t Best %d:", this.populationFitness, include_best_x_snakes)).append(" [");
        int i = 0;
        for (Snake snake : this.snakes){
            sb.append(String.format("%5.02f", snake.fitness)).append(", ");
            if (i>include_best_x_snakes) break;
            i++;
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("]");
        i = 0;
        sb.append("\t\tGene profiles: [");
        for (Snake snake : this.snakes){
            sb.append(String.format("%1$4s",snake.name)).append(", ");
            if (i>include_best_x_snakes) break;
            i++;
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("]");
        return sb.toString();
    }

    private List<Snake> createFirstGeneration(int count, int score, int maxTries){
        List<Snake> snakes = new ArrayList<>();
        while (snakes.size()<count){
            Snake snake = this.createSnakeFitterThan(score, maxTries);
            if (snake!=null){
                snakes.add(snake);
                snake.fitness = snake.getScore();
            }
        }
        return snakes;
    }

    private Snake mutate(Snake parent, String name, double mutationRate){
        Snake clone = parent.clone(name, true);
        int mutations = NeuralNetworkMutation.mutate(clone, mutationRate, new RandomInitializer());
        clone.name += ":"+mutations;
        return clone;
    }

    private void sortSnakes(){
        this.snakes.sort((o1, o2) -> ((Double)o2.fitness).compareTo(o1.fitness));
    }

    private Snake createSnakeFitterThan(int minScore, int maxTries){
        int score = 0;
        int tries = 0;
        int bestScore = -1;
        Snake snake = null;
        while(minScore>=score && tries<maxTries){
            PlayingField playingField = new PlayingField();
            playingField.setSnake(new Snake("NEW", prototypeSnake.snakeBrain.initializeFirstGenerationBrain()));
            playingField.moveSnakeUntilDead();
            if (playingField.snake.getScore()>bestScore){
                snake = playingField.snake;
                bestScore = playingField.snake.getScore();
            }
            score = snake.getScore();
            tries++;
        }
        return snake;
    }

    private void computeSnakesFitness(int runs){
        for (Snake snake : this.snakes){
            snake.computeFitness(runs);
        }
    }

    private double computePopulationFitness(){
        double average = 0;
        int i=1;
        for (Snake snake : this.snakes){
            if (i<= USE_BEST_X_SNAKES_TO_COMPUTE_POPULATION_FITNESS) {
                average += snake.fitness;
            }
            i++;
        }
        int div = Math.min(this.snakes.size(), USE_BEST_X_SNAKES_TO_COMPUTE_POPULATION_FITNESS);
        return average / div;
    }

}
