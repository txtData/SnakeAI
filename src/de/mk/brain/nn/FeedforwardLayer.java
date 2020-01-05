package de.mk.brain.nn;

public class FeedforwardLayer implements ILayer {
    private int inputs;
    private int outputs;
    private IActivationFunction activationFunction;

    public INeuron[] neurons;

    public FeedforwardLayer(int inputs, int neurons, IActivationFunction activationFunction){
        this.inputs = inputs;
        this.outputs = neurons;
        this.activationFunction = activationFunction;

        this.neurons = new INeuron[neurons];
        for (int i = 0; i < neurons; i++) {
            if (this.activationFunction!=null) {
                this.neurons[i] = new LinearNeuron(inputs, (IActivationFunction) activationFunction.cloneEmpty());
            }else{
                this.neurons[i] = new LinearNeuron(inputs);
            }
        }
    }

    public FeedforwardLayer cloneEmpty(){
        return new FeedforwardLayer(this.inputs, this.outputs, this.activationFunction);
    }

    public FeedforwardLayer copyThis(){
        FeedforwardLayer copy = this.cloneEmpty();
        for (int i=0; i<this.neurons.length ;i++){
            copy.neurons[i] = (INeuron) this.neurons[i].copyThis();
        }
        return copy;
    }

    public double[] compute(double[] inputs) {
        double[] outputs = new double[neurons.length];
        for (int i = 0; i < neurons.length; i++) {
            outputs[i] = neurons[i].compute(inputs);
        }
        return outputs;
    }

    public INeuron[] getNeurons(){
        return this.neurons;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<neurons.length; i++){
            sb.append("Neuron_").append(i+1).append(neurons[i]).append("\n");
        }
        return sb.toString();
    }

}
