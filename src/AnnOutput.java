import java.util.ArrayList;

public class AnnOutput extends AnnNeuron {

    // Svoriu atnaujinimas
    public void backpropagate(double target) {
        System.out.println("Backpropagating output");
        double o1 = this.output();
        double dEto1 = target - o1;
        double do1n1 = o1 * (1 - o1);
        this.n_weights = new ArrayList<>(this.weights);
        for(int i = 0; i < this.inputs.size(); i++) {
            this.n_weights.set(i, ( this.n_weights.get(i) + dEto1 * do1n1 * this.inputs.get(i).output() ));
            this.inputs.get(i).backpropagate(dEto1 * do1n1 * this.weights.get(i));
        }
        this.n_bias = this.bias + dEto1 * do1n1;
    }

    public void updateWeights() {
        System.out.println("Updating output");
        this.weights = this.n_weights;
        this.bias = this.n_bias;
    }
}
