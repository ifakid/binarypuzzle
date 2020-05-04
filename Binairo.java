import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

class Binairo {
    public int[][] grid;
    private static final int b = -1;
    public long start;

    public Binairo(int[][] grid){
        this.grid = grid;
        //n = grid.length;
    }

    public void backtrack(int[][] grid, int i, int j){
        int[][] temp = cloneGrid(grid);
        if (i == grid.length) {
            if (checkCols(grid) && checkRows(grid)) {
                printGrid(grid);
                long end = System.currentTimeMillis();
                System.out.println("Time elapsed: "+(end-start)+"ms");
                System.exit(0);
            }
        } else {
            for (int k = 0; k <= 1; k++) {
                if (this.grid[i][j] == b) {
                    temp[i][j] = k;
                }
                if (checkRows(temp) && checkCols(temp)) {
                    if (j == grid.length - 1)
                        backtrack(temp, i + 1, 0);
                    else //end of the row
                        backtrack(temp, i, j + 1);
                }
            }
        }
    }

    public void solve(){
        long start = System.currentTimeMillis();
        int j,x,y;
        boolean valid = true;
        int[][] temp;
        boolean brk;

        for (int i = 0; i < Math.pow(2,Math.pow(grid.length,2)); i++){
            temp = cloneGrid(grid);
            valid = true;
            brk = false;

            for (j = 0; j < Math.pow(grid.length,2); j++){
                x = j/grid.length;
                y = j%grid.length;

                temp[x][y] = ((i & (1 << j)) > 0) ? 1 : 0;

                if (grid[x][y] != -1)
                    if (temp[x][y] != grid[x][y]) {
                        brk = true;
                        break;
                    }
            }

            //If the combination doesn't follow the hint, skip
            if (brk) continue;

            valid = checkCols(temp) && checkRows(temp);

            if (valid) {
                long end = System.currentTimeMillis();
                System.out.println("Time elapsed: "+(end-start)+"ms");
                printGrid(temp);
                break;
            }
        }
        if (!valid) System.out.println("No solution found!");
    }

    public boolean checkRows(int[][] grid){
        int trueCount, falseCount;
        HashSet<Integer> values = new HashSet<Integer>();
        int completed = 0;
        StringBuilder current;
        int last;
        int count;

        for (int[] ints : grid) {
            trueCount = 0;
            falseCount = 0;
            current = new StringBuilder();
            count = 0;
            last = b;

            for (int j = 0; j < grid.length; j++) {
                if (ints[j] != -1) {
                    current.append(Integer.toString(ints[j]));
                    if (ints[j] == 1) {
                        trueCount++;
                    } else {
                        falseCount++;
                    }

                    if (last == ints[j]) {
                        count++;
                    } else {
                        count = 1;
                    }
                    last = ints[j];

                    //Three in a row
                    if (count == 3) {
                        return false;
                    }
                } else {
                    last = ints[j];
                    count = 0;
                }
            }

            if (current.length() == grid.length) {
                if (trueCount != falseCount) return false;
                values.add(Integer.parseInt(current.toString(), 2));
                completed++;
            }
        }
        //Values are unique
        return values.size() == completed;
    }

    public boolean checkCols(int[][] grid){
        int trueCount, falseCount;
        HashSet<Integer> values = new HashSet<Integer>();
        StringBuilder current;
        int completed = 0;

        int last;
        int count;

        for (int i = 0; i < grid.length; i++){
            trueCount = 0;
            falseCount = 0;
            current = new StringBuilder();
            count = 0;
            last = -1;

            for (int[] ints : grid) {
                if (ints[i] != -1) {
                    current.append(Integer.toString(ints[i]));
                    if (ints[i] == 1) {
                        trueCount++;
                    } else {
                        falseCount++;
                    }

                    if (last == ints[i]) {
                        count++;
                    } else {
                        count = 1;
                    }
                    last = ints[i];

                    if (count == 3) {
                        return false;
                    }
                } else {
                    last = ints[i];
                    count = 0;
                }
            }

            if (current.length() == grid.length) {
                if (trueCount != falseCount) return false;
                values.add(Integer.parseInt(current.toString(), 2));
                completed++;
            }
        }
        return values.size() == completed;
    }

    public void printGrid(int[][] grid){
        for (int[] integer: grid){
            for (int i: integer){
                if (i == -1) System.out.print("n ");
                else System.out.print(Integer.toString(i)+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int[][] cloneGrid(int[][] grid){
        int[][] copy = new int[grid.length][grid.length];
        for (int i = 0; i < grid.length; i++){
            System.arraycopy(grid[i], 0, copy[i], 0, grid.length);
        }
        return copy;
    }

    public static void main(String[] args) {
        //b = blank space
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter board size: ");
        int n = sc.nextInt();
        sc.nextLine();
        int[][] grid = new int[n][n];
        String[] rowParsed;
        System.out.println("Enter values for grid (space-separated, b to indicate blank space): ");
        for (int i = 0; i < n; i++){
            rowParsed = sc.nextLine().split(" ");
            for (int j = 0; j < n; j++){
                try {
                    grid[i][j] = Integer.parseInt(rowParsed[j]);
                } catch (Exception e){
                    grid[i][j] = -1;
                }
            }
        }

        Binairo b = new Binairo(grid);
        //b.start = System.currentTimeMillis();
        b.solve();
        //b.backtrack(grid,0,0);
    }
}
