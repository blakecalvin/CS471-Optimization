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
            s += 0.5 + Math.pow(Math.sin((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)-0.5)),2)/Math.pow((1+0.001*(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))),2);
        }
        s = -s;
        return s;
    }

    // #7
    public double stretchedVSineWave(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += (Math.pow((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)),.25)*Math.pow(Math.sin((50*Math.pow((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)),.1))),2)+1);
        }
        return s;
    }

    // #8
    public double AckeysOne(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += (1/Math.pow((Math.E),.2))*Math.sqrt(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))+3*(Math.cos(2*v.get(i))+Math.sin(2*v.get(i+1)));
        }
        return s;
    }

    // #9
    public double AckeysTwo(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += 20 + Math.E - (20/(Math.pow(Math.E,.2)*Math.sqrt((Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))/2)))-Math.pow(Math.E,0.5*(Math.cos(2*Math.PI*v.get(i))+Math.cos(2*Math.PI*v.get(i+1))));
        }
        return s;
    }

    // #10
    public double eggHolder(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += -v.get(i)*Math.sin(Math.sqrt(Math.abs(v.get(i)-v.get(i+1)-47)))-(v.get(i+1)+47)*Math.sin(Math.sqrt(Math.abs(v.get(i+1)+47+(v.get(i)/2))));
        }
        return s;
    }

    // #11
    public double rana(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += v.get(i)*Math.sin(Math.sqrt(Math.abs(v.get(i+1)-v.get(i)+1)))*Math.cos(Math.sqrt(Math.abs(v.get(i+1)+v.get(i)+1)))+(v.get(i+1)+1)*Math.cos(Math.sqrt(Math.abs(v.get(i+1)-v.get(i)+1)))*Math.sin(Math.sqrt(Math.abs(v.get(i+1)+v.get(i)+1)));
        }
        return s;
    }

    // #12
    public double pathological(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += 0.5 + (Math.pow(Math.sin(Math.sqrt(100*Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2))),2)-0.5)/(1+0.001*Math.pow((Math.pow(v.get(i),2)-2*v.get(i)*v.get(i+1)+Math.pow(v.get(i+1),2)),2));
        }
        return s;
    }

    // #13
    public double michalewicz(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d; i++){
            s += Math.sin(v.get(i))*Math.pow((Math.sin((i*Math.pow(v.get(i),2))/Math.PI)),10);
        }
        s = -s;
        return s;
    }

    // #14
    public double mastersCosineWave(ArrayList<Double> v, int d){
        double s = 0.0;
        for(int i = 0; i < d-1; i++){
            s += Math.pow(Math.E,(-(1/8)*(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)+0.5*v.get(i+1)*v.get(i))))*Math.cos(4*Math.sqrt(Math.pow(v.get(i),2)+Math.pow(v.get(i+1),2)+0.5*v.get(i+1)*v.get(i)));
        }
        s = -s;
        return s;
    }

    // #15
    public double shekelsFoxholes(ArrayList<Double> v, ArrayList<Double> c, ArrayList<ArrayList<Double>> a, int m, int n){
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
}

// #1
class schwefel implements FitnessFormula{

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
class firstDeJong implements FitnessFormula{
    
    public double calculate(ArrayList<Double> v, int d) {
        return 0;
    }

    public double[] range() {
        return new double[0];
    }
}

// #3


// #4


// #5


// #6
