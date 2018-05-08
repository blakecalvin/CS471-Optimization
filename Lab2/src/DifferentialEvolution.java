import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

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

    ArrayList<Double> run() {

        Population G = new Population(ns, dim);
        randomInit(G, bounds);
        int g = 0;
        G.evaluate(ff);


        while(g < gMax){
            for(int i = 0; i < ns; i++){
                ArrayList<Double> u = new ArrayList<>();
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
                        u = rand_2_exp(G, G.p.get(i), r1, r2, r3, r4, i);
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

    //#1
    ArrayList<Double> best_1_exp(Population G, ArrayList<Double> x, int r2, int r3){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        for(int j = 0; j < dim; j++){
            v.add(G.bestSolutionByFit.get(j)+f*(G.p.get(r2).get(j)-G.p.get(r3).get(j)));
        }
        ArrayList<Double> u = expCrossover(x, v);
        return u;
    }

    //#2
    ArrayList<Double> rand_1_exp(Population G, ArrayList<Double> x, int r1, int r2, int r3){
        ArrayList<Double> v = new ArrayList<>();

        for(int j = 0; j < dim; j++){
            v.add(G.p.get(r1).get(j)+f*(G.p.get(r2).get(j)-G.p.get(r3).get(j)));
        }
        ArrayList<Double> u = expCrossover(x, v);
        return u;
    }

    //#3
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

    //#4
    ArrayList<Double> best_2_exp(Population G, ArrayList<Double> x, int r1, int r2, int r3, int r4){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        for(int j = 0; j < dim; j++){
            v.add(G.bestSolutionByFit.get(j)+f*(G.p.get(r1).get(j)+G.p.get(r2).get(j)-G.p.get(r3).get(j)-G.p.get(r4).get(j)));
        }
        ArrayList<Double> u = expCrossover(x, v);
        return u;
    }

    //#5
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

    //#6
    ArrayList<Double> best_1_bin(Population G, ArrayList<Double> x, int r2, int r3){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        for(int j = 0; j < dim; j++){
            v.add(G.bestSolutionByFit.get(j)+f*(G.p.get(r2).get(j)-G.p.get(r3).get(j)));
        }
        ArrayList<Double> u = binCrossover(x, v);
        return u;
    }

    //#7
    ArrayList<Double> rand_1_bin(Population G, ArrayList<Double> x, int r1, int r2, int r3){
        ArrayList<Double> v = new ArrayList<>();

        for(int j = 0; j < dim; j++){
            v.add(G.p.get(r1).get(j)+f*(G.p.get(r2).get(j)-G.p.get(r3).get(j)));
        }
        ArrayList<Double> u = binCrossover(x, v);
        return u;
    }

    //#8
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

    //#9
    ArrayList<Double> best_2_bin(Population G, ArrayList<Double> x, int r1, int r2, int r3, int r4){
        ArrayList<Double> v = new ArrayList<>();
        G.getBestSolutionByFit();

        for(int j = 0; j < dim; j++){
            v.add(G.bestSolutionByFit.get(j)+f*(G.p.get(r1).get(j)-G.p.get(r2).get(j)-G.p.get(r3).get(j)-G.p.get(r4).get(j)));
        }

        ArrayList<Double> u = binCrossover(x, v);
        return u;
    }

    //#10
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


    void selection(Population G, int i, ArrayList<Double> u){
        if(ff.calculate(u, dim) <= G.f.get(i)){
            G.p.set(i, u);
        }
    }

    void randomInit(Population population, double[] bounds) {

        for(int i = 0; i < ns; i++){
            for(int j = 0; j < dim; j++){
                double r = random(bounds[0], bounds[1]);
                population.p.get(i).add(r);
            }
        }
    }

    String getName() {
        return "Differential Evolution";
    }
}
