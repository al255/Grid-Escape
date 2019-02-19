import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Solution for Grid Escape problem given at Code Jam I/O for Women 2019.
 */
public class Solution {

    private int r;
    private int c;
    private int k;

    private static final char south = 'S';
    private static final char east = 'E';
    private static final char north = 'N';
    private static final char west = 'W';
    private static final String caseStr = "Case #";
    private static final String possible = "POSSIBLE";
    private static final String impossible = "IMPOSSIBLE";

    private char[] map;

    /**
     * Main method.
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt();  // tests
        Solution[] sol_arr = new Solution[t];
        for (int i = 1; i <= t; ++i) {
            Solution sol = new Solution();
            sol.r = in.nextInt();
            sol.c = in.nextInt();
            sol.k = in.nextInt();
            sol_arr[i - 1] = sol;
        }
        for (int i = 0; i < t; i++) {
            if (sol_arr[i].solve()) {
                sol_arr[i].printSuccess(i+1);
            } else {
                sol_arr[i].printFailure(i+1);
            }
        }
        in.close();
    }

    /**
     * Generating a labyrinth.
     * @return true if a labyrinth can be created, false if not.
     */
    private boolean solve() {
        if (this.k > this.c * this.r || this.k < 0 || this.r < 1 || this.c < 1 || this.k == (this.c * this.r) - 1) {
            // impossible. go to fail
            return false;
        }
        map = new char[this.r * this.c];
        // point 'winning' cells to the north to create exit routes
        for (int cell = 0; cell < this.k; cell++) {
            map[cell] = north;
        }
        // if map is a vertical line of cells, point all 'losing' cells to south
        if (this.c == 1) {
            for (int j = this.k; j < map.length; j++) {
                map[j] = south;
            }
            // and make the last one open to the north, creating a loop
            map[map.length - 1] = north;
            return true;
        }
        // if k < no of cells, need to create loop with no exit for the rest
        if (map.length - this.k > this.c) { // more than 1 row left
            for (int cell = this.k; cell < map.length - this.c; cell++) {
                // point all south
                map[cell] = south;
            }
        }
        int lastCell = map.length - this.c; // start cell of last row
        if (map.length - this.k < this.c || this.r == 1) {
            lastCell = this.k; // a certain cell in the last row
        }
        for (int cell = lastCell; cell < map.length - 1; cell++) {
            // point all east except for the last one
            map[cell] = east;
        }
        // point the last one west to create a loop
        map[map.length - 1] = west;
        return true;
    }

    /**
     * Print success message.
     * @param caseNumber number of the case
     */
    private void printSuccess(int caseNumber) {
        System.out.println(caseStr + caseNumber + ": " + possible);
        for (int j = 0; j < map.length; j++) {
            System.out.print(map[j]);
            if ((j + 1) % this.c == 0) {
                System.out.println();
            }
        }
    }

    /**
     * Print failure message.
     * @param caseNumber number of the case
     */
    private void printFailure(int caseNumber) {
        System.out.println(caseStr + caseNumber + ": " + impossible);
    }
}
