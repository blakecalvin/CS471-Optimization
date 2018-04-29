package FitnessFormulas;

import java.util.ArrayList;

// #13
public class Michalewicz extends FitnessFormula {

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.sin(v.get(i))*Math.pow((Math.sin((i*Math.pow(v.get(i),2))/Math.PI)),10);
        }
        s = -s;
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {0,Math.PI};
        return r;
    }

    public String name(){
        return "FitnessFormulas.Michalewicz";
    }
}
