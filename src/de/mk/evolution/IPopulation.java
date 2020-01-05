package de.mk.evolution;

import de.mk.environment.Snake;

public interface IPopulation {

    void createFirstGeneration();
    int evolve();
    double getPopulationFitness();
    Snake getFittestSnake();

}
