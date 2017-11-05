import java.util.Arrays;
import edu.princeton.cs.algs4.StdOut;
public class CircularSuffixArray {
    private String model;
    private Suffix[] suffixArray;
    private int length;
    private class Suffix implements Comparable<Suffix> {
        private String text;
        private int offset;
        private int index;
        public Suffix(String ss, int n, int index) {
            this.text = ss;
            this.offset = n;
            this.index = index;
        }
        public int compareTo(Suffix that) {
            int length = text.length()/2;
            for (int i = 0; i < length; i++) {
                if ((int) text.charAt(reindex()+i) > (int) text.charAt(that.reindex()+i))
                    return 1;
                else if ((int) text.charAt(reindex()+i) < (int) text.charAt(that.reindex()+i))
                    return -1;
            }
            return 0;
        }
        public int reindex() {
            return index;
        }
    }
    public CircularSuffixArray(String s) {    // circular suffix array of s
        if (s == null)
            throw new java.lang.IllegalArgumentException();
        model = s + s;
        suffixArray = new Suffix[s.length()]; 
        length = s.length();
        for (int i = 0; i < length; i++) {
            suffixArray[i] = new Suffix(model, i, i);
        }
        Arrays.sort(suffixArray);
    }
    
    /*public Suffix[] resa() {
            Suffix[] sa = new Suffix[length];
            for(int i = 0; i < length; i++) {
                sa[i] = suffixArray[i];
            }
            return sa;
        }*/
    
    public int length() {                    // length of s
        return length;
    }
    public int index(int i) {                 // returns index of ith sorted suffix
        if (i < 0 || i >= length)
            throw new java.lang.IllegalArgumentException();
        return suffixArray[i].reindex();
    }
    public static void main(String[] args) {  // unit testing (required)
        String s = "ABRACADABRA!";
        CircularSuffixArray cs = new CircularSuffixArray(s);
        int length = s.length();
        StdOut.println(cs.length());
        for (int i = 0; i < length; i++) {
            StdOut.println(cs.index(i));
        }
    }
}