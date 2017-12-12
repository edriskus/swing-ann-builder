import java.util.ArrayList;

public class AnnMapper extends Element {
    private ArrayList<Double> weights;
    private float bias = 1;

    // Apskaiciavimas
    protected double calculate(ArrayList<Element> inputs) {
        if(inputs.size() < 1) return 1;
        return step(inputs.get(0).output());
    }

    private double step(double x) {
        if(x > 0.5) return 1;
        else return 0;
    }

}
