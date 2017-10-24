import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int numOfSeg;
    private LineSegment[] LineSegTemp;
    private LineSegment[] LineSeg;
    
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null)
            throw new java.lang.NullPointerException();
        for (int p = 0; p < points.length; p++) {
            if (points[p] == null)
                throw new java.lang.NullPointerException();
        }
        Arrays.sort(points);
        /*for (int p = points.length - 1; p >= 1; p--) {
            for (int q = 0; q < p; q++) {
                if (points[q].compareTo(points[q+1]) > 0) {
                    Point temp = points[q];
                    points[q] = points[q+1];
                    points[q+1] = temp;
                }
            }
        }*/
        for (int p = 0; p < points.length - 1; p++) {
            if (points[p] == points[p+1])
                throw new java.lang.IllegalArgumentException();
        }
        numOfSeg = 0;
        int len = points.length;
        int temp_len = len*(len-1)*(len-2)*(len-3)/24;
        LineSegTemp = new LineSegment[temp_len];
        Point[] p = new Point[4];
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int l = k+1; l < points.length; l++) {
                        p[0] = points[i];
                        p[1] = points[j];
                        p[2] = points[k];
                        p[3] = points[l];
                        
                        if (p[0].slopeTo(p[1]) == p[0].slopeTo(p[2]) && p[0].slopeTo(p[1]) == p[0].slopeTo(p[3])) {
                            LineSegTemp[numOfSeg++] = new LineSegment(p[0], p[3]);
                        }
                    }
                }
            }
        }
        LineSeg = new LineSegment[numOfSeg];
        for (int i = 0; i < numOfSeg; i++) {
            LineSeg[i] = LineSegTemp[i];
        }
    }
    public int numberOfSegments()        // the number of line segments
    {
        return numOfSeg;
    }
    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] LineSeg2 = new LineSegment[numOfSeg];
        for (int i = 0; i < numOfSeg; i++) {
            LineSeg2[i] = LineSeg[i];
        }
        return LineSeg2;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        }
}