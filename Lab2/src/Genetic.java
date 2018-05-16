/**
 @author Blake Calvin
 CS 471 - Optimization
 Project 3
 @date 5/7/18
 */
import FitnessFormulas.FitnessFormula;
import java.util.ArrayList;
import java.util.List;

/**
 * GA algorithm class
 */
class Genetic extends Algorithm {

    public FitnessFormula f; //! Fitness formula
    private int ns; //! ns
    private int dim; //! Dimension size
    private double[] bounds; //! bounds of f
    private int tMax; //! max iterations
    private double cr; //! crossover rate
    private M m;
    private double er; //! elitism rate

    /**
     * Initializes GA test
     * @param f
     * @param ns
     * @param dim
     * @param bounds
     * @param tMax
     * @param cr
     * @param mRate
     * @param mRange
     * @param mPrecision
     * @param er
     */
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
     * Runs GA test
     * @return
     */
    ArrayList<Double> run() {

        int elitism = (int)er * ns;
        Population p = new Population(ns, dim);
        randomInit(p, bounds);
        p.evaluate(f);
        int t = 1;

        while(t <= tMax){
            p.c.clear();
            Population newP = new Population();
            for(int s =  0; s < ns; s+= 2){

                ArrayList<ArrayList<Double>> parents = select(p);
                ArrayList<ArrayList<Double>> offspring = crossover(parents.get(0), parents.get(1), cr);

                mutate(offspring.get(0), m, bounds);
                mutate(offspring.get(1), m, bounds);

                newP.p.add(offspring.get(0));
                newP.p.add(offspring.get(1));
                newP.ns += 2;
            }
            newP.dim = newP.p.get(0).size();
            newP.evaluate(f);
            reduce(p, newP, elitism);

            getCost(p);
            p.getBestSolutionByCost();
            t++;
        }
        fBest = p.bestCost; // Might need to change this to bestCost
        return p.bestSolutionByCost;
    }

    /**
     * gets total cost of population
     * @param p
     */
    void getCost(Population p) {
        for(int s = 0; s < ns; s++){
            if(p.f.get(s)>=0){
                p.c.add((1/(1+p.f.get(s))));
            }
            else{
                p.c.add((1 + Math.abs(p.f.get(s))));
            }
            p.totalCost += p.c.get(s);
        }
    }

    /**
     * selects parents for crossover
     * @param p
     * @return
     */
    ArrayList<ArrayList<Double>> select(Population p){
        ArrayList<ArrayList<Double>> r = new ArrayList<>();
        ArrayList<Double> p1 = selectParent(p);
        r.add(p1);
        ArrayList<Double> p2 = selectParent(p);
        r.add(p2);
        return r;
    }

    /**
     * selects parent
     * @param p
     * @return
     */
    ArrayList<Double> selectParent(Population p){
        double r = random(1,p.totalFitness);
        int s = 0;
        while(s < ns-1 && r > 0){
            r -= p.f.get(s);
            s += 1;
        }
        return p.p.get(s);
    }

    /**
     * crossover between two parents
     * @param p1
     * @param p2
     * @param cr
     * @return
     */
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

    /**
     * mutation of solutions
     * @param s
     * @param m
     * @param bounds
     */
    void mutate(ArrayList<Double> s, M m, double[] bounds) {
        for(int i = 0; i < dim; i++){
            if(random(0,1) < m.rate){
                double sTemp = s.get(i);
                s.set(i, sTemp + random(-1,1)*(bounds[1]-bounds[0])*m.range*Math.pow(2,(-1*random(0,1)*m.precision)));
            }
        }
    }

    /**
     * combines new population with old population
     * @param p
     * @param newP
     * @param e
     */
    void reduce(Population p, Population newP, int e){
        p.quicksort(0, p.p.size()-1);
        newP.quicksort(0, newP.p.size()-1);
        for(int i = 0; i < e; i++){
            newP.p.set(e+1-i, p.p.get(i));
            newP.f.set(e+1-i, p.f.get(i));
        }

        //swap
        swapData(p, newP);
    }

    /**
     * adds parents to offspring
     * @param p1
     * @param p2
     * @return
     */
    ArrayList<Double> join(List<Double> p1, List<Double> p2){
        ArrayList<Double> o = new ArrayList<>();
        o.addAll(p1);
        o.addAll(p2);
        return o;
    }

    /**
     * swap data of new population and old population
     * @param p
     * @param newP
     */
    void swapData(Population p, Population newP){
        ArrayList<ArrayList<Double>> tempP = new ArrayList<>();
        tempP.addAll(p.p);
        ArrayList<Double> tempF = p.f;

        p.f.clear();
        p.f.addAll(newP.f);
        newP.f.clear();
        newP.f.addAll(tempF);

        p.p.clear();
        p.p.addAll(newP.p);
        newP.p.clear();
        newP.p.addAll(tempP);
    }

    /**
     * returns name of algorithm
     * @return
     */
    String getName() {
        return "Genetic Algorithm";
    }

    /**
     * Stores Mutation variables
     */
    class M {
        public double rate;
        public double range;
        public double precision;

        /**
         * Initializes M class
         * @param rate
         * @param range
         * @param precision
         */
        public M(double rate, double range, double precision){
            this.rate = rate;
            this.range = range;
            this.precision = precision;
        }
    }
}


