import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

class BlindSearch extends Algorithm{

    private int iterations;
    private FitnessFormula f;
    private int d;

    public BlindSearch(int iterations, FitnessFormula f, int d){
        this.iterations = iterations;
        this.f = f;
        this.d = d;
    }

    public ArrayList<Double> run(){
        ArrayList<Double> argBest = new ArrayList<>();
        double fitness0 = 1000000000;

        for(int i  = 0; i < iterations; i++){
            ArrayList<Double> arg = generate(f.range(), d);
            Double fitness = f.calculate(arg, d);
            if(fitness < fitness0){
                fitness0 = fitness;
                argBest = arg;
            }
        }

        fBest = fitness0;
        count += iterations;
        return argBest;
    }

    String getName() {
        return "Blind Search";
    }
}
