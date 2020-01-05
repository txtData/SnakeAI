package de.mk.evolution;

import de.mk.environment.Snake;
import de.mk.brain.NNBrain;
import de.mk.brain.SnakeVision;
import de.mk.brain.nn.RandomInitializer;
import de.mk.environment.PlayingField;

import java.util.ArrayList;
import java.util.List;

public class MutatingPopulation implements IPopulation{

    private static int POPULATION_SIZE = 500;
    private static double MUTATION_RATE = 0.005;
    private static int MIN_EVALUATION_RUNS = 50;
    private static int MAX_EVALUATION_RUNS = 100;

    private static int USE_BEST_X_FOR_FITNESS = 50;

    private Snake prototypeSnake;
    private List<Snake> snakes;
    private int generation = 0;
    private double populationFitness;


    public MutatingPopulation(){
    }

    public void createFirstGeneration(){
        this.prototypeSnake = new Snake("Prototpye", new NNBrain(new SnakeVision()));
        System.out.print("Creating first generation...");
        this.snakes = this.createFirstGeneration(POPULATION_SIZE, 3, 500);
        System.out.println(" Done.");
        computeSnakesFitness(MIN_EVALUATION_RUNS);
        this.sortSnakes();
        this.populationFitness = this.computePopulationFitness();
        this.generation = 1;
    }

    public int evolve(){
        this.generation++;
        List<Snake> newSnakes = new ArrayList<>();
        for (int i=0; i<POPULATION_SIZE/10; i++) {
            Snake snake = snakes.get(i);
            String id = (i + 1) + "";
            newSnakes.add(this.mutate(snake, id, 0.0));
            newSnakes.add(this.mutate(snake, id, 0.0));
            newSnakes.add(this.mutate(snake, id, MUTATION_RATE / 10.0));
            newSnakes.add(this.mutate(snake, id, MUTATION_RATE / 5.0));
            newSnakes.add(this.mutate(snake, id, MUTATION_RATE / 2.0));
            newSnakes.add(this.mutate(snake, id, MUTATION_RATE));
            newSnakes.add(this.mutate(snake, id, MUTATION_RATE));
            newSnakes.add(this.mutate(snake, id, MUTATION_RATE * 2.0));
            newSnakes.add(this.mutate(snake, id, MUTATION_RATE * 5.0));
            newSnakes.add(this.mutate(snake, id, MUTATION_RATE * 10.0));
        }
        this.snakes = newSnakes;
        int evalRuns = Math.min(Math.max(generation, MIN_EVALUATION_RUNS), MAX_EVALUATION_RUNS);
        computeSnakesFitness(MIN_EVALUATION_RUNS);
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
        return this.toString(10);
    }

    public String toString(int top){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Population Fitness: %6.3f\t\t Best %d:", this.populationFitness, top)).append(" [");
        int i = 0;
        for (Snake snake : this.snakes){
            sb.append(String.format("%5.02f", snake.fitness)).append(", ");
            if (i>top) break;
            i++;
        }
        sb.delete(sb.length()-2, sb.length());
        sb.append("]");
        i = 0;
        sb.append("\t\tGene profiles: [");
        for (Snake snake : this.snakes){
            sb.append(String.format("%1$4s",snake.name)).append(", ");
            if (i>top) break;
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
        int mutations = NNMutation.mutate(clone, mutationRate, new RandomInitializer());
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
            playingField.setSnake(new Snake("0:0", prototypeSnake.snakeBrain.initializeFirstGenerationBrain()));
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
            if (i<=USE_BEST_X_FOR_FITNESS) {
                average += snake.fitness;
            }
            i++;
        }
        int div = Math.min(this.snakes.size(), USE_BEST_X_FOR_FITNESS);
        return average / div;
    }

}
