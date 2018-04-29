import java.util.ArrayList;

public class Population {

    private int dim;
    private int ns;
    private ArrayList<ArrayList<Double>> p;
    public ArrayList<Double> f;
    public ArrayList<Double> c;

    public Population(){
        dim = 0;
        ns = 0;
        p = new ArrayList<>();
        f = new ArrayList<>();
        c = new ArrayList<>();
    }

    public Population(int ns, int dim){
        this.dim = dim;
        this.ns = ns;
        p = new ArrayList<>();
        f = new ArrayList<>();
        c = new ArrayList<>();
    }

    public double getValue(int row, int col){
        return p.get(row).get(col);
    }

    public ArrayList<Double> getSol(int row){
        return p.get(row);
    }

    public boolean addValue(int solution, double value){
        return p.get(solution).add(value);
    }

    public boolean addSol(ArrayList<Double> solution){
        if(ns == 0 && dim == 0){
            ns++;
            dim = solution.size();
        }
        if(solution.size() == dim){
            return p.add(solution);
        }
        return false;
    }

    public void replace(int row, ArrayList<Double> solution){
        p.set(row, solution);
    }

    public void replace(int row, int col, double value){
        p.get(row).set(col, value);
    }


}
