package FitnessFormulas;

import java.util.ArrayList;

// #5
public class Griewangk extends FitnessFormula {

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        double p = 1.0;
        double I = 1.0;
        for(int i = 0; i < d; i++){
            s += (Math.pow(v.get(i),2)/4000);
            p = p * Math.cos((v.get(i)/(Math.sqrt(I))));
            I++;
        }
        s = 1 + s - p;
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-500,500};
        return r;
    }

    public String name(){
        return "FitnessFormulas.Griewangk";
    }
}
