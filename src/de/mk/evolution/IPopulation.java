package de.mk.evolution;

import de.mk.environment.Snake;

/**
 * Represents a population of snakes that is one generation.
 * Contains methods to create the first generation and to evolve further generations.
 */
public interface IPopulation {

    //** Creates a first generation of snakes. **//
    void createFirstGeneration();

    //** Evolves the population to the next generation. Returns the current generation count. *+//
    int evolve();

    //** Returns this populations fitness. *+//
    double getPopulationFitness();

    //** returns the fittest snake. **//
    Snake getFittestSnake();

}
