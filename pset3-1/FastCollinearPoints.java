import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {
    private int numOfSeg;
    private LineSegment[] LineSegTemp;
    private LineSegment[] LineSeg;

    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
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
        for (int p = 0; p < points.length-1; p++) {
            if (points[p] == points[p+1])
                throw new java.lang.IllegalArgumentException();
        }
        
        numOfSeg = 0;
        int len = points.length;
        int temp_len = 10000;
        LineSegTemp = new LineSegment[temp_len];
        for (int i = 0; i < points.length; i++) {
            Point[] pointsTemp = new Point[points.length];
            for (int j = 0; j < points.length; j++)
                pointsTemp[j] = points[j];
            Arrays.sort(pointsTemp, points[i].slopeOrder());            
            for (int j = 0; j < points.length-2; j++) {
                int k = 1;
                while (points[i].slopeTo(pointsTemp[j]) == points[i].slopeTo(pointsTemp[j+k])) {
                    k++;
                    if ((j+k) == points.length)
                        break;
                }
                if (k > 2) {
                    Point[] pTemp = new Point[k+1];
                    pTemp[0] = points[i];
                    for (int l = 1; l < k+1; l++) {
                        pTemp[l] = pointsTemp[j+l-1];
                    }
                    Arrays.sort(pTemp);
                    if (pTemp[0].compareTo(points[i]) == 0)
                        LineSegTemp[numOfSeg++] = new LineSegment(pTemp[0], pTemp[k]);
                    j = j+k-1;   
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        }
}