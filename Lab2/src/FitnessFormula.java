/*
    Blake Calvin
    CS 471 - Optimization
    Project 1
    Last Updated - 4/4/18
 */

import java.util.ArrayList;

/*
    Abstract class for creation of fitness functions
 */
abstract class FitnessFormula {

    // Variables
    public long avgTime = 0;

    // Abstract methods
    abstract double calculate(ArrayList<Double> v, int d);
    abstract double[] range();
    abstract String name();

    // methods
    long getAvgTime(){
        return avgTime;
    }
    void resetAvgTime(){
        avgTime = 0;
    }
    boolean scalable(){
        return true;
    }
}
