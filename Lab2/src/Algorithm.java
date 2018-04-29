import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

abstract class Algorithm {

    double fBest = 0;
    int count = 0;

    abstract ArrayList<Double> calculate(int iterations, FitnessFormula f, int d);
    abstract String getName();

    /*
        Function: generate()
        Description: Generates a arrayList of length 'd' within the given range using the
            Mersenne Twister pseudo-number generator.
        Params: double[] range, int d
        Return: ArrayList<Double>
    */
    ArrayList<Double> generate(double[] range, int d){
        // Range for given function
        double min = range[0];
        double max = range[1];

        // Returned values from the calculate function
        ArrayList<Double> result = new ArrayList<>();
        for(int j = 0; j < d; j++){

            // Creates new instance of Mersenne Twister pseudo-number generator
            MTRandom rand = new MTRandom();
            double generated = min + (max - min) * rand.nextDouble();

            result.add(generated);
        }

        return result;
    }

}
