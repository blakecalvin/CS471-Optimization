import java.util.ArrayList;

public interface FitnessFormula {
    double calculate(ArrayList<Double> v, int d);
    double[] range();
}