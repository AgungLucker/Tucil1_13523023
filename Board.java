import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int N, M;
    private char[][] board;


    // Buat objek board baru dari kolom dan baris
    public Board(int N, int M) {
        this.N = N;
        this.M = M;
        this.board = new char[N][M];
        for (int row = 0; row < N; row++) {
            for (int unit = 0; unit < M; unit++) {
                board[row][unit] = '0';
            }
        }
    }

    // Buat objek board baru dari array board
    public Board(char[][] board) {
        this.N = board.length;
        this.M = board[0].length;
        this.board = new char[N][M];
        for (int i = 0; i < N; i++) {
            System.arraycopy(board[i], 0, this.board[i], 0, M);
        }
    }
    // Buat objek board baru dari board lain
    public Board(Board baseBoard) {
        this.N = baseBoard.N;
        this.M = baseBoard.M;
        this.board = new char[N][M];
        for (int i = 0; i < N; i++) {
            System.arraycopy(baseBoard.board[i], 0, this.board[i], 0, M);
        }
    
    }   

    // Getter
    public char[][] getBoard() {
        return board;
    }

    // Cek blok dapat ditempatkan pada lokasi yang kosong dalam board
    public boolean canPlaceBlock(List<int[]> block, int x, int y) {
        for (int[] unit : block) {
            int xPosition = unit[0] + x;
            int yPosition = unit[1] + y;
            if ((xPosition >= N || yPosition >= M || board[xPosition][yPosition] != '0' || board[xPosition][yPosition] == '.')) {
                return false;
            }
        }
        return true;
    }

    public void placeBlock(List<int[]> block, int x, int y, char abjad) {
        for (int[] unit : block) {
            board[unit[0] + x][unit[1] + y] = abjad;
        }
    }

    // Cek apakah papan masih ada space kosong
    public boolean hasEmptySpace() {
        for (int row = 0; row < N; row++) {
            for (int unit = 0; unit < M; unit++) {
                if (board[row][unit] == '0') {
                    return true;
                }
            }
        }
        return false;
    }
    

}
