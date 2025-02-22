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
                board[row][unit] = '1';
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

    // Cek blok dapat ditempatkan pada lokasi X dalam board
    public boolean canPlaceBlock(List<int[]> block, int x, int y) {
        for (int[] unit : block) {
            int xPosition = unit[0] + x;
            int yPosition = unit[1] + y;
            if ((xPosition >= N || yPosition >= M || board[xPosition][yPosition] != '1' || board[xPosition][yPosition] == '.')) {
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
                if (board[row][unit] == '1') {
                    return true;
                }
            }
        }
        return false;
    }
    // Memetakan abjad pada Board dengan warna
    private Map<Character, String> fillColorBoard() {
        String[] colorPerLetter = {
            "\u001B[31m", "\u001B[32m", "\u001B[33m", "\u001B[34m", "\u001B[35m", "\u001B[36m", "\u001B[37m", 
        "\u001B[91m", "\u001B[92m", "\u001B[93m", "\u001B[94m", "\u001B[95m", "\u001B[96m", "\u001B[97m", 
        "\u001B[101m", "\u001B[102m", "\u001B[103m", "\u001B[104m", "\u001B[105m", "\u001B[106m", "\u001B[107m", 
        "\u001B[90m", "\u001B[100m"
        };

        Map <Character, String> blockColorMap = new HashMap<>();
        int i = 0;

        for (char[] row : board) {
            for (char unit : row) {
                if (!blockColorMap.containsKey(unit) && unit != '1') {
                    blockColorMap.put(unit, colorPerLetter[i % colorPerLetter.length]);
                    i++;
                }
            }
        }

        return blockColorMap;
    }

    public void printBoard() {
        Map <Character, String> blockColorMap = fillColorBoard();
        System.out.println();
        for (char[] row : board) {
            for (char unit : row) {
                if (unit == '.') {
                    System.out.print("  ");
                }
                else {
                    System.out.print(blockColorMap.get(unit) + unit + " " + "\u001B[0m");
                }
            }
            System.out.println();
        }
        System.out.println("\u001B[0m");
    }

}
