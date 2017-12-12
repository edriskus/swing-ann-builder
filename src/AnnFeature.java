import java.util.ArrayList;

public class AnnFeature extends Element {
    private double value;
    AnnFeature(double input) {
        this.value = input;
    }

    AnnFeature() {
        this.value = 1;
    }

    // Galima keisti reiksme
    public void set(double val) {
        this.value = val;
    }

    protected double calculate(ArrayList<Element> inputs) {
        return this.value;
    }
}
