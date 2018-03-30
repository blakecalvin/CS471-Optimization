/*
    Blake Calvin
    CS 471 - Optimization
    Project 1
    Last Updated - 3/29/18
 */

import java.lang.Math;
import java.util.*;

public class Project_1 {

    private static final int TESTS = 30;

    public static void main(String[] args){

    }

    public ArrayList<ArrayList<Double>> generate(double[] range, int d){
        double min = range[0];
        double max = range[1];
        ArrayList<ArrayList<Double>> result = new ArrayList<>();

        //Mersenne Twister
        for(int i = 0; i < TESTS; i ++){
            for(int j = 0; j < d; j++){
                //generate number
                double generated = 0.0;
                result.get(i).add(j, generated);
            }
        }

        return result;
    }
}

//--- Fitness Function Definitions -------------------------------------------

// #1
class Schwefel implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += -(v.get(i))*Math.sin(Math.sqrt(Math.abs(v.get(i))));
        }
        return s;
    }

    public double[] range() {
        double[] r = {-512,512};
        return r;
    }
}

// #2
class FirstDeJong implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.pow(v.get(i),2);
        }
        return s;
    }

    public double[] range() {
        double[] r = {-100,100};
        return r;
    }
}

// #3
class Rosenbrock implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += (100*((Math.pow(v.get(i),2))-v.get(i+1))) + Math.pow((1-v.get(i)),2);
        }
        return s;
    }

    public double[] range() {
        double[] r = {-100,100};
        return r;
    }
}

// #4
class Rastrigin implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.pow(v.get(i),2)-(10*Math.cos(2*Math.PI*v.get(i)));
        }
        s = s*2*d;
        return s;
    }

    public double[] range() {
        double[] r = {-30,30};
        return r;
    }
}

// #5
class Griewangk implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        double p = 1.0;
        for(int i = 0; i < d; i++){
            s += (Math.pow(v.get(i),2)/4000);
            p = p * Math.cos((v.get(i)/(Math.sqrt(i))));
        }
        s = 1 + s - p;
        return s;
    }

    public double[] range() {
        double[] r = {-500,500};
        return r;
    }
}

// #6
class SineEnvelopeSineWave implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += 0.5 + Math.pow(Math.sin((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)-0.5)),2)/Math.pow((1+0.001*(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))),2);
        }
        s = -s;
        return s;
    }

    public double[] range() {
        double[] r = {-30,30};
        return r;
    }
}

// #7
class StretchedVSineWave implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += (Math.pow((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)),.25)*Math.pow(Math.sin((50*Math.pow((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)),.1))),2)+1);
        }
        return s;
    }

    public double[] range() {
        double[] r = {-30,30};
        return r;
    }
}

// #8
class AckeysOne implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += (1/Math.pow((Math.E),.2))*Math.sqrt(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))+3*(Math.cos(2*v.get(i))+Math.sin(2*v.get(i+1)));
        }
        return s;
    }

    public double[] range() {
        double[] r = {-32,32};
        return r;
    }
}

// #9
class AckeysTwo implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += 20 + Math.E - (20/(Math.pow(Math.E,.2)*Math.sqrt((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))/2)))-Math.pow(Math.E,0.5*(Math.cos(2*Math.PI*v.get(i))+Math.cos(2*Math.PI*v.get(i+1))));
        }
        return s;
    }

    public double[] range() {
        double[] r = {-32,32};
        return r;
    }
}

// #10
class EggHolder implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += -v.get(i)*Math.sin(Math.sqrt(Math.abs(v.get(i)-v.get(i+1)-47)))-(v.get(i+1)+47)*Math.sin(Math.sqrt(Math.abs(v.get(i+1)+47+(v.get(i)/2))));
        }
        return s;
    }

    public double[] range() {
        double[] r = {-500,500};
        return r;
    }
}

// #11
class Rana implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += v.get(i)*Math.sin(Math.sqrt(Math.abs(v.get(i+1)-v.get(i)+1)))*Math.cos(Math.sqrt(Math.abs(v.get(i+1)+v.get(i)+1)))+(v.get(i+1)+1)*Math.cos(Math.sqrt(Math.abs(v.get(i+1)-v.get(i)+1)))*Math.sin(Math.sqrt(Math.abs(v.get(i+1)+v.get(i)+1)));
        }
        return s;
    }

    public double[] range() {
        double[] r = {-500,500};
        return r;
    }
}

// #12
class Pathological implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += 0.5 + (Math.pow(Math.sin(Math.sqrt(100*Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))),2)-0.5)/(1+0.001*Math.pow((Math.pow(v.get(i),2)-2*v.get(i)*v.get(i+1)+Math.pow(v.get(i+1),2)),2));
        }
        return s;
    }

    public double[] range() {
        double[] r = {-100,100};
        return r;
    }
}

// #13
class Michalewicz implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.sin(v.get(i))*Math.pow((Math.sin((i*Math.pow(v.get(i),2))/Math.PI)),10);
        }
        s = -s;
        return s;
    }

    public double[] range() {
        double[] r = {0,Math.PI};
        return r;
    }
}

// #14
class MastersCosineWave implements FitnessFormula{

    public double calculate(ArrayList<Double> v, int d) {
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += Math.pow(Math.E,(-(1/8)*(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)+0.5*v.get(i+1)*v.get(i))))*Math.cos(4*Math.sqrt(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)+0.5*v.get(i+1)*v.get(i)));
        }
        s = -s;
        return s;
    }

    public double[] range() {
        double[] r = {-30,30};
        return r;
    }
}

// #15
class ShekelsFoxhole {

    public double calculate(ArrayList<Double> v, ArrayList<Double> c, ArrayList<ArrayList<Double>> a, int m, int n){
        double s = 0.0;
        for(int i = 0; i < m; i++){
            double s2 = 0.0;
            for(int j = 0; j < n; j++){
                ArrayList<Double> row = a.get(i);
                s2 += Math.pow((v.get(j)-row.get(j)),2);
            }
            s += 1/(c.get(i)+s2);
        }
        s = -s;
        return s;
    }

    public double[] range() {
        double[] r = {0,10};
        return r;
    }
}
