import java.util.Iterator;

// not sure what constant worst-case time wants
public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private int size;
    private class Node
    {
        private Item item;
        private Node next;
    }
    public Deque()                           // construct an empty deque
    {
        first = null;                        
        size = 0;
    }
    public boolean isEmpty()                 // is the deque empty?
    {
        return first == null;
    }
    public int size()                        // return the number of items on the deque
    {
        return size;
    }
    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        size++;
        Node newnode = new Node();
        newnode.item = item;
        newnode.next = first;
        first = newnode;
    }
    public void addLast(Item item)           // add the item to the end
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (size == 0) {
            Node newnode = new Node();
            newnode.item = item;
            newnode.next = first;
            first = newnode;
        }
        else {
            Node trace = first;
            while (trace.next != null) 
                trace = trace.next;
            Node newnode = new Node();
            newnode.item = item;
            trace.next = newnode;
        }
        size++;
    }
    public Item removeFirst()                // remove and return the item from the front 
    {
        if (size == 0)
            throw new java.util.NoSuchElementException();
        size--;
        Item item = first.item;
        first = first.next;
        return item;
    }
    public Item removeLast()                 // remove and return the item from the end
    {
        if (size == 0)
            throw new java.util.NoSuchElementException();
        else if (size == 1) {
            Item item = first.item;
            first = null;
            size--;
            return item;
        }
        else {
            Node trace = first;
            Node trace2 = first;
            trace = trace.next;
            while (trace.next != null) {
                trace = trace.next;
                trace2 = trace2.next;
            }
            Item item = trace.item;
            trace.item = null;
            trace.next = null;
            trace2.next = null;
            size--;
            return item;
        }
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() {    return current != null;    }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    public static void main(String[] args)   // unit testing (optional)
    {
        
    }
}