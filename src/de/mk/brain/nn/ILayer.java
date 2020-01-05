package de.mk.brain.nn;

public interface ILayer extends IPrototype{
    double[] compute(double[] inputs);
    INeuron[] getNeurons();
}
