import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;



public class SeparateChainingHashSTWordNet {
    private static final int INIT_CAPACITY = 4;

    private int n;                                // number of key-value pairs
    private int m;                                // hash table size
    private SequentialSearchSTWordNet[] st;  // array of linked-list symbol tables


    /**
     * Initializes an empty symbol table.
     */
    public SeparateChainingHashSTWordNet() {
        this(INIT_CAPACITY);
    } 

    /**
     * Initializes an empty symbol table with {@code m} chains.
     * @param m the initial number of chains
     */
    public SeparateChainingHashSTWordNet(int m) {
        this.m = m;
        st = (SequentialSearchSTWordNet[]) new SequentialSearchSTWordNet[m];
        for (int i = 0; i < m; i++)
            st[i] = new SequentialSearchSTWordNet();
    } 

    // resize the hash table to have the given number of chains,
    // rehashing all of the keys
    private void resize(int chains) {
        SeparateChainingHashSTWordNet temp = new SeparateChainingHashSTWordNet (chains);
        for (int i = 0; i < m; i++) {
            for (String key : st[i].keys()) {
                for(int v: st[i].get(key))
                    temp.put(key, v);
            }
        }
        this.m  = temp.m;
        this.n  = temp.n;
        this.st = temp.st;
    }

    // hash value between 0 and m-1
    private int hash(String key) {
        return (key.hashCode() & 0x7fffffff) % m;
    } 

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    } 

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    } 

    /**
     * Returns the value associated with the specified key in this symbol table.
     *
     * @param  key the key
     * @return the value associated with {@code key} in the symbol table;
     *         {@code null} if no such value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public Iterable<Integer> get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        int i = hash(key);
        return st[i].get(key);
    } 

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value if the symbol table already contains the specified key.
     * Deletes the specified key (and its associated value) from this symbol table
     * if the specified value is {@code null}.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(String key, int val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");


        // double table size if average length of list >= 10
        if (n >= 10*m) resize(2*m);

        int i = hash(key);
        if (!st[i].contains(key)) n++;
        st[i].put(key, val);
    } 

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    /*public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

        int i = hash(key);
        if (st[i].contains(key)) n--;
        st[i].delete(key);

        // halve table size if average length of list <= 2
        if (m > INIT_CAPACITY && n <= 2*m) resize(m/2);
    } 
*/
    // return keys in symbol table as an Iterable
    public Iterable<String> keys() {
        Queue<String> queue = new Queue<String>();
        for (int i = 0; i < m; i++) {
            for (String key : st[i].keys())
                queue.enqueue(key);
        }
        return queue;
    } 


    /**
     * Unit tests the {@code SeparateChainingHashST} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) { 
        SeparateChainingHashSTWordNet st = new SeparateChainingHashSTWordNet();
        In in = new In(args[0]);
        while(in.hasNextLine())
        {
            String s = in.readLine();
            String[] sarray = s.split(",");
            String[] words = sarray[1].split(" ");
            int a = Integer.parseInt(sarray[0]);
            for (int i= 0; i < words.length; i++)
                st.put(words[i], a);
        }

        for (String ss:st.keys()) {
            StdOut.println(ss);
            for (int q:st.get(ss))
                StdOut.println(q);
        }
        StdOut.println(st.size());


    }
    

}

