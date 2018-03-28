import java.lang.Math;
import java.util.*;

public class Project_1 {

    // #1
    public double schwefel(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += -(v.get(i))*Math.sin(Math.sqrt(Math.abs(v.get(i))));
        }
        return s;
    }

    // #2
    public double firstDeJong(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.pow(v.get(i),2);
        }
        return s;
    }

    // #3
    public double rosenbrock(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += (100*((Math.pow(v.get(i),2))-v.get(i+1))) + Math.pow((1-v.get(i)),2);
        }
        return s;
    }

    // #4
    public double rastrigin(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.pow(v.get(i),2)-(10*Math.cos(2*Math.PI*v.get(i)));
        }
        s = s*2*d;
        return s;
    }

    // #5
    public double griewangk(ArrayList<Double> v, int d){
        double s = 0.0;
        double p = 1.0;
        for(int i = 0; i < d; i++){
            s += (Math.pow(v.get(i),2)/4000);
            p = p * Math.cos((v.get(i)/(Math.sqrt(i))));
        }
        s = 1 + s - p;
        return s;
    }

    // #6
    public double sineEnvelopeSineWave(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += 0.5 + (Math.sin(Math.pow((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)-0.5),2)))/Math.pow((1+0.001*(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))),2);
        }
        s = -s;
        return s;
    }

}
