import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import java.util.ArrayDeque;
import java.util.Arrays;

public class BurrowsWheeler {
    // apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray cs = new CircularSuffixArray(s);
        StringBuilder sb = new StringBuilder(cs.length());
        int l = cs.length();
        String ss = s + s;
        for (int i = 0; i < l;i++) {
            if (cs.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }
        }
        for (int i = 0; i < l;i++) {
            sb.insert(i, ss.charAt(cs.index(i)+l-1));
        }
        
        String f = sb.toString();
        BinaryStdOut.write(f);
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        ArrayDeque<Integer>[] charCount = new ArrayDeque[256];
        for (int i = 0; i < 256; i++) {
            charCount[i] = new ArrayDeque<Integer>();
        }
        int l = s.length();
        for (int i = 0; i < l;i++) {
            charCount[(int) s.charAt(i)].add(i);
        }
        int[] next = new int[l];
        int j = 0;
        for (int i = 0; i < 256; i++) {
            while (charCount[i].size() != 0)
                next[j++] = charCount[i].poll();
        }
        
        char[] ch = s.toCharArray();
        Arrays.sort(ch);
        StringBuilder sb = new StringBuilder(l);
        sb.insert(0, ch[first]);
        for (int i = 1; i < l;i++) {
            sb.insert(i, ch[next[first]]);
            first = next[first];
        }
        String ss = sb.toString();
        BinaryStdOut.write(ss);
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
    public static void main(String[] args){
        if (args[0].indexOf("-") == 0)
            transform();
        else if (args[0].indexOf("+") == 0)
            inverseTransform();
        else
            StdOut.println("Wrong!");
    }
}