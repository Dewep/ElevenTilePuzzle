import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * Author: MAIGRET Aurelien (am2074)
 */
public class Solver {

    /* Main */
    public static void main(String[] args) {
        String start = "*ad**b_c*addbdbd";
        String dest = "*ad**abd*b_cbddd";

        LinkedList<String> path = uniformCost(start, dest);
        for (String move:path) {
            System.out.println(move);
        }

        System.exit(0);
    }

    /* Uniform Cost Algorithm */
    public static LinkedList<String> uniformCost(String start, String dest) {
        PriorityQueue<LinkedList<String>> queue = new PriorityQueue<LinkedList<String>>(11, new LinkedListStringComparator());

        LinkedList<String> path = new LinkedList<String>();
        path.add(start);
        queue.add(path);

        while (true) {
            path = queue.poll();

            if (path == null) {
                return null;
            }

            String last = path.getLast();
            if (last.equals(dest)) {
                return path;
            }

            LinkedList<String> moves = nextMoves(last);
            for (String move:moves) {
                if (!path.contains(move)) {
                    LinkedList<String> newPath = new LinkedList<String>(path);
                    newPath.addLast(move);
                    queue.add(newPath);
                }
            }
        }
    }

    /* Find next moves of a given tile */
    public static LinkedList<String> nextMoves(String tile) {
        LinkedList<String> moves = new LinkedList<>();

        int space = tile.indexOf("_");

        // UP
        if (space >= 4 && tile.charAt(space - 4) != '*') {
            moves.add(swap(tile, space, space - 4));
        }
        // DOWN
        if (space <= 11 && tile.charAt(space + 4) != '*') {
            moves.add(swap(tile, space, space + 4));
        }
        // LEFT
        if (space % 4 != 0 && tile.charAt(space - 1) != '*') {
            moves.add(swap(tile, space, space - 1));
        }
        // RIGHT
        if ((space + 1) % 4 != 0 && tile.charAt(space + 1) != '*') {
            moves.add(swap(tile, space, space + 1));
        }

        return moves;
    }

    /* Comparator of LinkedList<String> */
    public static class LinkedListStringComparator implements Comparator<LinkedList<String>> {
        @Override
        public int compare(LinkedList<String> list1, LinkedList<String> list2) {
            return list1.size() - list2.size();
        }
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
