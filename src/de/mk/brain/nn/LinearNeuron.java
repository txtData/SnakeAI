package de.mk.brain.nn;

public class LinearNeuron implements INeuron {

    private IActivationFunction activationFunction;
    private  double[]            weights;
    private  double              bias;


    public LinearNeuron(int inputs, IActivationFunction activationFunction){
        this.activationFunction = activationFunction;
        this.weights = new double[inputs];
    }

    public LinearNeuron(int inputs){
        this.weights = new double[inputs];
    }

    public double[] getWeights(){
        return this.weights;
    }

    public void setWeights(double[] weights){
        for (int i=0; i<weights.length; i++){
            this.weights[i] = weights[i];
        }
    }

    public void setWeight(int id, double weight){
        this.weights[id] = weight;
    }

    public double getBias(){
        return this.bias;
    }

    public void setBias(double bias){
        this.bias = bias;
    }

    public LinearNeuron cloneEmpty(){
        if (this.activationFunction!=null) {
            return new LinearNeuron(this.weights.length, (IActivationFunction) activationFunction.cloneEmpty());
        }else{
            return new LinearNeuron(this.weights.length);
        }
    }

    public LinearNeuron copyThis(){
        LinearNeuron copy = this.cloneEmpty();
        for (int i=0; i<this.weights.length ;i++){
            copy.weights[i] = this.weights[i];
        }
        copy.bias = this.bias;
        return copy;
    }

    public double compute(double[] inputs) {
        double result = 0;
        for (int i = 0; i < weights.length; i++) {
            result += this.weights[i] * inputs[i];
        }
        result += this.bias;
        if (this.activationFunction == null) {
            return result;
        }else{
            return this.activationFunction.compute(result);
        }
    }

    public int getInputCount(){
        return this.weights.length;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<this.weights.length; i++){
            sb.append("[").append(String.format("%+.3f",weights[i])).append("]");
        }
        sb.append("b=").append(String.format("%+.3f",bias));
        return sb.toString();
    }


}
