package de.mk.brain.nn;

public class LeakyReLu implements IActivationFunction {

    public double compute(double input){
        return Math.max(input*0.00001, input);
    }

    public IActivationFunction cloneEmpty(){
        return new LeakyReLu();
    }
    public IActivationFunction copyThis() {
        return this.cloneEmpty();
    }

}
