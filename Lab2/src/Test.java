import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Test {

    private Algorithm algo;
    private FitnessFormula[] list;
    private int d;
    private int iter;
    private int t;
    private String file;
    private double[] results;

    public Test(Algorithm a, FitnessFormula[] f, int dimensions, int iterations, int tests, String output){
        algo = a;
        list = f;
        d = dimensions;
        iter = iterations;
        t = tests;
        file = output;
        run();
    }

    /*
        Creates a test for each algorithm running through all fitness functions with the given number of tests and iterations
     */
    void run(){
        for(int i = 0; i<list.length; i++){
            if(list[i].scalable() || d <= 10) {
                results = new double[t];
                for (int k = 0; k < t; k++) {

                    algo.calculate(iter, list[i], d);
                    results[k] = algo.fBest;
                    algo.fBest = 0;
                }

                int avgIter = algo.count / t;
                long avg = (list[i].getAvgTime()) / algo.count;
                algo.count = 0;
                export(list[i].name(), d, avg, avgIter, results);
            }
        }
    }

    /*
        Function: export
        Description: Exports the results from the tests to a csv file for analysis of data.
        Params: String name, int d, long avgTime, double[] results
        Return: none
    */
    void export(String f, int d, long avgTime, int avgIter, double[] results){
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
            sb.append(",");
            sb.append(avgIter);
            for(int i = 0; i < t; i++){
                sb.append(",");
                sb.append(results[i]);
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
