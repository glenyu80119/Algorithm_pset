import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class SeamCarver {
    private Picture pic;
    private double[][] energymat;
    private double[][] energycalmat;
    private int[][] pathmat;
    
    public SeamCarver(Picture picture) {               // create a seam carver object based on the given picture
        if (picture == null)
            throw new java.lang.IllegalArgumentException();
        pic = new Picture(picture);
        energymat = new double[pic.width()][pic.height()];
        int h = pic.height();
        int w = pic.width();
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                energymat[i][j] = energy(i, j);
            }
        }
    }
    public Picture picture() {                         // current picture
        Picture picRe = new Picture(pic);
        return picRe;
    }
    public     int width() {                           // width of current picture
        return pic.width();
    }
    public     int height() {                          // height of current picture
        return pic.height();
    }
    
    public  double energy(int x, int y) {              // energy of pixel at column x and row y
        if (x < 0 || y < 0 || x >= pic.width() || y >= pic.height())
            throw new java.lang.IllegalArgumentException();
        Color rowB;
        Color rowL;
        Color colB;
        Color colL;
        if (x == 0) {
            rowB = pic.get(1, y);
            rowL = pic.get(pic.width()-1, y);
        }
        else if (x == (pic.width()-1)) {
            rowB = pic.get(0, y);
            rowL = pic.get(x-1, y);
        }
        else {
            rowB = pic.get(x+1, y);
            rowL = pic.get(x-1, y);
        }
        
        if (y == 0) {
            colB = pic.get(x, 1);
            colL = pic.get(x, pic.height()-1);
        }
        else if (y == (pic.height()-1)) {
            colB = pic.get(x, 0);
            colL = pic.get(x, y-1);
        }
        else {
            colB = pic.get(x, y+1);
            colL = pic.get(x, y-1);
        }
          return Math.sqrt(energyCal(rowB, rowL) + energyCal(colB, colL));
          
    }
    private double energyCal(Color A, Color B) {
        int difr = (A.getRed()-B.getRed())*(A.getRed()-B.getRed());
        int difg = (A.getGreen()-B.getGreen())*(A.getGreen()-B.getGreen());
        int difb = (A.getBlue()-B.getBlue())*(A.getBlue()-B.getBlue());
        return difr + difb + difg;
    }
    
    private void relax(double[][] energycalm, double[][] energym,int[][] pathm, int x, int y) {
            if (x == 0) {
                if ((energycalm[x][y] + energym[x][y+1]) < energycalm[x][y+1]) {
                energycalm[x][y+1] = energycalm[x][y] + energym[x][y+1];
                pathm[x][y+1] = x;
            }
        
            if ((energycalm[x][y] + energym[x+1][y+1]) < energycalm[x+1][y+1]) {
                energycalm[x+1][y+1] = energycalm[x][y] + energym[x+1][y+1];
                pathm[x+1][y+1] = x;
            }
            
        }
        else if (x == pic.width()-1) {
            if ((energycalm[x][y] + energym[x][y+1]) < energycalm[x][y+1]) {
                energycalm[x][y+1] = energycalm[x][y] + energym[x][y+1];
                pathm[x][y+1] = x;
            }
        
            if ((energycalm[x][y] + energym[x-1][y+1]) < energycalm[x-1][y+1]) {
                energycalm[x-1][y+1] = energycalm[x][y] + energym[x-1][y+1];
                pathm[x-1][y+1] = x;
            }
        
        }
        else{
            if ((energycalm[x][y] + energym[x][y+1]) < energycalm[x][y+1]) {
                energycalm[x][y+1] = energycalm[x][y] + energym[x][y+1];
                pathm[x][y+1] = x;
            }
        
            if ((energycalm[x][y] + energym[x-1][y+1]) < energycalm[x-1][y+1]) {
                energycalm[x-1][y+1] = energycalm[x][y] + energym[x-1][y+1];
                pathm[x-1][y+1] = x;
            }
            
            if ((energycalm[x][y] + energym[x+1][y+1]) < energycalm[x+1][y+1]) {
                energycalm[x+1][y+1] = energycalm[x][y] + energym[x+1][y+1];
                pathm[x+1][y+1] = x;
            }
        }
        
    }
    
    private double[][] matrixtranspose(double[][] m) {
        int newwid = m[0].length;
        int newheight = m.length;
        double [][] tm = new double[newwid][newheight];
        for (int i = 0; i < newwid; i++) {
            for(int j = 0; j < newheight; j++) {
                tm[i][j] = m[j][i];
            }
        }
        return tm;
    }
    
    public   int[] findHorizontalSeam() {               // sequence of indices for horizontal seam*/
        Picture transPic = new Picture(pic.height(), pic.width());
        int w = pic.height();
        int h = pic.width();
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                transPic.set(i, j, pic.get(j, i));
            }
        }
        
        pic = new Picture(transPic);
        
        energymat = matrixtranspose(energymat);
        
        int[] hor = findVerticalSeam();
        
        transPic = new Picture(pic.height(), pic.width());
        w = pic.height();
        h = pic.width();
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                transPic.set(i, j, pic.get(j, i));
            }
        }
        pic = new Picture(transPic);
        energymat = matrixtranspose(energymat);
        return hor;
        
    }
    public   int[] findVerticalSeam() {                 // sequence of indices for vertical seam
        energycalmat = new double[pic.width()][pic.height()];
        int hh = pic.height();
        int ww = pic.width();
        for(int j = 0; j < hh; j++) {
            for(int i = 0; i < ww; i++) {
                energycalmat[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        pathmat = new int[pic.width()][pic.height()];

        for(int j = 0; j < hh-1; j++) {
            for(int i = 0; i < ww; i++) {
                if (j == 0) {
                    energycalmat[i][j] = energymat[i][j];
                    pathmat[i][j] = 0;
                }        
                relax(energycalmat, energymat, pathmat, i, j); 

            }
        }

        double[] lastcol = new double[pic.width()];
        int w = pic.width();
        int h = pic.height();
        for (int i = 0; i < w; i++)
            lastcol[i] = energycalmat[i][h-1];
        Arrays.sort(lastcol);
        int flag = 0;
        int trytry = 0;
        int endpoint = Integer.MAX_VALUE;
        int[] pathans = new int[h];
        
        while(flag == 0) {
            for(int i = 0; i < w; i++) {
                if (energycalmat[i][h-1] == lastcol[trytry]) {
                     endpoint = i;
                }
            }
            pathans[pic.height()-1] = endpoint;
            for(int k = pic.height()-2; k >= 0; k--) {
                pathans[k] = pathmat[endpoint][k+1];
                endpoint = pathans[k];
            }
            flag = 1;

            for (int k = 0; k < h-1; k++) {
                if (Math.abs(pathans[k] - pathans[k+1]) > 1) {
                    trytry++;
                    flag = 0;
                }
            }
        }
        return pathans;
    }
   
    public    void removeHorizontalSeam(int[] seam) {   // remove horizontal seam from current picture
        Picture transPic = new Picture(pic.height(), pic.width());
        int w = pic.height();
        int h = pic.width();
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                transPic.set(i, j, pic.get(j, i));
            }
        }
        
        pic = new Picture(transPic);
        removeVerticalSeam(seam);
        transPic = new Picture(pic.height(), pic.width());
        w = pic.height();
        h = pic.width();
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                transPic.set(i, j, pic.get(j, i));
            }
        }
        pic = new Picture(transPic);
        
        energymat = matrixtranspose(energymat);
        
    }
    public    void removeVerticalSeam(int[] seam) {     // remove vertical seam from current picture
        if (seam == null)
            throw new java.lang.IllegalArgumentException();
        if (pic.width() == 1)
            throw new java.lang.IllegalArgumentException();
        int oldw = pic.width();
        int oldh = pic.height();
        if (seam.length != oldh)
            throw new java.lang.IllegalArgumentException();
        for (int i = 0; i < oldh; i++) {
            if (seam[i] >= oldw || seam[i] < 0)
                throw new java.lang.IllegalArgumentException();
        }
        
        for (int i = 0; i < oldh-1; i++) {
            if (Math.abs(seam[i] - seam[i+1]) > 1)
                throw new java.lang.IllegalArgumentException();
        }
        Picture newPic = new Picture(pic.width()-1, pic.height());
        int h = newPic.height();
        int w = newPic.width();
        for(int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                if (i < seam[j])
                   newPic.set(i, j, pic.get(i,j));
                else
                   newPic.set(i, j, pic.get(i+1,j));
            }
        }
        pic = new Picture(newPic);
        energymat = new double[pic.width()][pic.height()];
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w; i++) {
                energymat[i][j] = energy(i, j);
            }
        }
        
    }

    public static void main(String[] args) {           //  unit testing (required)
    }
}