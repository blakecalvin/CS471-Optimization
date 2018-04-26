import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// #15
class ShekelsFoxhole extends FitnessFormula {

    private int m = 30;
    private ArrayList<Double> c;
    private ArrayList<ArrayList<Double>> a;

    // When object is created, read in values from text file
    public ShekelsFoxhole(){

        String fileName = "Shekel's_Foxhole_Data.txt";

        try{
            Scanner s = new Scanner(new File(fileName));
            s.useDelimiter("\\Z");

            c = new ArrayList<>();

            // Parse c value array
            String file = s.next();
            String[] var = file.split(";");
            String[] c1 = var[0].split("\\{");
            String[] c2 = c1[1].split("}");
            String[] c3 = c2[0].split(",");
            for(int i=0; i<c3.length; i++){
                c.add(Double.parseDouble(c3[i]));
            }

            a = new ArrayList<>();

            // Parse a value array
            String[] a1 = var[1].split("\\{");
            for(int i=2; i < a1.length; i++){
                ArrayList<Double> inner = new ArrayList<>();
                String[] a2 = a1[i].split("}");
                String[] a3 = a2[0].split(",");
                for(int j=0; j<a3.length; j++){
                    inner.add(Double.parseDouble(a3[j]));
                }
                a.add(inner);
            }

            s.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Error: File not found.");
        }
    }

    public double calculate(ArrayList<Double> v, int d){
        long start = System.nanoTime();
        double s = 0.0;
        for(int i = 0; i < m; i++){
            double s2 = 0.0;
            for(int j = 0; j < d; j++){
                s2 += Math.pow((v.get(j)-a.get(i).get(j)),2);
            }
            s += 1/(c.get(i)+s2);
        }
        s = -s;
        avgTime += System.nanoTime() - start;
        return s;
    }

    public double[] range() {
        double[] r = {0,10};
        return r;
    }

    public String name(){
        return "Shekel's Foxhole";
    }

    public boolean scalable(){
        return false;
    }
}
