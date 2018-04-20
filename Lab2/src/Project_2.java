/*
    Blake Calvin
    CS 471 - Optimization
    Project 2
    Last Updated - 4/12/18
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Project_2 {

    // Constants
    private static final int TESTS = 30;

    // All fitness equations
    public static FitnessFormula[] list = {new Schwefel(), new FirstDeJong(), new Rosenbrock(), new Rastrigin(), new Griewangk(),
            new SineEnvelopeSineWave(), new StretchedVSineWave(), new AckeysOne(), new AckeysTwo(), new EggHolder(), new Rana(),
            new Pathological(), new Michalewicz(), new MastersCosineWave(), new ShekelsFoxhole()};

    public static void main(String[] args){

        if(args.length<3){
            System.out.println("Error: missing arguments [Algorithm #] [# of Dimensions] [# of Iterations]");
            return;
        }

        int algorithm = Integer.parseInt(args[0]);
        int dimensions = Integer.parseInt(args[1]);
        int iterations = Integer.parseInt(args[2]);

        switch (algorithm){
            case 1:
                new Test(new BlindSearch(), list, dimensions, iterations, TESTS);
                break;
            case 2:
                new Test(new LocalSearch(), list, dimensions, iterations, TESTS);
                break;
            case 3:

                break;
        }

    }

}

//--- Algorithms -------------------------------------------------------------

class BlindSearch extends Algorithm{

    public ArrayList<Double> calculate(int iterations, FitnessFormula f, int d){
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

        fBest = fitness0;
        count += iterations;
        return argBest;
    }

    String getName() {
        return "Blind Search";
    }
}

class LocalSearch extends Algorithm{

    private double delta = .11;

    ArrayList<Double> calculate(int iterations, FitnessFormula f, int d) {

        ArrayList<Double> argBest = generate(f.range(), d);
        boolean tau = true;

        while(tau){

            tau = false;
            ArrayList<Double> argLoc = empiricalGradient(argBest, f, d);
            withinBounds(f, argLoc);

            if(f.calculate(argLoc, d) < f.calculate(argBest, d)){
                argBest = argLoc;
                tau = true;
            }

            count ++;
        }

        fBest = f.calculate(argBest, d);
        return argBest;
    }

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

    public boolean scalable(){
        return false;
    }
}

