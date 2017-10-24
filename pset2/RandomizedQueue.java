import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;
    
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        s = (Item[]) new Object[10];
        for (int k = 0; k < 10; k++) {
            s[k] = null;
        }
        N = 0;
    }
    public boolean isEmpty()                 // is the queue empty?
    {
        return N == 0;
    }
    public int size()                        // return the number of items on the queue
    {
        return N;
    }
    public void enqueue(Item item)           // add the item
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (N == s.length) 
            resize(2 * s.length);
        s[N++] = item;
        
    }
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }
    public Item dequeue()                    // remove and return a random item
    {
        if (N == 0)
            throw new java.util.NoSuchElementException();
        int p = StdRandom.uniform(N);
        Item it = s[p];
        for (int k = p; p < N-1; p++) {
            s[k] = s[k+1];
        }
        s[N-1] = null;
        if (N > 0 && N == s.length/4) resize(s.length/2);
        N--;
        return it;
    }
    public Item sample()                     // return (but do not remove) a random item
    {
        if (N == 0)
            throw new java.util.NoSuchElementException();
        return s[StdRandom.uniform(N)];
    }
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new ArrayIterator();
    }
    private class ArrayIterator implements Iterator<Item>
    {
        private int i = 0;
        private int[] order = new int [N];
        
        public boolean hasNext() { return i < N; }
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
        public Item next() { 
            if (i == 0 || i == N) 
                order = StdRandom.permutation(N);
            if (i == N) 
                throw new java.util.NoSuchElementException();
            else {
                return s[order[i++]];
            }
        }
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        
    }
}