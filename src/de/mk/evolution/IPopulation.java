package de.mk.evolution;

import de.mk.environment.Snake;

/**
 * Represents a population of snakes that is one generation.
 * Contains methods to create the first generation and to evolve further generations.
 */
public interface IPopulation {

    void createFirstGeneration();
    int evolve();
    double getPopulationFitness();
    Snake getFittestSnake();

}
