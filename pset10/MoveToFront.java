import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.StdOut;
public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        int[] R = new int[256];
        for (int i = 0; i < 256; i++) {
            R[i] = i;
        }
        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int now = (int) c;
            BinaryStdOut.write((char) R[now]);
            if (R[now] != 0) {
                for (int i = 0; i < 256; i++) {
                    if (R[i] < R[now])
                        R[i]++;
                }
                R[now] = 0;
            }
        }
        
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        int[] R = new int[256];
        for (int i = 0; i < 256; i++) {
            R[i] = i;
        }
        char cc = '\0';
        while(!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int now = (int) c;
            if (now != 0) {
                for (int i = 0; i < 256; i++) {
                    if (R[i] == now) {
                        BinaryStdOut.write((char) i);
                        cc = (char) i;
                    }                  
                }
                
                for (int i = 0; i < 256; i++) {
                    if (R[i] < now) {
                        R[i]++;
                    } 
                }
                R[(int) cc] = 0;
            }
            else
                BinaryStdOut.write(cc);
        }
        
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args[0].indexOf("-") == 0)
            encode();
        else if (args[0].indexOf("+") == 0)
            decode();
        else
            StdOut.println("Wrong!");
    }
}