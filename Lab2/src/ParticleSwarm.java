import FitnessFormulas.FitnessFormula;

import java.util.ArrayList;

public class ParticleSwarm extends Algorithm{

    //Parameters
    //k = 0.9
    //pbest = c1 = 0.8
    //gbest = c2 = 1.2

    int iterations;
    FitnessFormula f;
    int dim;
    int particles;
    double gBest;
    ArrayList<Double> pBest;
    double[] bounds;
    double k;
    double c1;
    double c2;



    public ParticleSwarm(int iterations, FitnessFormula f, int dim, int particles, double[] bounds, double k, double c1, double c2){
        this.iterations = iterations;
        this.f = f;
        this.dim = dim;
        this.particles = particles;
        this.bounds = bounds;
        this.k = k;
        this.c1 = c1;
        this.c2 = c2;
        pBest = new ArrayList<>();
    }

    ArrayList<Double> run() {

        Population p = new Population(particles, dim);
        ArrayList<Double> v = new ArrayList<>();

        for(int i = 0; i < particles; i++){
            initParticle(p, i);
            double r = random(0, (0.5*(bounds[1]-bounds[0])));
            v.add(r);
        }

        p.evaluate(f);
        pBest.addAll(p.f);
        gBest = min(pBest);

        for(int i = 0; i < iterations; i++){
            for(int j = 0; j < particles; j++){

            }
        }

        return null;
    }

    void initParticle(Population p, int x){
        for(int i = 0; i < dim; i++){
            double r = random(bounds[0], bounds[1]);
            p.p.get(x).add(r);
        }
    }

    double min(ArrayList<Double> pBest){
        double min = 0.0;
        for(double p: pBest){
            if(p < gBest){
                gBest = p;
            }
        }
        return min;
    }

    String getName() {
        return "Particle Swarm";
    }
}
