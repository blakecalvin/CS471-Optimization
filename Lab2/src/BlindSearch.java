import java.util.ArrayList;

class BlindSearch extends Algorithm{

    public ArrayList<Double> calculate(int iterations, FitnessFormula f, int d){
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
