import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;

public class WordNet {
    private int V;//num of vertex
    private int E;//num of edge  
    private SeparateChainingHashSTWordNet st;
    private Bag<String>[] dict;
    private Digraph diGraph;
    private SAP sap;
    private String[] inputdata;
   // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        inputdata = new String[2];
        inputdata[0] = synsets;
        inputdata[1] = hypernyms;
        if (synsets != null && (!synsets.equals("")) && hypernyms != null && (!hypernyms.equals(""))) {
            st = new SeparateChainingHashSTWordNet();
            In in = new In(synsets);
            V = 0;
            while(in.hasNextLine())
            {
                V++;
                String s = in.readLine();
                String[] sarray = s.split(",");
                String[] words = sarray[1].split(" ");
                int a = Integer.parseInt(sarray[0]);
                for (int i= 0; i < words.length; i++)
                    st.put(words[i], a);
            }
            dict = (Bag<String>[]) new Bag[V];
            for(int i=0; i < V; i++)
                dict[i] = new Bag<String>();
            for(String ss: st.keys()){
                for(int ii: st.get(ss))
                    dict[ii].add(ss);
            }
            diGraph = new Digraph(V);
            In edges = new In(hypernyms);
            E = 0;
            while(edges.hasNextLine()){
                String s = edges.readLine();
                String[] sarray = s.split(",");
                int hypo = Integer.parseInt(sarray[0]);
                if (sarray.length > 1) {
                    for (int i = 1; i < sarray.length; i++){
                        int hyper = Integer.parseInt(sarray[i]);
                        diGraph.addEdge(hypo,hyper);
                        E++;
                    }
                }
            }
            sap = new SAP(diGraph);
        }
        else
            throw new java.lang.IllegalArgumentException("no files");
    }
   
   // all WordNet nouns
    public Iterable<String> nouns() {
        Bag<String> ns = new Bag<String> ();
        for (String ss : st.keys())
            ns.add(ss);
        return ns;
    }

   // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word != null && (!word.equals("")))
            return (st.get(word) != null);
        throw new java.lang.IllegalArgumentException("no input");
    }

   // a synset (second field of synsets.txt) that is a shortest common ancestor
   // of noun1 and noun2 (defined below)
    public String sap(String noun1, String noun2) {
        Iterable<Integer> n1 = st.get(noun1);
        Iterable<Integer> n2 = st.get(noun2);
        if (n1 == null || n2 == null)
            throw new java.lang.IllegalArgumentException("There is no such noun.");
        int ans = sap.ancestor(n1, n2);
        String ansStr = "";
        int nn = 0;
        for (String s: dict[ans]) {
            if (nn == 0) {
                ansStr = ansStr + s;
                nn++;
            }
            else
                ansStr = ansStr +" " +s;
        }
        return ansStr;
    }

   // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        Iterable<Integer> n1 = st.get(noun1);
        Iterable<Integer> n2 = st.get(noun2);
        if (n1 == null || n2 == null)
            throw new java.lang.IllegalArgumentException("There is no such noun.");
        int ans = sap.length(n1, n2);
        return ans;
    }
    private String[] InputData() {
        return inputdata;
    }
   // unit testing (required)
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            int length   = wn.distance(v, w);
            String ancestor = wn.sap(v, w);
            StdOut.printf("length = %d", length);
            StdOut.println("ancestor : "+ ancestor);
        }
    }
    
}