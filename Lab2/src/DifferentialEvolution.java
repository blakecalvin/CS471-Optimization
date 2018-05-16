/**
 @author Blake Calvin
 CS 471 - Optimization
 Project 3
 @date 5/7/18
 */
import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

/**
 * DE Class
 */
public class DifferentialEvolution extends Algorithm {

    int dim;
    int gMax;
    int ns;
    FitnessFormula ff;
    double f;
    double cr;
    double[] bounds;
    int strategy;
    double lambda;

    /**
     * Initializes DE Algorithm
     * @param dim
     * @param gMax
     * @param ns
     * @param ff
     * @param f
     * @param cr
     * @param bounds
     * @param strategy
     */
    public DifferentialEvolution(int dim, int gMax,int ns, FitnessFormula ff, double f, double cr, double[] bounds, int strategy){
        this.dim = dim;
        this.gMax = gMax;
        this.ns = ns;
        this.ff = ff;
        this.f = f;
        this.cr = cr;
        this.bounds = bounds;
        this.strategy = strategy;
        lambda = f/2;
    }

    /**
     * Runs the DE algorithm on population
     * @return The best solution vector
     */
    ArrayList<Double> run() {

        Population G = new Population(ns, dim);
        randomInit(G, bounds);
        int g = 0;
        G.evaluate(ff);


        while(g < gMax){
            for(int i = 0; i < ns; i++){
                ArrayList<Double> u = new ArrayList<>();

                //Randomly generate r1,r2,r3,r4
                int r1, r2, r3, r4;
                do{
                    r1 = (int)random(0,ns);
                }while(r1 == i);
                do{
                    r2 = (int)random(0,ns);
                }while(r2 == r1 || r2 == i);
                do{
                    r3 = (int)random(0,ns);
                }while(r3 == r2 || r3 == r1 || r3 == i);
                do{
                    r4 = (int)random(0,ns);
                }while(r4 == r3 || r4 == r2 || r4 == r1 || r4 == i);

                //Chooses mutation strategy
                switch (strategy){
                    case 1:
                        u = best_1_exp(G, G.p.get(i), r2, r3);
                        break;
                    case 2:
                        u = rand_1_exp(G, G.p.get(i), r1, r2, r3);
                        break;
                    case 3:
                        u = randToBest_1_exp(G, G.p.get(i), r1, r2);
                        break;
                    case 4:
                        u = best_2_exp(G, G.p.get(i), r1, r2, r3, r4);
                        break;
                    case 5:
                        u = rand_2_exp(G, G.p.get(i), r1, r2, r3, r4, i);
                        break;
                    case 6:
                        u = best_1_bin(G, G.p.get(i), r2, r3);
                        break;
                    case 7:
                        u = rand_1_bin(G, G.p.get(i), r1, r2, r3);
                        break;
                    case 8:
                        u = randToBest_1_bin(G, G.p.get(i), r1, r2);
                        break;
                    case 9:
                        u = best_2_bin(G, G.p.get(i), r1, r2, r3, r4);
                        break;
                    case 10:
                        u = rand_2_bin(G, G.p.get(i), r1, r2, r3, r4, i);
                        break;
                }
                selection(G, i, u);

                G.f.clear();
                G.evaluate(ff);
            }
            g++;
        }
        G.getBestSolutionByFit();
        fBest = G.bestFitness;
        return G.bestSolutionByFit;
    }

    /**
     * Crossover between noisy vector and Xi
     * @param x
     * @param v
     * @return Arraylist u
     */
    ArrayList<Double> expCrossover(ArrayList<Double> x, ArrayList<Double> v){
        ArrayList<Double> u = new ArrayList<>();
        u.addAll(x);

        if(random(0,1) < cr){
            int n = (int)random(0,dim);
            int L = (int)random(0,dim);
            int counter = 0;

            while(counter < L){
                u.set((n+counter)%dim, v.get((n+counter)%dim));
                counter++;
            }
        }

        return u;
    }

    /**
     * Crossover between noisy vector and Xi
     * @param x
     * @param v
     * @return Arraylist u
     */
    ArrayList<Double> binCrossover(ArrayList<Double> x, ArrayList<Double> v){
        ArrayList<Double> u = new ArrayList<>();
        u.addAll(x);

        for(int j = 0; j < dim; j++){
            if(random(0,1) < cr){
                u.set(j, v.get(j));
            }
        }

        return u;
    }

    /**
     * best_1_exp mutation
     * @param G
     * @param x
     * @param r2
     * @param r3
     * @return new solution
     */
    ArrayList<Double> best_1_exp(Population G, ArrayList<Double> x, int r2, int r3){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        for(int j = 0; j < dim; j++){
            v.add(G.bestSolutionByFit.get(j)+f*(G.p.get(r2).get(j)-G.p.get(r3).get(j)));
        }
        ArrayList<Double> u = expCrossover(x, v);
        return u;
    }

    /**
     * rand_1_exp mutation
     * @param G
     * @param x
     * @param r1
     * @param r2
     * @param r3
     * @return new solution
     */
    ArrayList<Double> rand_1_exp(Population G, ArrayList<Double> x, int r1, int r2, int r3){
        ArrayList<Double> v = new ArrayList<>();

        for(int j = 0; j < dim; j++){
            v.add(G.p.get(r1).get(j)+f*(G.p.get(r2).get(j)-G.p.get(r3).get(j)));
        }
        ArrayList<Double> u = expCrossover(x, v);
        return u;
    }

