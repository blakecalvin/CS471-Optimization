/*
    Blake Calvin
    CS 471 - Optimization
    Project 2
    Last Updated - 4/12/18
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Project_2 {

    // Constants
    private static final int TESTS = 30;
    private static final int[] D = {10,20,30};

    public static void main(String[] args){

        if(args.length<4){
            System.out.println("Error: missing arguments [Function #] [Algorithm #] [# of Dimensions] [# of Iterations]");
            return;
        }

        int function = Integer.parseInt(args[0]);
        int algorithm = Integer.parseInt(args[1]);
        int dimensions = Integer.parseInt(args[2]);
        int iterations = Integer.parseInt(args[3]);

        switch (function) {
            case 1:
                test(new Schwefel(), algorithm, dimensions, iterations);
                break;
            case 2:
                test(new FirstDeJong(), algorithm, dimensions, iterations);
                break;
            case 3:
                test(new Rosenbrock(), algorithm, dimensions, iterations);
                break;
            case 4:
                test(new Rastrigin(), algorithm, dimensions, iterations);
                break;
            case 5:
                test(new Griewangk(), algorithm, dimensions, iterations);
                break;
            case 6:
                test(new SineEnvelopeSineWave(), algorithm, dimensions, iterations);
                break;
            case 7:
                test(new StretchedVSineWave(), algorithm, dimensions, iterations);
                break;
            case 8:
                test(new AckeysOne(), algorithm, dimensions, iterations);
                break;
            case 9:
                test(new AckeysTwo(), algorithm, dimensions, iterations);
                break;
            case 10:
                test(new EggHolder(), algorithm, dimensions, iterations);
                break;
            case 11:
                test(new Rana(), algorithm, dimensions, iterations);
                break;
            case 12:
                test(new Pathological(), algorithm, dimensions, iterations);
                break;
            case 13:
                test(new Michalewicz(), algorithm, dimensions, iterations);
                break;
            case 14:
                test(new MastersCosineWave(), algorithm, dimensions, iterations);
                break;
            case 15:
                test(new ShekelsFoxhole(), algorithm, dimensions, iterations);
                break;

        }

    }

    /*
        Function: test()
        Description: Runs 30 tests on the given function 'f' and dimension set 'd'.
        Params: FitnessFormula f, int[] d
        Return: none
     */
    public static void test(FitnessFormula f, int algo, int d, int iter){

        double[] results = new double[TESTS];

        for(int k = 0; k < TESTS; k++){
            switch (algo){
                case 1:
                    results[k] = f.calculate(randomWalk(iter, f , d),d);
                    break;
                case 2:
                    //results[k] = ;
                    break;
                case 3:
                    //results[k] = ;
                    break;
            }
        }

        // Calculates average time to complete computation
        long avg = (f.getAvgTime())/(long)TESTS*iter;

        // Exports results to CSV file
        export(f.name(), d, avg, results);
        // resets avgTime to zero
        f.resetAvgTime();
    }

    /*
        Function: generate()
        Description: Generates a arrayList of length 'd' within the given range using the
            Mersenne Twister pseudo-number generator.
        Params: double[] range, int d
        Return: ArrayList<Double>
     */
    public static ArrayList<Double> generate(double[] range, int d){

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

    /*
        Function: export
        Description: Exports the results from the tests to a csv file for analysis of data.
        Params: String name, int d, long avgTime, double[] results
        Return: none
     */
    public static void export(String name, int d, long avgTime, double[] results){

        try{
            FileWriter fw = new FileWriter("fitness.csv", true);
            StringBuilder sb = new StringBuilder();

            sb.append(name);
            sb.append(",");
            sb.append(d);
            sb.append(",");
            sb.append(avgTime);
            for(int i = 0; i < TESTS; i++){
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

    //--- Algorithms -------------------------------------------------------------------

    public static ArrayList<Double> randomWalk(int iterations, FitnessFormula f, int d){

        ArrayList<Double> argBest = new ArrayList<>();
        double fitness0 = f.range()[1];

        for(int i  = 0; i < iterations; i++){
           ArrayList<Double> arg = generate(f.range(), d);
           Double fitness = f.calculate(arg, d);
           if(fitness < fitness0){
               fitness0 = fitness;
               argBest = arg;
           }
        }

        return argBest;
    }
}

//--- Fitness Function Definitions -------------------------------------------

// #1
class Schwefel extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += -(v.get(i))*Math.sin(Math.sqrt(Math.abs(v.get(i))));
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-512,512};
        return r;
    }

    public String name(){
        return "Schwefel";
    }
}

// #2
class FirstDeJong extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.pow(v.get(i),2);
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-100,100};
        return r;
    }

    public String name(){
        return "1st De Jong";
    }
}

// #3
class Rosenbrock extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += (100*((Math.pow(v.get(i),2))-v.get(i+1))) + Math.pow((1-v.get(i)),2);
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-100,100};
        return r;
    }

    public String name(){
        return "Rosenbrock";
    }
}

// #4
class Rastrigin extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.pow(v.get(i),2)-(10*Math.cos(2*Math.PI*v.get(i)));
        }
        s = s*2*d;
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-30,30};
        return r;
    }

    public String name(){
        return "Rastrigin";
    }
}

