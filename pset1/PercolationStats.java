
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] parray; 
    private int trial;
    private int size;
    private int call;
    
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException();
        parray = new double[trials];
        trial = trials;
        size = n;
        for (int k = 0; k < trials; k++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, size+1);
                int col = StdRandom.uniform(1, size+1);
                if (!p.isOpen(row, col)) {
                    p.open(row, col);
                }
            }
            parray[k] = (double) p.numberOfOpenSites()/(size*size);
        }
    }
    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(parray);
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        if (trial == 1)
            return Double.NaN;
        return StdStats.stddev(parray);
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return StdStats.mean(parray) - 1.96* StdStats.stddev(parray)/(Math.sqrt(trial));
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return StdStats.mean(parray) + 1.96* StdStats.stddev(parray)/(Math.sqrt(trial));
    } 

    public static void main(String[] args)        // test client (described below)
    {
        PercolationStats p_states = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[0]));
        System.out.println("mean                          = "+p_states.mean());
        System.out.println("stddev                        = "+p_states.stddev());
        System.out.println("95% confidence interval       = ["+p_states.confidenceLo()+","+p_states.confidenceHi()+"]");
        
    }
}