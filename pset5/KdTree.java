import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Stack;

public class KdTree {
    private int size;
    private Node root;
    
    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private boolean dir;
        
        public Node(Point2D p, boolean dir, RectHV rec) {
            this.p = new Point2D(p.x(), p.y());
            this.dir = dir;
            lb = null;
            rt = null;
            rect = null;
            this.rect = new RectHV(rec.xmin(), rec.ymin(), rec.xmax(), rec.ymax());
        }
        
        private boolean compare(Point2D that) {
            if (dir) {
                if (this.p.x() > that.x())
                    return false;
                else
                    return true;
            }
            else {
                if (this.p.y() > that.y())
                    return false;
                else
                    return true;
            }
        }
    }
    
    public KdTree()                               // construct an empty set of points 
    {
        root = null;
        size = 0;
    }
    public boolean isEmpty()                      // is the set empty? 
    {
        return root == null;
    }
    public int size()                         // number of points in the set 
    {
        int s = size;
        return s;
    }
    private Node putnode(Node x, Point2D that, boolean dir, RectHV r) {
        if (x == null) {
            size++;
            return new Node(that, dir, r);
        }
        
        if (x.compare(that)) {
            if (!x.p.equals(that)) {
                if (dir) {
                    x.rt = putnode(x.rt, that, !x.dir, new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax()));       
                }
                else {
                    x.rt = putnode(x.rt, that, !x.dir, new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax()));
                }
            }

        }
        else {
            if (dir) {
                x.lb = putnode(x.lb, that, !x.dir, new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax()));         
            }
            else {
                x.lb = putnode(x.lb, that, !x.dir, new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y()));
            }
        }
        return x;
    }
    private boolean getnode(Node x, Point2D that) 
    {
        if (x == null)
            return false;
        
        if (x.compare(that)) {
            if (x.p.equals(that))
                return true;
            return getnode(x.rt, that);
        }
        else
            return getnode(x.lb, that);            
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null)
            throw new java.lang.NullPointerException();

        root = putnode(root, p, true, new RectHV(0.0, 0.0, 1.0, 1.0));
    }
    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null)
            throw new java.lang.NullPointerException();
        return getnode(root, p);
    }
    public void draw()                         // draw all points to standard draw 
    {
        ArrayDeque<Node> ad = new ArrayDeque<Node>();
        ad.addLast(root);


        while (!ad.isEmpty()) {
            Node n = ad.remove();
            if (n.lb != null)
                ad.addLast(n.lb);
            if (n.rt != null)
                ad.addLast(n.rt);


            if (n.dir) {
                
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                n.p.draw();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                n.p.draw();
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
            }
        }
    }
    private double[] resize(int n, double[] pre) {
        double[] temp = new double[n*2];
        for (int i = 0; i < n; i++) {
            temp[i] = pre[i];
        }
        for (int i = n; i < 2*n; i++) {
            temp[i] = 0;
        }
        return temp;
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        ArrayDeque<Node> ad = new ArrayDeque<Node>();
        ArrayList<Point2D> adPoint = new ArrayList<Point2D>();
        if (root == null)
            return adPoint;
        ad.addLast(root);
        while (!ad.isEmpty()) {
            Node n = ad.remove();
            if (n.dir) {
                if (rect.intersects(new RectHV(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax())))
                {
                    if (rect.contains(n.p))
                        adPoint.add(n.p);
                
                    if (n.lb != null)
                        ad.addLast(n.lb);
                    if (n.rt != null)
                        ad.addLast(n.rt);
                }
                else {
                    if (rect.xmax() < n.p.x()) {
                         if (n.lb != null)
                             ad.addLast(n.lb);
                    }
                    else {
                        if (n.rt != null)
                            ad.addLast(n.rt);
                    }
                }
            }
            else {
                if (rect.intersects(new RectHV(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y())))
                {
                    if (rect.contains(n.p))
                        adPoint.add(n.p);
                
                    if (n.lb != null)
                        ad.addLast(n.lb);
                    if (n.rt != null)
                        ad.addLast(n.rt);
                }
                else {
                    if (rect.ymax() < n.p.y()) {
                         if (n.lb != null)
                             ad.addLast(n.lb);
                    }
                    else {
                        if (n.rt != null)
                            ad.addLast(n.rt);
                    }
                }
            }
            /*if (rect.intersects(n.rect))
            {
                if (rect.contains(n.p))
                    adPoint.add(n.p);
                
                if (n.lb != null)
                    ad.addLast(n.lb);
                if (n.rt != null)
                    ad.addLast(n.rt);
            }*/
        }
        return adPoint;
    }
    
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if (root == null)
            return null;
        Stack<Node> ad = new Stack<Node>();
        Point2D nPoint = p;    //strange??????????????????
        double disSquare = 10;
        ad.push(root);
        while (!ad.isEmpty()) {
            Node n = ad.pop();
            double disTemp = p.distanceSquaredTo(n.p);
            if (disTemp < disSquare) {
                nPoint = n.p;
                disSquare = disTemp;
                
            }
            
            if (n.rect.distanceSquaredTo(p) < disSquare) {
                if (n.lb != null)
                    ad.push(n.lb);
                if (n.rt != null)
                    ad.push(n.rt);
            }
        }
        
        return nPoint;
    }

    /*public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        String filename = args[0];
        In in = new In(filename);

        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
    }*/
}