// #5
class Griewangk extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        double p = 1.0;
        double I = 1.0;
        for(int i = 0; i < d; i++){
            s += (Math.pow(v.get(i),2)/4000);
            p = p * Math.cos((v.get(i)/(Math.sqrt(I))));
            I++;
        }
        s = 1 + s - p;
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-500,500};
        return r;
    }

    public String name(){
        return "Griewangk";
    }
}

// #6
class SineEnvelopeSineWave extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += 0.5 + Math.pow(Math.sin((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)-0.5)),2)/Math.pow((1+0.001*(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))),2);
        }
        s = -s;
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-30,30};
        return r;
    }

    public String name(){
        return "Sine Envelope Sine Wave";
    }
}

// #7
class StretchedVSineWave extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += (Math.pow((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)),.25)*Math.pow(Math.sin((50*Math.pow((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)),.1))),2)+1);
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-30,30};
        return r;
    }

    public String name(){
        return "Stretched V Sine Wave";
    }
}

// #8
class AckeysOne extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += (1/Math.pow((Math.E),.2))*Math.sqrt(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))+3*(Math.cos(2*v.get(i))+Math.sin(2*v.get(i+1)));
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-32,32};
        return r;
    }

    public String name(){
        return "Ackey's One";
    }
}

// #9
class AckeysTwo extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += 20 + Math.E - (20/(Math.pow(Math.E,.2)*Math.sqrt((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))/2)))-Math.pow(Math.E,0.5*(Math.cos(2*Math.PI*v.get(i))+Math.cos(2*Math.PI*v.get(i+1))));
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-32,32};
        return r;
    }

    public String name(){
        return "Ackey's Two";
    }
}

// #10
class EggHolder extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += -v.get(i)*Math.sin(Math.sqrt(Math.abs(v.get(i)-v.get(i+1)-47)))-(v.get(i+1)+47)*Math.sin(Math.sqrt(Math.abs(v.get(i+1)+47+(v.get(i)/2))));
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-500,500};
        return r;
    }

    public String name(){
        return "Egg Holder";
    }
}

// #11
class Rana extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += v.get(i)*Math.sin(Math.sqrt(Math.abs(v.get(i+1)-v.get(i)+1)))*Math.cos(Math.sqrt(Math.abs(v.get(i+1)+v.get(i)+1)))+(v.get(i+1)+1)*Math.cos(Math.sqrt(Math.abs(v.get(i+1)-v.get(i)+1)))*Math.sin(Math.sqrt(Math.abs(v.get(i+1)+v.get(i)+1)));
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-500,500};
        return r;
    }

    public String name(){
        return "Rana";
    }
}

// #12
class Pathological extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += 0.5 + (Math.pow(Math.sin(Math.sqrt(100*Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))),2)-0.5)/(1+0.001*Math.pow((Math.pow(v.get(i),2)-2*v.get(i)*v.get(i+1)+Math.pow(v.get(i+1),2)),2));
        }
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-100,100};
        return r;
    }

    public String name(){
        return "Pathological";
    }
}

// #13
class Michalewicz extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.sin(v.get(i))*Math.pow((Math.sin((i*Math.pow(v.get(i),2))/Math.PI)),10);
        }
        s = -s;
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {0,Math.PI};
        return r;
    }

    public String name(){
        return "Michalewicz";
    }
}

// #14
class MastersCosineWave extends FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += Math.pow(Math.E,(-(1/8)*(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)+0.5*v.get(i+1)*v.get(i))))*Math.cos(4*Math.sqrt(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)+0.5*v.get(i+1)*v.get(i)));
        }
        s = -s;
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {-30,30};
        return r;
    }

    public String name(){
        return "Master's Cosine Wave";
    }
}

// #15
class ShekelsFoxhole extends FitnessFormula{

    private int m = 30;
    private ArrayList<Double> c;
    private ArrayList<ArrayList<Double>> a;

    // When object is created, read in values from text file
    public ShekelsFoxhole(){

        String fileName = "Shekel's_Foxhole_Data.txt";

        try{
            Scanner s = new Scanner(new File(fileName));
            s.useDelimiter("\\Z");

            c = new ArrayList<>();

            // Parse c value array
            String file = s.next();
            String[] var = file.split(";");
            String[] c1 = var[0].split("\\{");
            String[] c2 = c1[1].split("}");
            String[] c3 = c2[0].split(",");
            for(int i=0; i<c3.length; i++){
                c.add(Double.parseDouble(c3[i]));
            }

            a = new ArrayList<>();

            // Parse a value array
            String[] a1 = var[1].split("\\{");
            for(int i=2; i < a1.length; i++){
                ArrayList<Double> inner = new ArrayList<>();
                String[] a2 = a1[i].split("}");
                String[] a3 = a2[0].split(",");
                for(int j=0; j<a3.length; j++){
                    inner.add(Double.parseDouble(a3[j]));
                }
                a.add(inner);
            }

            s.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error: File not found.");
        }
    }

    public double calculate(ArrayList<Double> v, int d){
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < m; i++){
            double s2 = 0.0;
            for(int j = 0; j < d; j++){
                s2 += Math.pow((v.get(j)-a.get(i).get(j)),2);
            }
            s += 1/(c.get(i)+s2);
        }
        s = -s;
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {0,10};
        return r;
    }

    public String name(){
        return "Shekel's Foxhole";
    }
}

