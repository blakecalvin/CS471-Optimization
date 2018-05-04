import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

class IterativeLocalSearch extends Algorithm {

    private int iterations;
    private FitnessFormula f;
    private int d;
    private double delta;

    public IterativeLocalSearch(int iterations, FitnessFormula f, int d, double delta){
        this.iterations = iterations;
        this.f = f;
        this.d = d;
        this.delta = delta;
    }

    ArrayList<Double> run() {

        ArrayList<Double> argBest = generate(f.range(), d);
        double locBest = f.calculate(argBest, d);
        ArrayList<Double> globalBest = argBest;
        fBest = locBest;

        int iterCount = 1;

        while(iterCount <= iterations){

            argBest = (new LocalSearch(iterations, f, d, delta)).run();
            locBest = f.calculate(argBest,d);

            if(locBest < fBest){
                fBest = locBest;
                globalBest = argBest;
            }

            iterCount++;
        }
        count += iterations;
        return globalBest;
    }

    String getName() {
        return "Iterative Local Search";
    }
}
