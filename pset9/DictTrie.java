

public class DictTrie {
    private Node root;
    
    private class Node {
        private boolean exist;
        private Node[] next = new Node[26];
    }
    
    public DictTrie() {
        root = new Node();
    }
    
    public void put(String s) {
        root = put(root, s, 0);
    }
    
    private Node put(Node x, String s, int d) {
        if (x == null) x = new Node();
        if (d == s.length()) { x.exist = true; return x; }
        char c = s.charAt(d);
        int n = (int)c;
        x.next[n-65] = put(x.next[n-65], s, d+1);
        return x;
    }
    
    private Node get(Node x, String s, int d) {
        if (x == null) return null;
        if (d == s.length()) return x;
        char c = s.charAt(d);
        int n = (int)c;
        return get(x.next[n-65], s, d+1);
    }
    
    public boolean contains(String s) {
        if (s == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, s, 0);
        if (x == null) 
            return false;
        else
            return x.exist;
    }
    
    public boolean hasprefix(String s) {
        if (s == null) throw new IllegalArgumentException("argument to hasprefix() is null");
        Node x = get(root, s, 0);
        if (x == null) 
            return false;
        return true;
    }
    
}