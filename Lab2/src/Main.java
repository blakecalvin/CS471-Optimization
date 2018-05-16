/**
    @author Blake Calvin
    CS 471 - Optimization
    Project 3
    @date 5/7/18
 */

import FitnessFormulas.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //! Initialize all fitness equations
    public static FitnessFormula[] list = {new Schwefel(), new FirstDeJong(), new Rosenbrock(), new Rastrigin(), new Griewangk(),
            new SineEnvelopeSineWave(), new StretchedVSineWave(), new AckeysOne(), new AckeysTwo(), new EggHolder(), new Rana(),
            new Pathological(), new Michalewicz(), new MastersCosineWave(), new ShekelsFoxhole()};

    // Parameters
    private static int TESTS = 30; //! Number of tests to run
    private static String algorithm; //! Algorithm choice
    private static int dim; //! Dimension size
    private static int iter; //! Number of iterations
    private static String file; //! output file
    private static double delta; //! Delta of LS
    private static long avgTime; //! Average time to complete test
    private static int ns; //! number of solutions
    private static double cr; //! Crossover rate
    private static double mRate; //! mutation rate
    private static double mRange; //! mutation range
    private static double mPrecision; //! mutation precision
    private static double er; //! elitism rate

    private static double F;
    private static int strategy; //! Mutation strategy


    /**
     * Main method called from bash script.
     * @param args
     */
    public static void main(String[] args){

        if(args.length<4){
            System.out.println("Error: missing arguments [Algorithm] [# of Dimensions] [# of Iterations] [Output file]");
            return;
        }

        algorithm = args[0];
        dim = Integer.parseInt(args[1]);
        iter = Integer.parseInt(args[2]);
        file = args[3];

        if(algorithm.equals("DE")){
            strategy = strategyChoice();
        }

        createTest();
    }

    /**
     * Creates tests for optimization
     */
    public static void createTest(){

        parseParameters();

        for(FitnessFormula f : list){
            if(f.scalable() || dim == 10){
                ArrayList<Double> results = new ArrayList<>();
                avgTime = 0;

                switch (algorithm){
                    case "BS":
                        results = test(new BlindSearch(iter, f, dim), TESTS);
                        break;
                    case "LS":
                        results = test(new LocalSearch(iter, f, dim, delta), TESTS);
                        break;
                    case "ILS":
                        results = test(new IterativeLocalSearch(iter, f, dim, delta), TESTS);
                        break;
                    case "GA":
                        results = test(new Genetic(f, ns, dim, f.range(), iter, cr, mRate, mRange, mPrecision, er), TESTS);
                        break;
                    case "DE":
                        results = test(new DifferentialEvolution(dim, iter, ns, f, F, cr, f.range(), strategy), TESTS);
                        break;
                }

                //get avgTime for results
                System.out.println(f.name()+": "+results.toString());
                avgTime = f.avgTime/TESTS;

                export(f.name(), dim, avgTime, results);
            }
        }
    }

    /**
     * Runs test on desired alogrithm.
     * @param a
     * @param iter
     * @return Array of results of size iter
     */
    public static ArrayList<Double> test(Algorithm a, int iter){
        ArrayList<Double> results = new ArrayList<>();
        for(int i = 0; i < iter; i++){
            a.run();
            results.add(a.fBest);
            //need to reset all values that could alter calculations
            a.fBest = 0;
        }
        return results;
    }

    /**
     * Parse Parameters
     * Takes the parameters for all algorithms from the parameters.txt.
     */
    public static void parseParameters(){
        String fileName = "Parameters.txt";

        try {
            Scanner s = new Scanner(new File(fileName));
            int counter = 1;
            while(s.hasNext()){
                switch (counter){
                    case 4:
                        TESTS = s.nextInt();
                        break;
                    case 7:
                        delta = s.nextDouble();
                        break;
                    case 10:
                        ns = s.nextInt();
                        break;
                    case 11:
                        cr = s.nextDouble();
                        break;
                    case 12:
                        mRate = s.nextDouble();
                        break;
                    case 13:
                        mRange = s.nextDouble();
                        break;
                    case 14:
                        mPrecision = s.nextDouble();
                        break;
                    case 15:
                        er = s.nextDouble();
                        break;
                    case 18:
                        F = s.nextDouble();
                        break;
                }
                counter++;
                s.nextLine();
            }

        }
        catch(FileNotFoundException e){
            System.out.println("Error: File not found (\"" + fileName + "\")"  );
        }
    }

    /**
     * strategy choice
     * Takes user input to determine mutation strategy for DE.
     * @return input for strategy choice
     */
    public static int strategyChoice(){
        Scanner in = new Scanner(System.in);
        int input =  0;
        do{
            System.out.println("Please choose a mutation strategy:");
            System.out.println("1: best/1/exp");
            System.out.println("2: rand/1/exp");
            System.out.println("3: rand-to-best/1/exp");
            System.out.println("4: best/2/exp");
            System.out.println("5: rand/2/exp");
            System.out.println("6: best/1/bin");
            System.out.println("7: rand/1/bin");
            System.out.println("8: rand-to-best/1/bin");
            System.out.println("9: best/2/bin");
            System.out.println("10: rand/2/bin");
            System.out.println("Input corresponding number: ");
            input = in.nextInt();
        }while(input <= 0 || input >= 11);
        return input;
    }

    /**
    * export
    * Exports the results from the tests to a csv file for analysis of data.
    * @params: String name, int d, long avgTime, double[] results
    * @return: none
    */
    public static void export(String f, int d, long avgTime, ArrayList<Double> results){
        try{
            File yourFile = new File(file);
            yourFile.createNewFile();
            FileWriter fw = new FileWriter(yourFile, true);
            StringBuilder sb = new StringBuilder();

            sb.append(f);
            sb.append(",");
            sb.append(d);
            sb.append(",");
            sb.append(avgTime);
            for(int i = 0; i < results.size(); i++){
                sb.append(",");
                sb.append(results.get(i));
            }
            sb.append("\n");

            fw.write(sb.toString());
            fw.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error: No Such File found.");
        }
        catch(IOException e){
            System.out.println("Error: Cannot output to file.");
        }
    }

}

