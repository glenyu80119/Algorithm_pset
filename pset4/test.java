import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class test {
    private int[] a;

    public test () {
    }
    private double[][] matrixtranspose(double[][] m) {
        int newwid = m[0].length;
        int newheight = m.length;
        double [][] tm = new double[newwid][newheight];
        for (int i = 0; i < newwid; i++) {
            for(int j = 0; j < newheight; j++) {
                tm[i][j] = m[j][i];
            }
        }
        return tm;
    }
    public static void main(String[] args) {
        test tt = new test();
        double[][] m1 = new double[2][3];
        
        m1[0][0] = 0;
        m1[0][1] = 1;
        m1[0][2] = 2;
        m1[1][0] = 3;
        m1[1][1] = 4;
        m1[1][2] = 5;; 
        for(int i = 0;i < m1.length; i++)
            StdOut.printf("%2.2f %2.2f %2.2f\n",m1[i][0],m1[i][1], m1[i][2]);
        m1 = tt.matrixtranspose(m1);
        for(int i = 0;i < m1.length; i++)
            StdOut.printf("%2.2f %2.2f \n",m1[i][0],m1[i][1]);
    }
}