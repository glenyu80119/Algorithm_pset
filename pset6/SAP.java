import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.SET;

//import java.lang.IllegalArgumentException;

public class SAP {
    private static final int INFINITY = Integer.MAX_VALUE;
    private Digraph diGraph;
    private short[] marked;  // marked[v] = true if v is reachable from s
    private SET<Integer> dfsing;
    // private SET<Integer> groups;
    
    // constructor takes a rooted DAG as argument
    public SAP(Digraph G) {
        if (!(G instanceof Digraph) || G == null)
            throw new java.lang.IllegalArgumentException();
        diGraph  = new Digraph(G);
        marked = new short[G.V()];
        // groups = new SET<Integer>();
        // int groupNumber = 0;
        short group = 1;
        for(int i = 0;i < G.V(); i++)
            marked[i] = 0;
        /* for(int a = 0; a < G.V(); a++) {
            if (marked[a] == 0) {
                dfsing = new SET<Integer> ();
                if (!dfs(diGraph, a, dfsing, group))
                    throw new java.lang.IllegalArgumentException("Not a DAG!");
                group++;
            }
        }*/
        /*for(int a = 0; a < G.V(); a++) {
            groups.add((int)marked[a]);
        }
        for(int i:groups) groupNumber++;
        if (groupNumber > 1) {
            throw new java.lang.IllegalArgumentException("No root!");
        }
         StdOut.println(groupNumber);*/
        
    }

    // length of shortest ancestral path between v and w
    // How to check if int v is null????????????????????????????????????????????????????????????????????????????????????????????????????
    public int length(int v, int w) {
        /*Integer a = null;
        try { 
            a = Integer.valueOf(v);
        }
        catch (NumberFormatException e) {
            throw new java.lang.IllegalArgumentException("null input");
        }
        Integer b = null;
        try { 
            b = Integer.valueOf(w);
        }
        catch (NumberFormatException e) {
            throw new java.lang.IllegalArgumentException("null input");
        }*/
        /*if (a == null || b == null)
            throw new java.lang.IllegalArgumentException("null input");*/
        if (v > diGraph.V() || w > diGraph.V() || v < 0 || w < 0)
            throw new java.lang.IllegalArgumentException("wrong input");
        DeluxeBFS BfV = new DeluxeBFS(diGraph,v);
        DeluxeBFS BfW = new DeluxeBFS(diGraph,w);
        Iterable<Integer> SearchedV = BfV.SearchedVertex();
        int shortDistance = INFINITY;
        for (int i : SearchedV) {
            if (BfV.hasPathTo(i) && BfW.hasPathTo(i)) {
                if ((BfV.distTo(i) + BfW.distTo(i)) < shortDistance) {
                    shortDistance = BfV.distTo(i) + BfW.distTo(i);
                }
            }
        }
        if (shortDistance != INFINITY)
            return shortDistance;
        else
            return -1;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        /*Integer a = null;
        try { 
            a = Integer.valueOf(v);
        }
        catch (NumberFormatException e) {
            throw new java.lang.IllegalArgumentException("null input");
        }
        Integer b = null;
        try { 
            b = Integer.valueOf(w);
        }
        catch (NumberFormatException e) {
            throw new java.lang.IllegalArgumentException("null input");
        }*/
        /*Integer a = new Integer(v);
        Integer b = new Integer(w);
        if (a == null || b == null)
            throw new java.lang.IllegalArgumentException("null input");*/
        if (v > diGraph.V() || w > diGraph.V() || v < 0 || w < 0)
            throw new java.lang.IllegalArgumentException("null input");
        DeluxeBFS BfV = new DeluxeBFS(diGraph,v);
        DeluxeBFS BfW = new DeluxeBFS(diGraph,w);
        Iterable<Integer> SearchedV = BfV.SearchedVertex();
        int shortDistance = INFINITY;
        int shortAncestor = INFINITY;
        for (int i: SearchedV) {
            if (BfV.hasPathTo(i) && BfW.hasPathTo(i)) {
                if ((BfV.distTo(i) + BfW.distTo(i)) < shortDistance) {
                    shortDistance = BfV.distTo(i) + BfW.distTo(i);
                    shortAncestor = i;
                }
            }
        }
        
       if (shortAncestor != INFINITY)
            return shortAncestor;
        else
            return -1;
    }

