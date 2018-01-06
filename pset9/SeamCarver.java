import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class SeamCarver {
    private Picture pic;
    private double[][] energymat;
    private double[][] energycalmat;
    private int[][] pathmat;
    private int[][] picRGB;
    private int width;
    private int height;
    public SeamCarver(Picture picture) {               // create a seam carver object based on the given picture
        if (picture == null)
            throw new java.lang.IllegalArgumentException();
        pic = new Picture(picture);
        energymat = new double[pic.width()][pic.height()];
        picRGB = new int[pic.width()][pic.height()];
        height = pic.height();
        width = pic.width();
        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                picRGB[i][j] = pic.getRGB(i, j);
            }
        }
        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                energymat[i][j] = energy(i, j);
            }
        }

    }
    
    public Picture picture() {                         // current picture
        Picture picRe = new Picture(width, height);
        for(int j = 0; j < height; j++) {
            for(int i = 0; i < width; i++) {
                picRe.setRGB(i,j, picRGB[i][j]);
            }
        }
        
        return picRe;
    }
    public     int width() {                           // width of current picture
        int w = width;
        return w;
    }
    public     int height() {                          // height of current picture
        int h = height;
        return h;
    }
    
    public  double energy(int x, int y) {              // energy of pixel at column x and row y
        if (x < 0 || y < 0 || x >= width || y >= height)
            throw new java.lang.IllegalArgumentException();
        int rowB;
        int rowL;
        int colB;
        int colL;
        if (x == 0 || x == (width - 1)) {
            return 1000.0;
        }
        else {
            rowB = picRGB[x+1][y];
            rowL = picRGB[x-1][y];
        }
        
        if (y == 0 || y == (height - 1)) {
            return 1000.0;
        }
        else {
            colB = picRGB[x][y+1];
            colL = picRGB[x][y-1];
        }
          return Math.sqrt(energyCal(rowB, rowL) + energyCal(colB, colL));        
    }
    
    private double energyCal(int A, int B) {
        int difr = (((A>>16)&255)-((B>>16)&255))*(((A>>16)&255)-((B>>16)&255));
        int difg = (((A>>8)&255)-((B>>8)&255))*(((A>>8)&255)-((B>>8)&255));
        int difb = ((A&255)-(B&255))*((A&255)-(B&255));
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
        else if (x == width-1) {
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
    
    private int[][] matrixtranspose(int[][] m) {
        int newwid = m[0].length;
        int newheight = m.length;
        int [][] tm = new int[newwid][newheight];
        for (int i = 0; i < newwid; i++) {
            for(int j = 0; j < newheight; j++) {
                tm[i][j] = m[j][i];
            }
        }
        return tm;
    }
    
    public   int[] findHorizontalSeam() {               // sequence of indices for horizontal seam*/
        int temp = height;
        height =  width;
        width = temp;
        
        picRGB = matrixtranspose(picRGB);
        energymat = matrixtranspose(energymat);
        
        int[] hor = findVerticalSeam();
        
        temp = height;
        height =  width;
        width = temp;
        picRGB = matrixtranspose(picRGB);
        energymat = matrixtranspose(energymat);
        return hor;
        
    }
    public   int[] findVerticalSeam() {                 // sequence of indices for vertical seam
        energycalmat = new double[width][height];
        int hh = height;
        int ww = width;
        for(int j = 0; j < hh; j++) {
            for(int i = 0; i < ww; i++) {
                energycalmat[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        pathmat = new int[width][height];

        for(int j = 0; j < hh-1; j++) {
            for(int i = 0; i < ww; i++) {
                if (j == 0) {
                    energycalmat[i][j] = energymat[i][j];
                    pathmat[i][j] = 0;
                }        
                relax(energycalmat, energymat, pathmat, i, j); 
            }

        }

        int w = width;
        int h = height;
        double[] lastcol = new double[w];
        
        for (int i = 0; i < w; i++){
            lastcol[i] = energycalmat[i][h-1];
        }
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
            pathans[height-1] = endpoint;
            for(int k = height-2; k >= 0; k--) {
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
        int temp = height;
        height =  width;
        width = temp;
        
        picRGB = matrixtranspose(picRGB);
        energymat = matrixtranspose(energymat);
        
        removeVerticalSeam(seam);
        
        temp = height;
        height =  width;
        width = temp;
        picRGB = matrixtranspose(picRGB);
        energymat = matrixtranspose(energymat);
        
    }
    public    void removeVerticalSeam(int[] seam) {     // remove vertical seam from current picture
        if (seam == null)
            throw new java.lang.IllegalArgumentException();
        if (width == 1)
            throw new java.lang.IllegalArgumentException();
        int oldw = width;
        int oldh = height;
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
        
        int[][] newpicRGB = new int[width-1][height];
        double[][] newenergymat = new double[width-1][height];
        int h = height;
        int w = width;
        for(int j = 0; j < h; j++) {
            for (int i = 0; i < w-1; i++) {
                if (i < seam[j]) {
                    newpicRGB[i][j] = picRGB[i][j];
                    newenergymat[i][j] = energymat[i][j];
                }
                else {
                    newpicRGB[i][j] = picRGB[i+1][j];
                    newenergymat[i][j] = energymat[i+1][j];
                }
            }
        }
        picRGB = newpicRGB;
        width = width-1;
        for(int j = 0; j < h; j++) {
            for(int i = 0; i < w-1; i++) {
                if (i == (seam[j]-1) || i == (seam[j]))
                    newenergymat[i][j] = energy(i, j);
            }
        }
        energymat = newenergymat;
        
    }

    public static void main(String[] args) {           //  unit testing (required)
        Picture pic = new Picture(args[0]);
        SeamCarver sc = new SeamCarver(pic);
        
    }
}