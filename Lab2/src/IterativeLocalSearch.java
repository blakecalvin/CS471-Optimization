import java.util.ArrayList;

class IterativeLocalSearch extends Algorithm {

    ArrayList<Double> calculate(int iterations, FitnessFormula f, int d) {

        ArrayList<Double> argBest = generate(f.range(), d);
        double locBest = f.calculate(argBest, d);
        ArrayList<Double> globalBest = argBest;
        fBest = locBest;

        int iterCount = 1;

        while(iterCount <= iterations){

            argBest = (new LocalSearch()).calculate(iterations, f, d);
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
