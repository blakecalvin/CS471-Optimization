package FitnessFormulas;/*
    Blake Calvin
    CS 471 - Optimization
    Project 1
    Last Updated - 4/4/18
 */

import java.util.ArrayList;

/*
    Abstract class for creation of fitness functions
 */
public abstract class FitnessFormula {

    // Variables
    public long avgTime = 0;

    // Abstract methods
    public abstract double calculate(ArrayList<Double> v, int d);
    public abstract double[] range();
    public abstract String name();

    // methods
    public long getAvgTime(){
        return avgTime;
    }
    void resetAvgTime(){
        avgTime = 0;
    }
    public boolean scalable(){
        return true;
    }
}
