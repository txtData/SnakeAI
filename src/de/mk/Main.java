package de.mk;

import de.mk.evolution.IPopulation;
import de.mk.evolution.MutatingPopulation;
import de.mk.environment.PlayingFieldWindow;
import de.mk.environment.Snake;

public class Main {

    private static int MAX_GENERATIONS = 5000;
    private static boolean SHOW_ALWAYS = false;
    private static int SHOW_GAME_AFTER_GENERATIONS = 10;

    public static void main(String[] args) {
        new Main();
    }

    private Main(){
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
            if (!playingFieldWindow.isRunning() && (generation%SHOW_GAME_AFTER_GENERATIONS==0 || SHOW_ALWAYS)) {
                playingFieldWindow.placeSnake(fittestSnake, generation, population.getPopulationFitness());
            }
        }
    }
}
