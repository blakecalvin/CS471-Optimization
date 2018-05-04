package FitnessFormulas;

import java.util.ArrayList;

// #1
public class Schwefel extends FitnessFormula {

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += -(v.get(i))*Math.sin(Math.sqrt(Math.abs(v.get(i))));
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-512,512};
        return r;
    }

    public String name(){
        return "Schwefel";
    }
}
