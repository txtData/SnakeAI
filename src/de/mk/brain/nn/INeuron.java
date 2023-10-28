package de.mk.brain.nn;

public interface INeuron extends IPrototype{

    double compute(double[] inputs);
    int getInputCount();
    double[] getWeights();
    void setWeights(double[] weights);
    void setWeight(int id, double weight);

}
