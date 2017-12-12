import org.json.*;

import java.util.ArrayList;

public class NeuralNetwork {
    private ArrayList<Element> elements = new ArrayList<Element>();
    private ArrayList<Element> outputs = new ArrayList<Element>();
    private ArrayList<Element> inputs = new ArrayList<Element>();
    public ArrayList<ArrayList<Double>> currentTrainData = new ArrayList<>();

    NeuralNetwork() {}

    public Element add(Element e) {
        if (e instanceof AnnOutput) {
            this.outputs.add(e);
        }
        if (e instanceof AnnFeature) {
            this.inputs.add(e);
        }
        this.elements.add(e);
        return e;
    }

    public void remove(Element el) {
        for(Element e: this.elements) {
            if(e.inputs.contains(el)) e.inputs.remove(el);
        }
        this.elements.remove(el);
        this.outputs.remove(el);
        this.inputs.remove(el);
    }

    public ArrayList<Element> getInputs() {
        ArrayList<Element> res = new ArrayList<>();
        for(Element e: elements) {
            if(e instanceof AnnFeature) res.add(e);
        }
        return res;
    }

    public ArrayList<Element> getStartingPoints() {
        ArrayList<Element> res = new ArrayList<>();
        for(Element e: elements) {
            if(e.inputs.size() < 1) res.add(e);
        }
        return res;
    }

    public ArrayList<Element> getOutputs() {
        return outputs;
    }

    public double get(int i) {
        return this.outputs.get(i).output();
    }

    public ArrayList<Double> get() {
        ArrayList<Double> res = new ArrayList<Double>();
        for(Element e: this.outputs) {
            res.add(e.output());
        }
        return res;
    }

    public void setDataPoint(ArrayList<Double> data) {
        for (int j = 0; j < this.inputs.size(); j++) {
            this.inputs.get(j).set(data.get(j));
        }
    }

    public void train(ArrayList<ArrayList<Double>> data, int iterations) {
        this.currentTrainData = data;
        for(int it = 0; it < iterations; it++) {
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < this.inputs.size(); j++) {
                    this.inputs.get(j).set(data.get(i).get(j));
                }
                for (int j = 0; j < this.outputs.size(); j++) {
                    this.outputs.get(j).backpropagate(
                            data.get(i).get(j + this.inputs.size())
                    );
                }
                for (int j = 0; j < this.elements.size(); j++) {
                    this.elements.get(j).updateWeights();
                }
            }
        }
    }

    public ArrayList<ArrayList<Double>> formatData(String input) {
        System.out.println("Received: " + input);
        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        System.out.println("Creating a JSON object");
        JSONObject obj = new JSONObject("{ \"array\": " + input + "}");
        System.out.println("Creating a JSON array");
        JSONArray points = obj.getJSONArray("array");
        System.out.println("Iterating");
        for (int i = 0; i < points.length(); i++) {
            System.out.println("Getting a JSON array at " + i);
            JSONArray inside = points.getJSONArray(i);
            ArrayList<Double> tarr = new ArrayList<>();
            for (int j = 0; j < this.inputs.size() + this.outputs.size(); j++) {
                System.out.println("Getting a double at " + j);
                tarr.add(inside.getDouble(j));
            }
            data.add(tarr);
        }
        return data;
    }

    public ArrayList<Element> getElements() {
        return this.elements;
    }

}
