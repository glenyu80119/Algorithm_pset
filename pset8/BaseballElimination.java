import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination {
    private int numberTeam;
    private String [] nameTeam;
    private int [][] games;
    private FlowNetwork fn;
    private FordFulkerson ff;
    
    public BaseballElimination(String filename) {                   // create a baseball division from given filename in format specified below
        In in = new In(filename);
        numberTeam = in.readInt();
        nameTeam = new String [numberTeam];
        games = new int[numberTeam][numberTeam+3];
        
        for(int j = 0; j < numberTeam; j++){
            nameTeam[j] = in.readString();
            for(int i = 0; i < numberTeam+3; i++){
                games[j][i] = in.readInt();
            }
        }
        
        /*for(int i = 0; i < games.length; i++){
            StdOut.printf("%s  ", nameTeam[i]);
            for(int j = 0; j < games[0].length; j++){
                StdOut.printf("%d ",games[i][j]);
            }
            StdOut.println();
        }*/
        
    }
    
    
    public int numberOfTeams(){                       // number of teams
        int n = numberTeam;
        return n;
    }
    public Iterable<String> teams(){                   // all teams
        ArrayList<String> name = new ArrayList<String>();
        for(int j = 0; j < numberTeam; j++){
            name.add(nameTeam[j]);
        }
        return name;
    }
    public int wins(String team) {                     // number of wins for given team
        int order = -1;
        for(int i = 0; i < numberTeam; i++){
            if(nameTeam[i].equals(team)){
                order = i;
                break;
            }
        }
        if (order == -1)
            throw new java.lang.IllegalArgumentException();
        int ans = games[order][0];
        return ans;
    }
    public int losses(String team){                    // number of losses for given team
        int order = -1;
        for(int i = 0; i < numberTeam; i++){
            if(nameTeam[i].equals(team)){
                order = i;
                break;
            }
        }
        if (order == -1)
            throw new java.lang.IllegalArgumentException();
        int ans = games[order][1];
        return ans;
    }
    public int remaining(String team){                 // number of remaining games for given team
        int order = -1;
        for(int i = 0; i < numberTeam; i++){
            if(nameTeam[i].equals(team)){
                order = i;
                break;
            }
        }
        if (order == -1)
            throw new java.lang.IllegalArgumentException();
        int ans = games[order][2];
        return ans;
    }
    public int against(String team1, String team2){    // number of remaining games between team1 and team2
        int order1 = -1, order2 = -1;
        for(int i = 0; i < numberTeam; i++){
            if(nameTeam[i].equals(team1)){
                order1 = i;
                break;
            }
        }
        for(int i = 0; i < numberTeam; i++){
            if(nameTeam[i].equals(team2)){
                order2 = i;
                break;
            }
        }
        if (order1 == -1 || order2 == -1)
            throw new java.lang.IllegalArgumentException();
        
        int ans = games[order1][order2+3];
        return ans;
    }
    public boolean isEliminated(String team) {             // is given team eliminated?
        Iterable<String>  ans = certificateOfElimination(team);

        if (ans != null)
            return true;
        return false;
    }
    public Iterable<String> certificateOfElimination(String team){  // subset R of teams that eliminates given team; null if not eliminated
        int V = ((numberTeam-1) * (numberTeam-2)) / 2 + (numberTeam-1) + 2;
        int E = ((numberTeam-1) * (numberTeam-2)) / 2 + (numberTeam-1) + ((numberTeam-1) * (numberTeam-2));
        double gg = 0;
        int order = -1;
        ArrayList<String> ans = new ArrayList<String>();
        for(int i = 0; i < numberTeam; i++){
            if(nameTeam[i].equals(team)){
                order = i;
                break;
            }
        }
        if (order == -1)
            throw new java.lang.IllegalArgumentException();
        
        for(int i = 0; i < numberTeam; i++){
            if(games[order][0] + games[order][2] <  games[i][0]){
                ans.add(nameTeam[i]);
                return ans;
            }
        }
        
        int q = 1;
        fn = new FlowNetwork(V);
        
        for(int j = 0; j < numberTeam-2; j++){
            for(int i = 0; i < numberTeam-2-j; i++){
                if((i+j+1) < order) {
                    FlowEdge e = new FlowEdge(0, q, games[j][i+j+1+3]);
                    gg = gg+games[j][i+j+1+3];
                    q++;
                    fn.addEdge(e);
                }
                else{
                    if(j < order){
                        FlowEdge e = new FlowEdge(0, q, games[j][i+j+2+3]);
                        gg = gg+games[j][i+j+2+3];
                        q++;
                        fn.addEdge(e);
                    }
                    else{
                        FlowEdge e = new FlowEdge(0, q, games[j+1][i+j+2+3]);
                        gg = gg+games[j+1][i+j+2+3];
                        q++;
                        fn.addEdge(e);
                    }
                }
            }
        }
        int vn = ((numberTeam-1) * (numberTeam-2)) / 2;
        q = 1;
        
        for(int j = 0; j < numberTeam-2; j++){
            for(int i = 0; i < numberTeam-2-j; i++){
                    FlowEdge e = new FlowEdge(q, vn+j+1, Double.POSITIVE_INFINITY);
                    FlowEdge w = new FlowEdge(q, vn+i+j+2, Double.POSITIVE_INFINITY);
                    q++;
                    fn.addEdge(e);
                    fn.addEdge(w);
            }

        }
        for(int j = 0; j < numberTeam-1; j++){
            if (j < order){
                FlowEdge e = new FlowEdge(q, V-1, games[order][0] + games[order][2] - games[j][0]);
                q++;
                fn.addEdge(e);
            }
            else{
                FlowEdge e = new FlowEdge(q, V-1, games[order][0] + games[order][2] - games[j+1][0]);
                q++;
                fn.addEdge(e);
            }
        }
        
        
        ff = new FordFulkerson(fn, 0, V-1);
        if (ff.value() == gg)
            return null;
        else{
            for(int i = vn+1; i < (V-1); i++){
                if (ff.inCut(i)){
                    if((i-vn-1) < order){
                        ans.add(nameTeam[i-vn-1]);
                    }
                    else{
                        ans.add(nameTeam[i-vn]);
                    }
                }
            }
        }
        return ans;
        
        
    }
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }  
    }    
}