    /**
     * randToBest_1_exp mutation
     * @param G
     * @param x
     * @param r1
     * @param r2
     * @return new solution
     */
    ArrayList<Double> randToBest_1_exp(Population G, ArrayList<Double> x, int r1, int r2){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        G.getBestSolutionByFit();
        for(int j = 0; j < dim; j++){
            v.add(x.get(j)+lambda*(G.bestSolutionByFit.get(j)-x.get(j))+f*(G.p.get(r1).get(j)-G.p.get(r2).get(j)));
        }
        ArrayList<Double> u = expCrossover(x, v);
        return u;
    }

    /**
     * best_2_exp mutation
     * @param G
     * @param x
     * @param r1
     * @param r2
     * @param r3
     * @param r4
     * @return new solution
     */
    ArrayList<Double> best_2_exp(Population G, ArrayList<Double> x, int r1, int r2, int r3, int r4){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        for(int j = 0; j < dim; j++){
            v.add(G.bestSolutionByFit.get(j)+f*(G.p.get(r1).get(j)+G.p.get(r2).get(j)-G.p.get(r3).get(j)-G.p.get(r4).get(j)));
        }
        ArrayList<Double> u = expCrossover(x, v);
        return u;
    }

    /**
     * rand_2_exp mutation
     * @param G
     * @param x
     * @param r1
     * @param r2
     * @param r3
     * @param r4
     * @param i
     * @return new solution
     */
    ArrayList<Double> rand_2_exp(Population G, ArrayList<Double> x, int r1, int r2, int r3, int r4, int i){
        ArrayList<Double> v = new ArrayList<>();
        int r5;
        do{
            r5 = (int)random(0,ns);
        }while(r5 == r4 || r5 == r3 || r5 == r2 || r5 == r1 || r5 == i);

        for(int j = 0; j < dim; j++){
            v.add(G.p.get(r5).get(j)+f*(G.p.get(r1).get(j)+G.p.get(r2).get(j)-G.p.get(r3).get(j)-G.p.get(r4).get(j)));
        }
        ArrayList<Double> u = expCrossover(x, v);
        return u;
    }

    /**
     * best_1_bin mutation
     * @param G
     * @param x
     * @param r2
     * @param r3
     * @return new solution
     */
    ArrayList<Double> best_1_bin(Population G, ArrayList<Double> x, int r2, int r3){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        for(int j = 0; j < dim; j++){
            v.add(G.bestSolutionByFit.get(j)+f*(G.p.get(r2).get(j)-G.p.get(r3).get(j)));
        }
        ArrayList<Double> u = binCrossover(x, v);
        return u;
    }

    /**
     * rand_1_bin mutation
     * @param G
     * @param x
     * @param r1
     * @param r2
     * @param r3
     * @return new solution
     */
    ArrayList<Double> rand_1_bin(Population G, ArrayList<Double> x, int r1, int r2, int r3){
        ArrayList<Double> v = new ArrayList<>();

        for(int j = 0; j < dim; j++){
            v.add(G.p.get(r1).get(j)+f*(G.p.get(r2).get(j)-G.p.get(r3).get(j)));
        }
        ArrayList<Double> u = binCrossover(x, v);
        return u;
    }

    /**
     * randToBest_1_bin mutation
     * @param G
     * @param x
     * @param r1
     * @param r2
     * @return new solution
     */
    ArrayList<Double> randToBest_1_bin(Population G, ArrayList<Double> x, int r1, int r2){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        G.getBestSolutionByFit();
        for(int j = 0; j < dim; j++){
            v.add(x.get(j)+lambda*(G.bestSolutionByFit.get(j)-x.get(j))+f*(G.p.get(r1).get(j)-G.p.get(r2).get(j)));
        }
        ArrayList<Double> u = binCrossover(x, v);
        return u;
    }

    /**
     * best_2_bin mutation
     * @param G
     * @param x
     * @param r1
     * @param r2
     * @param r3
     * @param r4
     * @return new solution
     */
    ArrayList<Double> best_2_bin(Population G, ArrayList<Double> x, int r1, int r2, int r3, int r4){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        for(int j = 0; j < dim; j++){
            v.add(G.bestSolutionByFit.get(j)+f*(G.p.get(r1).get(j)-G.p.get(r2).get(j)-G.p.get(r3).get(j)-G.p.get(r4).get(j)));
        }

        ArrayList<Double> u = binCrossover(x, v);
        return u;
    }

    /**
     * rand_2_bin mutation
     * @param G
     * @param x
     * @param r1
     * @param r2
     * @param r3
     * @param r4
     * @param i
     * @return new solution
     */
    ArrayList<Double> rand_2_bin(Population G, ArrayList<Double> x, int r1, int r2, int r3, int r4, int i){
        ArrayList<Double> v = new ArrayList<>();
        int r5;
        do{
            r5 = (int)random(0,ns);
        }while(r5 == r4 || r5 == r3 || r5 == r2 || r5 == r1 || r5 == i);

        for(int j = 0; j < dim; j++){
            v.add(G.p.get(r5).get(j)+f*(G.p.get(r1).get(j)+G.p.get(r2).get(j)-G.p.get(r3).get(j)-G.p.get(r4).get(j)));
        }

        ArrayList<Double> u = binCrossover(x, v);
        return u;
    }

    /**
     * selects bests solution
     * @param G
     * @param i
     * @param u
     */
    void selection(Population G, int i, ArrayList<Double> u){
        if(ff.calculate(u, dim) <= G.f.get(i)){
            G.p.set(i, u);
        }
    }

    /**
     * randomly initializes population
     * @param population
     * @param bounds
     */
    void randomInit(Population population, double[] bounds) {

        for(int i = 0; i < ns; i++){
            for(int j = 0; j < dim; j++){
                double r = random(bounds[0], bounds[1]);
                population.p.get(i).add(r);
            }
        }
    }

    /**
     * returns name of algorithm
     * @return
     */
    String getName() {
        return "Differential Evolution";
    }
}
