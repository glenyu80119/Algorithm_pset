import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;


public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }
        Iterator<String> i = rq.iterator();
        int po = Integer.parseInt(args[0]);
        for (int k = 0; k < po; k++) {
            if (i.hasNext())
                StdOut.println(i.next());
        }
    }
}