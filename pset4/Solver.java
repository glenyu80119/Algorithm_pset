import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;


public class Solver {
    private SearchNode last;
    private SearchNode lastSwap;
    private boolean solvable;
    private Stack<Board> trajectory;
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null)
            throw new java.lang.NullPointerException();
        SearchNode sn = new SearchNode(initial, 0, null);
        SearchNode snSwap = new SearchNode(initial.twin(), 0, null);
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pqSwap = new MinPQ<SearchNode>();
        pq.insert(sn);
        pqSwap.insert(snSwap);
        lastSwap = snSwap;
        last = sn;
        while(!pq.isEmpty()) {
            SearchNode smallest = pq.delMin();
            SearchNode smallestSwap = pqSwap.delMin();
            if (smallest.board.isGoal()) {
                last = smallest;
                solvable = true;
                break;
            }
            if (smallestSwap.board.isGoal()) {
                lastSwap = smallestSwap;
                solvable = false;
                break;
            }
            Iterable<Board> neighbors = smallest.board.neighbors();
            Iterable<Board> neighborsSwap = smallestSwap.board.neighbors();
            for (Board boardTemp: neighbors) {
                if (smallest.preSearchNode != null) {
                    if (!boardTemp.equals(smallest.preSearchNode.board)) {
                        SearchNode temp = new SearchNode(boardTemp, smallest.returnMove() +1, smallest);
                        pq.insert(temp);
                    }
                }
                else {
                    SearchNode temp = new SearchNode(boardTemp, smallest.returnMove() +1, smallest);
                    pq.insert(temp);
                }
            }
            for (Board boardTemp: neighborsSwap) {
                if (smallestSwap.preSearchNode != null) {
                    if (!boardTemp.equals(smallestSwap.preSearchNode.board)) {
                        SearchNode temp = new SearchNode(boardTemp, smallestSwap.returnMove() +1, smallestSwap);
                        pqSwap.insert(temp);
                    }
                }
                else {
                    SearchNode temp = new SearchNode(boardTemp, smallestSwap.returnMove() +1, smallestSwap);
                    pqSwap.insert(temp);
                }
            }
        }


    }
    private class SearchNode implements Comparable<SearchNode> {
        private int score;
        private int move;
        private Board board;
        private SearchNode preSearchNode;
        public SearchNode (Board b, int move, SearchNode pre) {
            score = b.manhattan() + move;
            this.move = move;
            board = b;
            preSearchNode = pre;
        }

        private int returnMove() {
            return this.move;
        }
        public int compareTo(SearchNode that) { 
            if (this.score > that.score)
                return 1;
            else if (this.score < that.score)
                return -1;
            else {
                if (this.board.hamming() < that.board.hamming())
                    return -1;
                else if (this.board.hamming() > that.board.hamming())
                    return 1;
                else 
                    return 0;
            }
        }
    }
    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (isSolvable())
            return last.returnMove();
        else
            return -1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (isSolvable()) {
            trajectory = new Stack<Board>();
            while (last != null) {
                trajectory.add(last.board);
                last = last.preSearchNode;
            }
            
            return trajectory;
        }
        else {
            return null;
        }
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        } 
    }
}