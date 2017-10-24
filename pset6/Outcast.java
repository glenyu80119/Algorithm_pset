import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wn;

    public Outcast(WordNet wordnet) {         // constructor takes a WordNet object
        /*String[] inputd = new String[2];
        inputd = wordnet.InputData();
        wn = new WordNet(inputd[0], inputd[1]);*/
        wn = wordnet;
    }
    public String outcast(String[] nouns) {   // given an array of WordNet nouns, return an outcast
        int l = nouns.length;
        int[] lens = new int[l];
        for (int i = 0;i <l; i++)
            lens[i] = 0;
        for(int i = 0; i< l; i++) {
            for (int j = 0; j < l; j++) {
                lens[i] = lens[i] + wn.distance(nouns[i], nouns[j]);
            }
        }
        int maxindex = 0;
        int temp = lens[0];
        for (int i = 0;i < l; i++) {
            if(lens[i] > temp) {
                maxindex = i;
                temp = lens[i];
            }
        }
        return nouns[maxindex];
    }
       

    public static void main(String[] args) {  // test client (see below)
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}