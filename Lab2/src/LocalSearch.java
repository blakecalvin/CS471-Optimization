import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

class LocalSearch extends Algorithm{

    private double delta = .25; //Normally .11

    ArrayList<Double> calculate(int iterations, FitnessFormula f, int d) {

        ArrayList<Double> argBest = generate(f.range(), d);
        fBest = f.calculate(argBest, d);
        boolean tau = true;

        while(tau){

            tau = false;
            ArrayList<Double> argLoc = empiricalGradient(argBest, f, d);
            withinBounds(f, argLoc);

            double locFitness = f.calculate(argLoc, d);
            if(locFitness < fBest){
                argBest = argLoc;
                tau = true;
                fBest = locFitness;
            }

            count ++;
        }

        return argBest;
    }

    /*
        Applies an empirical gradient to the given ArrayList
    */
    ArrayList<Double> empiricalGradient(ArrayList<Double> argBest, FitnessFormula f, int d){

        ArrayList<Double> argLoc = new ArrayList<>();
        ArrayList<Double> argTemp = new ArrayList<>(argBest);

        for(int i = 0; i < argBest.size(); i++){

            Double deltaF = argBest.get(i)+delta;
            argTemp.set(i,deltaF);

            argLoc.add(argBest.get(i) - delta*(f.calculate(argTemp, d)-f.calculate(argBest, d)));

            argTemp.set(i,argBest.get(i));
        }
        return argLoc;
    }

    /*
        Takes any dimension that is out of bounds and pushes it to the bounds
     */
    void withinBounds(FitnessFormula f, ArrayList<Double> arg){
        for(int i = 0; i < arg.size(); i++){
            if(arg.get(i) < f.range()[0]){
                arg.set(i, f.range()[0]);
            }
            else if(arg.get(i) > f.range()[1]){
                arg.set(i, f.range()[1]);
            }
        }
    }

    String getName() {
        return "Local Search";
    }
}
