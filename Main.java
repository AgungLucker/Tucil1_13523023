import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        Files fileReader = new Files();

        int N = fileReader.getN();
        int M = fileReader.getM();
        String S = fileReader.getS();
        Map<Character, List<int[]>> mapBlocks = fileReader.getBlocks();
        
        Board board;

        if (S.equals("CUSTOM")) {
            char[][] customBoard = fileReader.getCustomBoard();
            board = new Board(customBoard);
        } else {
            board = new Board(N, M);
        }
        Solver solver = new Solver(board.getBoard(), mapBlocks);

        // Mulai waktu pencarian
        long startTime = System.currentTimeMillis();

        if (solver.bruteForceSolve()) {
            long endTime = System.currentTimeMillis();
            Board solvedBoard = solver.getSolvedBoard();

            System.out.println("Solusi ditemukan:");
            solvedBoard.printBoard();
            System.out.println();
            System.out.println("Waktu pencarian: " + (endTime - startTime) + " ms");
            System.out.println("Banyak kasus yang ditinjau: " + solver.getCasesCount());
            System.out.println();

            fileReader.solutionToFile(solvedBoard.getBoard());
        } else {
            System.out.println("Tidak ada solusi.");
        }
    }
}
