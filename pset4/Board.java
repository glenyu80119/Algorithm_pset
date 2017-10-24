import java.util.ArrayList;


public class Board {
    private char[] board;
    private byte len;
    private byte x_0;
    private byte y_0;
    private ArrayList<Board> neighbor;

    
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
    {                                      // (where blocks[i][j] = block in row i, column j)
        len = (byte) blocks.length;
        board = new char[len * len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int temp = blocks[i][j];
                if (temp == 0) {
                    x_0 = (byte) i;
                    y_0 = (byte) j;
                }
                board[i*len +j] = (char) temp;
            }
        }
        

    }
                                           
    public int dimension()                 // board dimension n
    {
        return len;
    }
    public int hamming()                   // number of blocks out of place
    {
        short hamm = 0;
        
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (board[i*len +j] != (i*len+j+1) && board[i*len +j] != 0)
                    hamm++;
            }
        }
        return hamm;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int manh = 0;
        for (int k = 0; k < len*len; k++) {
            if (board[k] != 0) {
                int i = (board[k]-1) / len;
                int j = (board[k]-1) % len;
                int a = k / len;
                int b = k % len;
                manh += Math.abs(i-a);
                manh += Math.abs(j-b);
            }
        }
        return manh;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (i == (len-1) && j == (len-1))
                    return true;
                if (board[i*len +j] != (i*len+j+1))
                    return false;
            }
        }
        return true;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int[][] blockstwin = new int[len][len];
        short k = 0;
        while (true) {
            if (board[k] != 0 && board[k+1] != 0) {
                char temp = board[k];
                board[k] = board[k+1];
                board[k+1] = temp;
                break;
            }
            else 
                k++;
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                blockstwin[i][j] = (int) board[i*len +j];
            }
        }
        
        char temp = board[k];
        board[k] = board[k+1];
        board[k+1] = temp;
        
        return new Board(blockstwin);
    }
    public boolean equals(Object y)        // does this board equal y?\
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.len != that.len)
            return false;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (this.board[i*len+j] != that.board[i*len+j])
                    return false;
            }
        }
        return true;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        int[][] block = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                char temp = board[i*len +j];
                block[i][j] = (int) temp;
            }
        }
        
        neighbor = new ArrayList<Board>();
        if (x_0 - 1 >= 0) {
            block[x_0][y_0] = block[x_0-1][y_0];
            block[x_0-1][y_0] = 0;
            neighbor.add(new Board(block));
            block[x_0-1][y_0] = block[x_0][y_0];
            block[x_0][y_0] = 0;
        }
        if (x_0 + 1 < len) {
            block[x_0][y_0] = block[x_0+1][y_0];
            block[x_0+1][y_0] = 0;
            neighbor.add(new Board(block));
            block[x_0+1][y_0] = block[x_0][y_0];
            block[x_0][y_0] = 0;
        }
        if (y_0 - 1 >= 0) {
            block[x_0][y_0] = block[x_0][y_0-1];
            block[x_0][y_0-1] = 0;
            neighbor.add(new Board(block));
            block[x_0][y_0-1] = block[x_0][y_0];
            block[x_0][y_0] = 0;
        }
        if (y_0 + 1 < len) {
            block[x_0][y_0] = block[x_0][y_0+1];
            block[x_0][y_0+1] = 0;
            neighbor.add(new Board(block));
            block[x_0][y_0+1] = block[x_0][y_0];
            block[x_0][y_0] = 0;
        }
        return neighbor;
            
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(len + "\n");
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                char temp = board[i*len+j];
                int forString = (int) temp;
                s.append(String.format("%2d ", forString));
            }
        s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {

    }
}