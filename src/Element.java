import java.util.ArrayList;
import java.util.UUID;

public abstract class Element {
    public ArrayList<Double> weights = new ArrayList<>();
    public double bias = 1;

    protected ArrayList<Element> outputs = new ArrayList<>();
    protected ArrayList<Element> inputs = new ArrayList<Element>();
    protected int x;
    protected int y;
    protected UUID UID;
    public boolean hoverDelete = false;

    Element() {
        this.generateUID();
    }
    Element(ArrayList<Element> elements) {
        this.inputs = elements;
        this.generateUID();
    }

    protected void generateUID() {
        this.bias = Math.random();
        this.UID = UUID.randomUUID();
    }

    public void addInput(Element e) {
        if(this.inputs.contains(e)) {
            this.inputs.remove(e);
        } else {
            this.inputs.add(e);
        }
    }

    public ArrayList<Element> getInputs() {
        return inputs;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int val) {
        this.x = val;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int val) {
        this.y = val;
    }

    public String getUID() {
        return this.UID.toString();
    }

    public double output() {
        return this.calculate(this.inputs);
    }

    protected double calculate(ArrayList<Element> inputs) {
        return 1;
    }

    public void backpropagate(double error) {}

    public void updateWeights() {}

    public void set(double b) {};

    public void addOutput(Element e) {}

    public void setUID(String str) {
        this.UID = UUID.fromString(str);
    }
}
