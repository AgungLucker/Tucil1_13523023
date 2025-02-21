import java.util.Arrays;
import java.util.List;

public class Board {
    private int N, M;
    private char[][] board;

    public Board(int N, int M) {
        this.N = N;
        this.M = M;
        this.board = new char[N][M];
        for (char[] row : board) {
            Arrays.fill(row, 'X');
        }
    }

    // Getter
    public char[][] getBoard() {
        return board;
    }

    public boolean canPlaceBlock(List<int[]> block, int x, int y) {
        for (int[] unit : block) {
            int xPosition = unit[0] + x;
            int yPosition = unit[1] + y;
            if (xPosition >= N || yPosition >= M || board[xPosition][yPosition] != 'X') {
                return false;
            }
        }
        return true;
    }

    public void placeBlock(List<int[]> block, int x, int y, char huruf) {
        for (int[] unit : block) {
            board[unit[0] + x][unit[1] + y] = huruf;
        }
    }


    public void printBoard() {
        for (char[] row : board) {
            System.out.println(new String(row));
        }
    }
}
