package de.mk.brain.nn;

public interface IInitializer extends IPrototype{

    void initialize(INeuron neuron);
    void initialize(ILayer neuron);
    void initialize(FeedforwardNeuralNetwork network);

}
