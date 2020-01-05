package de.mk.brain.nn;

public class RandomInitializer implements IInitializer{

    private double from = -1;
    private double to   = 1;

    public RandomInitializer(){}

    public RandomInitializer(double from, double to){
        this.from = from;
        this.to = to;
    }

    public RandomInitializer cloneEmpty(){
        return new RandomInitializer(this.from, this.to);
    }
    public RandomInitializer copyThis(){
        return this.cloneEmpty();
    }

    public double getRandom(){
        return (Math.random()*(to-from))+from;
    }

    public void initialize(INeuron neuron){
        double[] weights = new double[neuron.getInputCount()];
        for (int i=0; i<neuron.getInputCount(); i++){
            weights[i] = getRandom();
        }
        neuron.setWeights(weights);
    }

    public void initialize(ILayer layer){
        for(int i = 0; i<layer.getNeurons().length; i++){
            this.initialize(layer.getNeurons()[i]);
        }
    }

    public void initialize(FeedforwardNeuralNetwork network){
        for(int i = 0; i<network.layers.length; i++){
            this.initialize(network.layers[i]);
        }
    }

    public void initialize(FeedforwardNeuralNetwork network, double from, double to){
        this.from = from;
        this.to = to;
        for(int i = 0; i<network.layers.length; i++){
            this.initialize(network.layers[i]);
        }
    }
}
