import java.util.LinkedList;
import java.io.PrintWriter;
import java.io.IOException;

/* Author: MAIGRET Aurelien (am2074) */
public class Solver {
    /* Main method, which calls the solver for each puzzle */
    public static void main(String[] args) throws IOException {
        String[] files = {
            "*ad**b_c*addbdbd2*ad**abd*b_cbddd.txt",
            "*dd**dda*bdcbba_2*dd**dac*d_abbbd.txt",
            "*c_**ddd*bbdbdaa2*bd**cdd*addb_ba.txt",
            "*ad**ad_*cdbbddb2*ad**cad*dd_bbdb.txt",
            "*dd**_bd*dacbbda2*dd**dd_*aacbdbb.txt",
            "*db**aab*dc_bddd2*ba**ddc*b_dbdda.txt",
            "*ca**bdd*dddbab_2*dc**dbd*ad_bdab.txt",
            "*cd**daa*dbbbd_d2*da**cad*db_bdbd.txt",
            "*dc**bad*_dbbdda2*bd**cdb*dd_bdaa.txt",
            "*dd**ba_*acdbdbd2*db**dca*dadbdb_.txt",
            "*dc**d_d*ababbdd2*cd**dbd*_adbabd.txt",
            "*bb**cda*ddabd_d2*bd**bad*c_dbdad.txt",
            "*da**cdd*d_abbdb2*ac**_ad*dddbbdb.txt",
            "*dd**bad*_dabbcd2*_d**dad*ddcbbba.txt",
            "*ac**bdd*a_bbddd2*c_**dbd*dadbbad.txt",
            "*dd**abd*dcabd_b2*da**d_b*cadbddb.txt"
        };
        for (String file:files) {
            String start = file.substring(0, 16); // Start of the puzzle
            String dest = file.substring(17, 33); // End of the puzzle
            solvePuzzle(file, start, dest);
        }
        System.exit(0);
    }
    /* Solver method */
    public static void solvePuzzle(String file, String start, String dest) throws IOException {
        PrintWriter writer = new PrintWriter(file.replace('*', '+')); // I'm a Window user
        LinkedList<String> path = uniformCost(start, dest); // Call the uniform cost algorithm to find the best solution
        for (int i = 0; i <= 12; i += 4) { // Write the solution into the file
            for (String move:path) {
                writer.write(move.substring(i, i + 4));
                writer.write(" ");
            }
            writer.write("\n");
        }
        writer.close();
    }
    /* Uniform Cost Algorithm */
    public static LinkedList<String> uniformCost(String start, String dest) {
        LinkedList<LinkedList<String>> queue = new LinkedList<LinkedList<String>>(); // Creation of a queue
        LinkedList<String> path = new LinkedList<String>(); // Each element in the queue will be a possible solution (= a list of paths)
        path.add(start);
        queue.add(path);
        while (true) {
            path = queue.poll(); // Retrieves and removes the head of the queue
            if (path == null) { // If the head is null, the queue is empty (= no solution)
                return null;
            }
            LinkedList<String> moves = nextMoves(path.getLast()); // Retrieves all the next possible moves
            for (String move:moves) {
                if (!path.contains(move)) { // If this solution had not already used
                    LinkedList<String> newPath = new LinkedList<String>(path); // Copy of the current path
                    newPath.addLast(move); // Add the current move
                    if (move.equals(dest)) { // If the move is the destination, this is the best solution
                        return newPath;
                    }
                    queue.add(newPath); // Else, insert the this new solution in the queue
                }
            }
        }
    }
    /* Find next moves of a given tile */
    public static LinkedList<String> nextMoves(String tile) {
        LinkedList<String> moves = new LinkedList<>();
        int space = tile.indexOf("_"); // Current position
        if (space >= 4 && tile.charAt(space - 4) != '*') { // UP
            moves.add(swap(tile, space, space - 4));
        }
        if (space <= 11 && tile.charAt(space + 4) != '*') { // DOWN
            moves.add(swap(tile, space, space + 4));
        }
        if (space % 4 != 0 && tile.charAt(space - 1) != '*') { // LEFT
            moves.add(swap(tile, space, space - 1));
        }
        if ((space + 1) % 4 != 0 && tile.charAt(space + 1) != '*') { // RIGHT
            moves.add(swap(tile, space, space + 1));
        }
        return moves;
    }
    /* Swap 2 characters in a String */
    public static String swap(String str, int index1, int index2) {
        StringBuilder sb = new StringBuilder(str);
        char tmp = sb.charAt(index1);
        sb.setCharAt(index1, sb.charAt(index2));
        sb.setCharAt(index2, tmp);
        return sb.toString();
    }
}
