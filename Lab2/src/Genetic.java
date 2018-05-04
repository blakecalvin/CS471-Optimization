import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;
import java.util.List;

class Genetic extends Algorithm {

    public FitnessFormula f;
    private int ns;
    private int dim;
    private double[] bounds;
    private int tMax;
    private double cr;
    private M m;
    private double er;


    public Genetic(FitnessFormula f, int ns, int dim, double[] bounds, int tMax, double cr, double mRate, double mRange, double mPrecision, double er){
        this.f = f;
        this.ns = ns;
        this.dim = dim;
        this.bounds = bounds;
        this.tMax = tMax;
        this.cr = cr;
        this.m = new M(mRate,mRange,mPrecision);
        this.er = er;
    }

    void randomInit(Population population, double[] bounds) {

        for(int i = 0; i < ns; i++){
            for(int j = 0; j < dim; j++){
                double r = random(bounds[0], bounds[1]);
                population.addValue(i, r);
            }
        }
    }

    ArrayList<Double> run() {

        int elitism = (int)er * ns;
        Population p = new Population(ns, dim);
        randomInit(p, bounds);
        p.evaluate(f);
        int t = 1;

        while(t <= tMax){
            Population newP = new Population(ns, dim);
            for(int s =  1; s <= ns; s+= 2){

                ArrayList<ArrayList<Double>> parents = select(p);
                ArrayList<ArrayList<Double>> offspring = crossover(parents.get(0), parents.get(1), cr);

                mutate(offspring.get(0), m, bounds);
                mutate(offspring.get(1), m, bounds);

                newP.addSol(offspring.get(0));
                newP.addSol(offspring.get(1));
            }
            newP.evaluate(f);
            reduce(p, newP, elitism);
            getCost(p);
            p.getBestSolution();
        }
        fBest = p.bestCost; // Might need to change this to bestCost
        return p.bestSolution;
    }

    void getCost(Population p) {
        for(int s = 0; s < ns; s++){
            if(p.f.get(s)>=0){
                p.c.set(s,(1/(1+p.f.get(s))));
            }
            else{
                p.c.set(s, (1 + Math.abs(p.f.get(s))));
            }
            p.totalCost += p.c.get(s);
        }
    }

    ArrayList<ArrayList<Double>> select(Population p){
        ArrayList<ArrayList<Double>> r = new ArrayList<>();
        ArrayList<Double> p1 = selectParent(p);
        r.add(p1);
        ArrayList<Double> p2 = selectParent(p);
        r.add(p2);
        return r;
    }

    ArrayList<Double> selectParent(Population p){
        double r = random(1,p.totalFitness);
        int s = 1;
        while(s <= ns && r > 0){
            r -= p.f.get(s);
            s += 1;
        }
        return p.getSol(s);
    }

    ArrayList<ArrayList<Double>> crossover(ArrayList<Double> p1, ArrayList<Double> p2, double cr) {
        ArrayList<ArrayList<Double>> o = new ArrayList<>();
        if(random(0,1)<cr){
            int d = (int)random(0,dim);
            o.add(join(p1.subList(0,d),p2.subList(d,dim)));
            o.add(join(p2.subList(0,d),p1.subList(d,dim)));
        }
        else{
            o.add(p1);
            o.add(p2);
        }
        return o;
    }

    void mutate(ArrayList<Double> s, M m, double[] bounds) {
        for(int i = 0; i < dim; i++){
            if(random(0,1) < m.rate){
                double sTemp = s.get(i);
                s.set(i, sTemp + random(-1,1)*(bounds[1]-bounds[0])*m.range*Math.pow(2,(-1*random(0,1)*m.precision)));
            }
        }
    }

    void reduce(Population p, Population newP, int e){
        p.quicksort(0, p.size());
        newP.quicksort(0, newP.size());
        for(int i = 0; i < e; i++){
            newP.replace(e+1-i,p.getSol(i));
            newP.f.set(e+1-i, p.f.get(i));
        }

        //swap
        swapData(p, newP);
    }

    ArrayList<Double> join(List<Double> p1, List<Double> p2){
        ArrayList<Double> o = new ArrayList<>();
        o.addAll(p1);
        o.addAll(p2);
        return o;
    }

    void swapData(Population p, Population newP){
        ArrayList<ArrayList<Double>> tempP = new ArrayList<>();
        tempP.addAll(p.getP());
        ArrayList<Double> tempF = p.f;

        p.f.clear();
        p.f.addAll(newP.f);
        newP.f.clear();
        newP.f.addAll(tempF);

        p.eraseP();
        p.addAll(newP.getP());
        newP.eraseP();
        newP.addAll(tempP);
    }

    String getName() {
        return "Genetic Algorithm";
    }

    class M {
        public double rate;
        public double range;
        public double precision;

        public M(double rate, double range, double precision){
            this.rate = rate;
            this.range = range;
            this.precision = precision;
        }
    }
}


