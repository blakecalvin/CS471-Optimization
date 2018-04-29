/*
    Blake Calvin
    CS 471 - Optimization
    Project 2
    Last Updated - 4/12/18
 */

import FitnessFormulas.*;

public class Main {

    // Constants
    private static final int TESTS = 30;

    // All fitness equations
    public static FitnessFormula[] list = {new Schwefel(), new FirstDeJong(), new Rosenbrock(), new Rastrigin(), new Griewangk(),
            new SineEnvelopeSineWave(), new StretchedVSineWave(), new AckeysOne(), new AckeysTwo(), new EggHolder(), new Rana(),
            new Pathological(), new Michalewicz(), new MastersCosineWave(), new ShekelsFoxhole()};

    // Parameters

    public static void main(String[] args){

        if(args.length<4){
            System.out.println("Error: missing arguments [Algorithm #] [# of Dimensions] [# of Iterations] [Output file]");
            return;
        }

        int algorithm = Integer.parseInt(args[0]);
        int dimensions = Integer.parseInt(args[1]);
        int iterations = Integer.parseInt(args[2]);
        String output = args[3];

        // creates test corresponding to the values passed in from the command line.
        switch (algorithm){
            case 1:
                new Test(new BlindSearch(), list, dimensions, iterations, TESTS, output);
                break;
            case 2:
                new Test(new LocalSearch(), list, dimensions, iterations, TESTS, output);
                break;
            case 3:
                new Test(new IterativeLocalSearch(), list, dimensions, iterations, TESTS, output);
                break;
        }

    }

    public static void parseParameters(){

    }

}

