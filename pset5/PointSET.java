import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;


public class PointSET {
    private SET<Point2D> setPoint2D;
    private SET<Point2D> pointRange;
    public PointSET()                               // construct an empty set of points 
    {
         setPoint2D = new SET<Point2D>();
    }
    public boolean isEmpty()                      // is the set empty? 
    {
        return setPoint2D.isEmpty();
    }
    public int size()                         // number of points in the set 
    {
        return setPoint2D.size();
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null)
            throw new java.lang.NullPointerException();
        setPoint2D.add(p);
    }
    public boolean contains(Point2D p)            // does the set contain point p? 
    {
        if (p == null)
            throw new java.lang.NullPointerException();
        return setPoint2D.contains(p);
    }
    public void draw()                         // draw all points to standard draw 
    {
        for (Point2D p: setPoint2D) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        pointRange = new SET<Point2D>();
        for (Point2D p: setPoint2D) {
            if (p.x() <= rect.xmax() && p.x() >= rect.xmin() && p.y() <= rect.ymax() && p.y() >= rect.ymin()) {
                pointRange.add(p);
            }
        }
        return pointRange;
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        if (p == null)
            throw new java.lang.NullPointerException();
        Point2D np = p;
        double distance = 10;
        for (Point2D q: setPoint2D) {
            if (q.distanceSquaredTo(p) < distance) {
                distance = q.distanceSquaredTo(p);
                np = q;
            }
        }
        return np;
    }
 
    /*public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
        PointSET kd = new PointSET();
        kd.insert(new Point2D(0.5, 0.0));
        kd.insert(new Point2D(0.5, 1.0));

        Point2D p = new Point2D(0.5, 0.7);


        System.out.println(kd.nearest(p));



        kd.draw();
    }*/
}