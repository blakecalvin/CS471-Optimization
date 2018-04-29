import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

abstract class EvolutionaryAlgorithm {

    abstract void randomInit(Population population, double[] bounds);

    abstract ArrayList<Double> run(FitnessFormula f);

    double random(double min, double max){
        MTRandom rand = new MTRandom();
        double r = min + (max - min) * rand.nextDouble();
        return r;
    }
}
