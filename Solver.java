import java.util.LinkedList;
import java.io.PrintWriter;
import java.io.IOException;

// Author: MAIGRET Aurelien (am2074)
public class Solver {
    /* Main */
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
            String start = file.substring(0, 16);
            String dest = file.substring(17, 33);
            solvePuzzle(file, start, dest);
        }
        System.exit(0);
    }
    /* Solve function */
    public static void solvePuzzle(String file, String start, String dest) throws IOException {
        PrintWriter writer = new PrintWriter("solutions/" + file.replace('*', '%'));
        LinkedList<String> path = uniformCost(start, dest);
        for (int i = 0; i <= 12; i += 4) {
            String line = "";
            for (String move:path) {
                if (line.length() > 0) {
                    line += " ";
                }
                line += move.substring(i, i + 4);
            }
            writer.write(line + "\n");
        }
        writer.close();
    }
    /* Uniform Cost Algorithm */
    public static LinkedList<String> uniformCost(String start, String dest) {
        LinkedList<LinkedList<String>> queue = new LinkedList<LinkedList<String>>();
        LinkedList<String> path = new LinkedList<String>();
        path.add(start);
        queue.add(path);
        while (true) {
            path = queue.poll();
            if (path == null) {
                return null;
            }
            LinkedList<String> moves = nextMoves(path.getLast());
            for (String move:moves) {
                if (!path.contains(move)) {
                    LinkedList<String> newPath = new LinkedList<String>(path);
                    newPath.addLast(move);
                    if (move.equals(dest)) {
                        return newPath;
                    }
                    queue.add(newPath);
                }
            }
        }
    }
    /* Find next moves of a given tile */
    public static LinkedList<String> nextMoves(String tile) {
        LinkedList<String> moves = new LinkedList<>();
        int space = tile.indexOf("_");
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
    /* Swap 2 letters in a String */
    public static String swap(String str, int index1, int index2) {
        StringBuilder sb = new StringBuilder(str);
        char tmp = sb.charAt(index1);
        sb.setCharAt(index1, sb.charAt(index2));
        sb.setCharAt(index2, tmp);
        return sb.toString();
    }
}
