/**
 @author Blake Calvin
 CS 471 - Optimization
 Project 3
 @date 5/7/18
 */
import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

/**
 * Iterative Local Search Class
 */
class IterativeLocalSearch extends Algorithm {

    private int iterations;
    private FitnessFormula f;
    private int d;
    private double delta;

    /**
     * Initilaizes ILS
     * @param iterations
     * @param f
     * @param d
     * @param delta
     */
    public IterativeLocalSearch(int iterations, FitnessFormula f, int d, double delta){
        this.iterations = iterations;
        this.f = f;
        this.d = d;
        this.delta = delta;
    }

    /**
     * Runs Local Search iterative until condition is no longer true.
     * @return
     */
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

    /**
     * returns name of algorithm
     * @return
     */
    String getName() {
        return "Iterative Local Search";
    }
}
