import java.util.ArrayList;

public class AnnNeuron extends Element {

    protected ArrayList<Double> n_weights = new ArrayList<>();
    protected double n_bias;
    protected double t_error = 0;
    protected double t_outcount = 0;

    public void addInput(Element e) {
        if(this.inputs.contains(e)) {
            this.inputs.remove(e);
        } else {
            this.inputs.add(e);
        }
        e.addOutput(this);
        this.resetWeights();
    }

    public void addOutput(Element e) {
        if(this.outputs.contains(e)) {
            this.outputs.remove(e);
        } else {
            this.outputs.add(e);
        }
        this.resetWeights();
    }

    AnnNeuron(ArrayList<Element> elements) {
        this.inputs = elements;
        this.generateUID();
        this.resetWeights();
    }
    AnnNeuron() {
        this.inputs = new ArrayList<Element>();
        this.generateUID();
        this.resetWeights();
    }


    public void resetWeights() {
        weights = new ArrayList<>();
        for(int i = 0; i < inputs.size(); i++) {
            weights.add(Math.random());
        }
    }

    // Apskaiciavimas
    protected double calculate(ArrayList<Element> inputs) {
        if(inputs.size() < 1) return 1;
        double res = 0;
        for(int i = 0; i < inputs.size(); i++) {
            res += inputs.get(i).output() * weights.get(i);
        }
        res += bias;
        return sigmoid(res);
    }

    private double sigmoid(double x) {
        return (1/( 1 + Math.pow(Math.E,(-1*x))));
    }

    // Svoriu atnaujinimas
    public void backpropagate(double error) {
        System.out.println("Backpropagating hidden");
        t_error += error;
        t_outcount++;
        double o1 = this.output();
        double dEto1 = t_error;
        double do1n1 = o1 * (1 - o1);
        this.n_weights = new ArrayList<>(this.weights);
        for(int i = 0; i < this.inputs.size(); i++) {
            this.n_weights.set(i, ( this.weights.get(i) + dEto1 * do1n1 * this.inputs.get(i).output() ));
            if(t_outcount == this.outputs.size()) this.inputs.get(i).backpropagate(dEto1 * do1n1 * this.weights.get(i));
        }
        this.n_bias = this.bias + dEto1 * do1n1;
    }

    public void updateWeights() {
        System.out.println("Updating hidden");
        this.t_outcount = 0;
        this.t_error = 0;
        this.weights = this.n_weights;
        this.bias = this.n_bias;
    }
}
