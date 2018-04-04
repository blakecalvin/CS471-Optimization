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

    private static final int TESTS = 30;
    private static final int[] D = {10,20,30};
    private static ArrayList<Long> time = new ArrayList<>();

    public static void main(String[] args){

        // Could make seperate thread for each test??

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

    // test function for various dimensions
    public static void test(FitnessFormula f, int[] d){

        for(int i = 0; i<d.length; i++){

            ArrayList<ArrayList<Double>> nums = generate(f.range(), d[i]);
            double[] results = new double[TESTS];

            for(int k = 0; k < TESTS; k++){
                results[k] = f.calculate(nums.get(k), d[i]);
            }
            long avg = (f.getAvgTime())/(long)TESTS;

            export(f.toString(), d[i], avg, results);
            f.resetAvgTime();
        }
    }

    public static ArrayList<ArrayList<Double>> generate(double[] range, int d){

        double min = range[0];
        double max = range[1];
        ArrayList<ArrayList<Double>> result = new ArrayList<>();

        // Mersenne Twister
        for(int i = 0; i < TESTS; i ++){
            ArrayList<Double> inner = new ArrayList<>();
            for(int j = 0; j < d; j++){

                MTRandom rand = new MTRandom();
                double generated = min + (max - min) * rand.nextDouble();

                inner.add(generated);
            }
            result.add(inner);
        }

        return result;
    }

    // Export data to csv file
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
            System.out.println("Error: cannot output to file.");
        }
    }
}

//--- Fitness Function Definitions -------------------------------------------

// #1
class Schwefel implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Schwefel";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #2
class FirstDeJong implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "1st De Jong";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #3
class Rosenbrock implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Rosenbrock";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #4
class Rastrigin implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Rastrigin";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #5
class Griewangk implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Griewangk";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #6
class SineEnvelopeSineWave implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Sine Envelope Sine Wave";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #7
class StretchedVSineWave implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Stretched V Sine Wave";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #8
class AckeysOne implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Ackey's One";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #9
class AckeysTwo implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Ackey's Two";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #10
class EggHolder implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Egg Holder";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #11
class Rana implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Rana";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #12
class Pathological implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Pathological";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #13
class Michalewicz implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Michalewicz";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #14
class MastersCosineWave implements FitnessFormula{

    public long avgTime = 0;

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

    public String toString(){
        return "Master's Cosine Wave";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}

// #15
class ShekelsFoxhole implements FitnessFormula{

    public long avgTime = 0;
    private int m = 30;
    private ArrayList<Double> c;
    private ArrayList<ArrayList<Double>> a;

    // When created read in data from file
    public ShekelsFoxhole(){

        String fileName = "Shekel's_Foxhole_Data.txt";

        try{
            Scanner s = new Scanner(new File(fileName));
            s.useDelimiter("\\Z");

            c = new ArrayList<>();

            // Parse c array
            String file = s.next();
            String[] var = file.split(";");
            String[] c1 = var[0].split("\\{");
            String[] c2 = c1[1].split("}");
            String[] c3 = c2[0].split(",");
            for(int i=0; i<c3.length; i++){
                c.add(Double.parseDouble(c3[i]));
            }

            a = new ArrayList<>();

            // Parse a array
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

    public String toString(){
        return "Shekel's Foxhole";
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void resetAvgTime() {
        avgTime = 0;
    }
}