    // length of shortest ancestral path of vertex subsets A and B
    //how to check if there is a null vertex in iterable???????????????????????????????????????????????????????????????
    public int length(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null || size(subsetA) == 0 || size(subsetB) == 0)
            throw new java.lang.IllegalArgumentException("null input");
        for (int a: subsetA) {
            if (a > diGraph.V() || a < 0)
                throw new java.lang.IllegalArgumentException("invalid input");
        }
        for (int a: subsetB) {
            if (a > diGraph.V() || a < 0)
                throw new java.lang.IllegalArgumentException("invalid input");
        }
        DeluxeBFS BfV = new DeluxeBFS(diGraph,subsetA);
        DeluxeBFS BfW = new DeluxeBFS(diGraph,subsetB);
        Iterable<Integer> SearchedV = BfV.SearchedVertex();
        int shortDistance = INFINITY;
        for (int i: SearchedV) {
            if (BfV.hasPathTo(i) && BfW.hasPathTo(i)) {
                if ((BfV.distTo(i) + BfW.distTo(i)) < shortDistance) {
                    shortDistance = BfV.distTo(i) + BfW.distTo(i);
                }
            }
        }
        
       if (shortDistance != INFINITY)
            return shortDistance;
        else
            return -1;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestor(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        if (subsetA == null || subsetB == null || size(subsetA) == 0 || size(subsetB) == 0)
            throw new java.lang.IllegalArgumentException("null input");
        for (int a: subsetA) {
            Integer aInt = new Integer(a);
            if (a > diGraph.V() || a < 0 || aInt == null)
                throw new java.lang.IllegalArgumentException("invalid input");
        }
        for (int a: subsetB) {
            if (a > diGraph.V() || a < 0)
                throw new java.lang.IllegalArgumentException("invalid input");
        }
        DeluxeBFS BfV = new DeluxeBFS(diGraph,subsetA);
        DeluxeBFS BfW = new DeluxeBFS(diGraph,subsetB);
        Iterable<Integer> SearchedV = BfV.SearchedVertex();
        int shortDistance = INFINITY;
        int shortAncestor = INFINITY;
        for (int i: SearchedV) {
            if (BfV.hasPathTo(i) && BfW.hasPathTo(i)) {
                if ((BfV.distTo(i) + BfW.distTo(i)) < shortDistance) {
                    shortDistance = BfV.distTo(i) + BfW.distTo(i);
                    shortAncestor = i;
                }
            }
        }
        
       if (shortAncestor != INFINITY)
            return shortAncestor;
        else
            return -1;
    }
    
    private int size(Iterable<Integer> it) {
        int i = 0;
        for (Object obj : it) i++;
        return i;
    }
    
        
    private boolean dfs(Digraph G, int v, SET<Integer> dfs, short group) {
        marked[v] = group;
        dfsing.add(v);
        for (int w : G.adj(v)) {
            if (dfsing.contains(w))
                return false;
            if (marked[w] == 0) {
                boolean ans = dfs(G, w , dfsing, group);
                dfsing.delete(w);
                if (!ans)
                    return false;
            }
            else{
                marked[v] = marked[w];
                group = marked[w];
                for(int k:dfsing)
                    marked[k] = marked[w];
                dfsing.delete(w);
            }
        }
        return true;
    }
        
    

    
    // tests this class by directly calling all instance methods
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sca = new SAP(G);
        /*SET<Integer> a = new SET<Integer>();
        a.add(3);
        SET<Integer> b = new SET<Integer>();
        b.add(6);
        int length1   = sca.length(a, b);
        int ancestor1 = sca.ancestor(a, b);
        StdOut.printf("length = %d, ancestor = %d\n", length1, ancestor1);*/
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}