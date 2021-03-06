/*
    Blake Calvin
    CS 471 - Optimization
    Project 1
    Last Updated - 4/4/18
 */

import java.io.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Scanner;

public class Project_1 {

    // Constants
    private static final int TESTS = 30;
    private static final int[] D = {10,20,30};

    public static void main(String[] args){

        //Test each fitness function over given dimensions
        test(new Schwefel(), D);

        test(new FirstDeJong(), D);

        test(new Rosenbrock(), D);

        test(new Rastrigin(), D);

        test(new Griewangk(), D);

        test(new SineEnvelopeSineWave(), D);

        test(new StretchedVSineWave(), D);

        test(new AckeysOne(), D);

        test(new AckeysTwo(), D);

        test(new EggHolder(), D);

        test(new Rana(), D);

        test(new Pathological(), D);

        test(new Michalewicz(), D);

        test(new MastersCosineWave(), D);

        int[] SHD = {10};
        test(new ShekelsFoxhole(), SHD);

    }

    /*
        Function: test()
        Description: Runs 30 tests on the given function 'f' and dimension set 'd'.
        Params: FitnessFormula f, int[] d
        Return: none
     */
    public static void test(FitnessFormula f, int[] d){

        for(int i = 0; i<d.length; i++){

            // Randomly generates a multidimensional arrayList of values to use for testing
            ArrayList<ArrayList<Double>> nums = generate(f.range(), d[i]);
            double[] results = new double[TESTS];

            // Puts generated values through function
            for(int k = 0; k < TESTS; k++){
                results[k] = f.calculate(nums.get(k), d[i]);
            }

            // Calculates average time to complete computation
            long avg = (f.getAvgTime())/(long)TESTS;

            // Exports results to CSV file
            export(f.name(), d[i], avg, results);
            // resets avgTime to zero
            f.resetAvgTime();
        }
    }

    /*
        Function: generate()
        Description: Generates a number arrayLists determined by 'TESTS' of length 'd' within the given range using the
            Mersenne Twister pseudo-number generator.
        Params: double[] range, int d
        Return: ArrayList<ArrayList<Double>>
     */
    public static ArrayList<ArrayList<Double>> generate(double[] range, int d){

        // Range for given function
        double min = range[0];
        double max = range[1];

        // Returned values from the calculate function
        ArrayList<ArrayList<Double>> result = new ArrayList<>();

        for(int i = 0; i < TESTS; i ++){
            ArrayList<Double> inner = new ArrayList<>();
            for(int j = 0; j < d; j++){

                // Creates new instance of Mersenne Twister pseudo-number generator
                MTRandom rand = new MTRandom();
                double generated = min + (max - min) * rand.nextDouble();

                inner.add(generated);
            }
            result.add(inner);
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
