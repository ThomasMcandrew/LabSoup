package soup.utils.csv;

import java.util.ArrayList;

public class DoubleList {
    private ArrayList<Integer> keys;
    private ArrayList<Double> values;
    public DoubleList(){
        keys = new ArrayList<>();
        values = new ArrayList<>();
    }
    public void put(int key, double val){
        keys.add(key);
        values.add(val);
    }
    public Double get(int key){
        return values.get(keys.indexOf(key));
    }
    public boolean containsKey(int key){
        return keys.contains(key);
    }
    public void add(int key, double val){
        double t = values.get(keys.indexOf(key));
        values.set(keys.indexOf(key),t + val);
    }
    public int length(){
        return keys.size();
    }
    public double[] getKeyAndVal(int i){
        return new double[]{keys.get(i), values.get(i)};
    }
}
