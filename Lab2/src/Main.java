/*
    Blake Calvin
    CS 471 - Optimization
    Project 2
    Last Updated - 4/12/18
 */

import FitnessFormulas.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // All fitness equations
    public static FitnessFormula[] list = {new Schwefel(), new FirstDeJong(), new Rosenbrock(), new Rastrigin(), new Griewangk(),
            new SineEnvelopeSineWave(), new StretchedVSineWave(), new AckeysOne(), new AckeysTwo(), new EggHolder(), new Rana(),
            new Pathological(), new Michalewicz(), new MastersCosineWave(), new ShekelsFoxhole()};

    // Parameters
    private static int TESTS = 30;
    private static String algorithm;
    private static int dim;
    private static int iter;
    private static String file;

    private static double delta;
    private static long avgTime;

    private static int ns;
    private static double cr;
    private static double mRate;
    private static double mRange;
    private static double mPrecision;
    private static double er;



    public static void main(String[] args){

        if(args.length<4){
            System.out.println("Error: missing arguments [Algorithm] [# of Dimensions] [# of Iterations] [Output file]");
            return;
        }

        algorithm = args[0];
        dim = Integer.parseInt(args[1]);
        iter = Integer.parseInt(args[2]);
        file = args[3];

        createTest();
    }

    public static void createTest(){

        parseParameters();

        for(FitnessFormula f : list){
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
                    results = test(new DifferentialEvolution(), TESTS);
                    break;
            }

            //get avgTime for results
            avgTime = f.avgTime/TESTS;

            export(f.name(), dim, avgTime, results);
        }
    }

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
                }
                counter++;
                s.nextLine();
            }

        }
        catch(FileNotFoundException e){
            System.out.println("Error: File not found.");
        }
    }

    /*
    Function: export
    Description: Exports the results from the tests to a csv file for analysis of data.
    Params: String name, int d, long avgTime, double[] results
    Return: none
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

