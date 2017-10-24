
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    private int size_n;
    private int[] state; 
    private int num_Open;
    private int[] parent;   // parent[i] = parent of i
    private int[] size;     // size[i] = number of sites in subtree rooted at i
    private int count;      // number of components
   
    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) throw new java.lang.IllegalArgumentException();
        count = n*n+2;
        parent = new int[count];
        size = new int[count];
        size_n = n;
        num_Open = 0;
        state = new int[n*n+2];
        for (int i = 0; i < count; i++) {
            parent[i] = i;
            size[i] = 1;
            state[i] = 0;
        }
        state[0] = 1;
        state[n*n+1] = 1;
    }

    private int find(int p) {
        validate(p);
        while (p != parent[p])
            p = parent[p];
        return p;
    }
    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n-1));  
        }
    }
    private boolean connected(int p, int q) {
        return find(p) == find(q);
    }
    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }

    public  void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (row <= 0 || row > size_n) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > size_n) throw new IndexOutOfBoundsException("column index i out of bounds");
        if (!isOpen(row, col)) {
            state[(row-1)*size_n+col] = 1;
            if ((col+1) <= size_n && isOpen(row, col+1))
                union((row-1)*size_n+col, (row-1)*size_n+col+1);
            if ((col-1) >= 1 && isOpen(row, col-1))
                union((row-1)*size_n+col, (row-1)*size_n+col-1);
            if ((row-1) >= 1 && isOpen(row-1, col))
                union((row-1)*size_n+col, (row-2)*size_n+col);
            if ((row+1) <= size_n && isOpen(row+1, col))
                union((row-1)*size_n+col, row*size_n+col);
            if (row == 1)
                union(0, (row-1)*size_n+col);
            if (row == size_n)
                union(size_n*size_n+1, (row-1)*size_n+col);
           
            num_Open++;
        }

    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row <= 0 || row > size_n) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > size_n) throw new IndexOutOfBoundsException("column index i out of bounds");
        return state[(row-1)*size_n+col] == 1;
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row <= 0 || row > size_n) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > size_n) throw new IndexOutOfBoundsException("column index i out of bounds");
        return connected((row-1)*size_n+col, 0);
    }
    public int numberOfOpenSites()       // number of open sites
    {
        return num_Open;
    }
    public boolean percolates()              // does the system percolate?
    {
        return connected(0, size_n*size_n+1);
    }
   
    public static void main(String[] args)   // test client (optional)
    {
        Percolation p = new Percolation(Integer.parseInt(args[0]));
        while (!p.percolates()) {          
            p.open(StdRandom.uniform(1, p.size_n+1), StdRandom.uniform(1, p.size_n+1));
        } 
        System.out.println(p.percolates());
        System.out.println((float) p.numberOfOpenSites()/(p.size_n*p.size_n));
       
    }
}