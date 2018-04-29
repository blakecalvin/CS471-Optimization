import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

class Genetic extends EvolutionaryAlgorithm {

    private int ns;
    private int dim;
    private double[] bounds;
    private int tMax;
    private double cr;
    private double mRate;
    private double mRange;
    private double mPrecision;
    private double er;


    private Genetic(int ns, int dim, double[] bounds, int tMax, double cr, double mRate, double mRange, double mPrecision, double er){
        this.ns = ns;
        this.dim = dim;
        this.bounds = bounds;
        this.tMax = tMax;
        this.cr = cr;
        this.mRate = mRate;
        this.mRange = mRange;
        this.mPrecision = mPrecision;
        this.er = er;
    }

    void randomInit(Population population, double[] bounds) {

        for(int i = 0; i < ns; i++){
            for(int j = 0; j < dim; j++){
                double r = random(bounds[0], bounds[1]);
                population.addValue(i, r);
            }
        }
    }

    ArrayList<Double> run(FitnessFormula f) {

        Double elitism = er * ns;
        Population p = new Population(ns, dim);
        randomInit(p, bounds);
        //evaluate(p);
        int t = 1;

        while(t <= tMax){
            Population newP = new Population(ns, dim);
            for(int s =  1; s <= ns; s+= 2){

            }
        }
        return null;
    }


}
