/**
 @author Blake Calvin
 CS 471 - Optimization
 Project 3
 @date 5/7/18
 */
import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

public class Population {

    public int dim;
    public int ns;

    public ArrayList<ArrayList<Double>> p;

    public ArrayList<Double> f; //General fitness
    public ArrayList<Double> c; //Algorithm specific cost

    public double totalCost;
    public double bestCost;
    public double totalFitness;
    public double bestFitness;
    public ArrayList<Double> bestSolutionByCost;
    public ArrayList<Double> bestSolutionByFit;


    /**
     * generic initialization
     */
    public Population(){
        dim = 0;
        ns = 0;
        p = new ArrayList<>();
        f = new ArrayList<>();
        c = new ArrayList<>();
        bestSolutionByCost = new ArrayList<>();
        bestSolutionByFit = new ArrayList<>();
    }

    /**
     * Initialize population
     * @param ns
     * @param dim
     */
    public Population(int ns, int dim){
        this.dim = dim;
        this.ns = ns;
        p = new ArrayList<>();
        for(int i = 0; i < ns; i++){
            p.add(new ArrayList<>());
        }
        f = new ArrayList<>();
        c = new ArrayList<>();
        bestSolutionByCost = new ArrayList<>();
        bestSolutionByFit = new ArrayList<>();
    }

    /**
     * evaluates population to determine fitness
     * @param fit
     */
    public void evaluate(FitnessFormula fit){
        for(int i = 0; i < ns; i++){
            Double fitness = fit.calculate(p.get(i), dim);
            f.add(fitness);
        }
    }

    /**
     * gets best solution by cost
     */
    public void getBestSolutionByCost(){
        bestCost = c.get(0);
        for(int i = 1; i < ns; i++){
            if(f.get(i) < bestCost){
                bestCost = c.get(i);
                bestSolutionByCost = p.get(i);
            }
        }
    }

    /**
     * gets best solution by fitness
     */
    public void getBestSolutionByFit(){
        bestSolutionByFit.clear();
        bestFitness = f.get(0);
        bestSolutionByFit.addAll(p.get(0));
        for(int i = 1; i < ns; i++){
            if(f.get(i) < bestFitness){
                bestSolutionByFit.clear();
                bestFitness = f.get(i);
                bestSolutionByFit.addAll(p.get(i));
            }
        }
    }

    /**
     * partition for quicksort method
     * @param low
     * @param high
     * @return
     */
    int partition(int low, int high)
    {
        double pivot = f.get(high);
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or
            // equal to pivot
            if (f.get(j) <= pivot)
            {
                i++;

                // swap
                double tempF = f.get(i);
                ArrayList<Double> tempS = p.get(i);

                p.set(i,p.get(j));
                f.set(i,f.get(j));
                p.set(j,tempS);
                f.set(j,tempF);
            }
        }

        // swap i+1 and high (or pivot)
        double tempF = f.get(i+1);
        ArrayList<Double> tempS = p.get(i+1);

        p.set(i+1,p.get(high));
        f.set(i+1,f.get(high));
        p.set(high,tempS);
        f.set(high,tempF);

        return i+1;
    }

    /**
     * quicksort function in ascending order
     * @param low
     * @param high
     */
    void quicksort(int low, int high)
    {
        if (low < high)
        {
            /* pi is partitioning index, arr[pi] is
              now at right place */
            int pi = partition(low, high);

            // Recursively sort elements before
            // partition and after partition
            quicksort(low, pi-1);
            quicksort(pi+1, high);
        }
    }


}

