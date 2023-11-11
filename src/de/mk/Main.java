package de.mk;

import de.mk.evolution.IPopulation;
import de.mk.evolution.MutatingPopulation;
import de.mk.environment.PlayingFieldWindow;
import de.mk.environment.Snake;

/**
 * Main entry point to the application.
 */
public class Main {

    //** Number of generations after which the program will terminate. **//
    private static final int MAX_GENERATIONS = 5000;

    public static void main(String[] args) {
        PlayingFieldWindow playingFieldWindow = new PlayingFieldWindow();
        playingFieldWindow.showGame();
        IPopulation population = new MutatingPopulation();
        population.createFirstGeneration();
        Snake fittestSnake;
        int generation = 1;
        while (generation<=MAX_GENERATIONS){
            System.out.println("Gen "+generation+"\t"+population.toString());
            generation = population.evolve();
            fittestSnake = population.getFittestSnake();
            if (!playingFieldWindow.isRunning()) {
                playingFieldWindow.placeSnake(fittestSnake, generation, population.getPopulationFitness());
            }
        }
    }
}
