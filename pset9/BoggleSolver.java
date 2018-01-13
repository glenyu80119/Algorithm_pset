import java.util.ArrayList;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver
{
    private DictTrie dt;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        dt = new DictTrie();
        int l = dictionary.length;
        for (int i = 0; i < l; i++) {
            dt.put(dictionary[i]);
        }

    }
    
    

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        int row = board.rows();
        int col = board.cols();
        SET<String> valid = new SET<String>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                boolean[][] marked = new boolean[row][col];
                marked[i][j] = true;
                Iterable<String> step1;
                if (board.getLetter(i, j) == 'Q') {
                    String newstring = String.valueOf(board.getLetter(i, j)) + "U";
                    step1 = expand(board, i, j, newstring, marked, new ArrayList<String>());
                }
                else {
                    step1 = expand(board, i, j, String.valueOf(board.getLetter(i, j)), marked, new ArrayList<String>());
                }
                for(String s: step1){
                    valid.add(s);
                }
            }
        }
        return valid;
    }
    
    private ArrayList<String> expand(BoggleBoard board, int i, int j, String s, boolean[][] marked, ArrayList<String> als) {
        int row = board.rows();
        int col = board.cols();
        
        // prefix section
        // add first if checked in dict
        if (s.length() >= 3) {
            if(!dt.hasprefix(s))
                return als;
            if (dt.contains(s)){
                als.add(s);
            }
        }
            
        // continue concat
        for(int m = -1; m < 2; m++) {
            for (int n = -1; n < 2; n++) {
                if ((m != 0 || n != 0) && ((i+m) >= 0 && (j+n) >= 0) && ((i+m) < row && (j+n) < col) && marked[i+m][j+n] == false) {
                    boolean[][] mark = new boolean[row][col];
                    for (int q = 0; q < row; q++) {
                        for (int w = 0; w < col; w++) {
                            mark[q][w] = marked[q][w];
                        }
                    }
                    String ss;
                    if (board.getLetter(i+m, j+n) == 'Q') {
                        ss =  s + String.valueOf(board.getLetter(i+m, j+n)) + "U";
                    }
                    else {
                        ss = s + String.valueOf(board.getLetter(i+m, j+n));
                    }
                    mark[i+m][j+n] = true;
                    als = expand(board, i+m, j+n, ss, mark, als);
                }
            }
        }
        return als;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        int l = word.length();
        if(l > 2 && l < 5)
            return 1;
        else if (l < 6)
            return 2;
        else if (l < 7)
            return 3;
        else if (l < 8)
            return 5;
        else
            return 11;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
