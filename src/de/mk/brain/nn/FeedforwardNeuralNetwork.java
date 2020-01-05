package de.mk.brain.nn;

import java.util.Arrays;

public class FeedforwardNeuralNetwork {

    private int inputCount;

    public double[] inputs;
    public ILayer[] layers;

    public FeedforwardNeuralNetwork(int inputCount){
        this.inputCount = inputCount;
        this.inputs = new double[inputCount];
        this.layers = new ILayer[0];
    }

    public void initialize(IInitializer initializer){
        initializer.initialize(this);
    }

    public FeedforwardNeuralNetwork copyThis(){
        FeedforwardNeuralNetwork copy = new FeedforwardNeuralNetwork(inputCount);
        copy.layers = new ILayer[this.layers.length];
        for (int i=0; i<this.layers.length ;i++){
            copy.layers[i] = (ILayer)this.layers[i].copyThis();
        }
        return copy;
    }

    public void addLayer(ILayer layer){
        layers = Arrays.copyOf(layers, layers.length+1);
        layers[layers.length-1] = layer;
    }

    public double[] computeOutput(double[] inputs){
        double[] outputs = inputs;
        for (ILayer layer: layers){
            outputs = layer.compute(outputs);
        }
        return outputs;
    }

    public int countWeights(){
        int weights = 0;
        for (int l=0; l<layers.length; l++) {
            INeuron neurons[] = this.layers[l].getNeurons();
            for (int n = 0; n < neurons.length; n++) {
                LinearNeuron ln = (LinearNeuron) neurons[n];
                weights += ln.getWeights().length;
            }
        }
        return weights;
    }

    public INeuron modifyWeight(int id, double value){
        int weightId = 0;
        for (int l=0; l<layers.length; l++) {
            INeuron neurons[] = this.layers[l].getNeurons();
            for (int n = 0; n < neurons.length; n++) {
                INeuron ln = neurons[n];
                if (weightId + ln.getWeights().length > id){
                    ln.setWeight(id-weightId, value);
                    return ln;
                }
                weightId += ln.getWeights().length;
            }
        }
        return null;
    }

    /**
     * Modifies the bias of the neuron belonging to the specified weight.
     */
    public boolean modifyBias(int id, double biasValue){
        int weightId = 0;
        for (int l=0; l<layers.length; l++) {
            INeuron neurons[] = this.layers[l].getNeurons();
            for (int n = 0; n < neurons.length; n++) {
                LinearNeuron ln = (LinearNeuron) neurons[n];
                if (weightId + ln.getWeights().length > id){
                    ln.setBias(biasValue);
                    return true;
                }
                weightId += ln.getWeights().length;
            }
        }
        return false;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<layers.length; i++){
            sb.append("Layer ").append(i+1).append(":\n").append(layers[i]).append("\n");
        }
        return sb.toString();
    }


}
