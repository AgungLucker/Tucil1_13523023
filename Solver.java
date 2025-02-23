import java.util.*;

public class Solver {
    private char[][] board;
    private List<Block> blocks;
    private int casesCount; 

    public Solver(char[][] board, Map<Character, List<int[]>> mapBlocks) {
        this.board = board;
        this.blocks = new ArrayList<>();
        this.casesCount = 0; 

        // Ubah map daftar Blok menjadi list dari objek block
        for (Map.Entry<Character, List<int[]>> entry : mapBlocks.entrySet()) {
            blocks.add(new Block(entry.getKey(), entry.getValue()));
        }
    }

    // Gette
    public Board getSolvedBoard() {
        return new Board(this.board);
    }
    public int getCasesCount() {
        return this.casesCount;
    }
    
    public boolean bruteForceSolve() {
        List<List<Block>> blockPermutations = getBlockPermutations();
        
        // Coba semua urutan permutasi blok 
        for (List<Block> blockOrder : blockPermutations) {
            if (tryCases(blockOrder, new Board(this.board), 0)) {
                return true;
            }
        }
        System.out.println("Tidak ada solusi yang ditemukan.");
        return false;
    }
    // Mencoba setiap
    private boolean tryCases(List<Block> blockOrder, Board currentBoard, int blockIdx) {
        if (blockIdx >= blockOrder.size()) {
            casesCount++;
            if (!currentBoard.hasEmptySpace()) { // Semua blok telah ditempatkan dan papan juga penuh
                this.board = currentBoard.getBoard(); 
                return true; 
            }
            return false; 
        }

        Block block = blockOrder.get(blockIdx);
        List<List<int[]>> blockTransformations = block.getBlockTransformations();

        // Coba semua transformasi (Rotasi + mirror) dari blok
        for (List<int[]> blockTransformation : blockTransformations) {
            casesCount++; 
            if (tryPlaceBlock(blockOrder, currentBoard, blockIdx, blockTransformation)) {
                return true;
            }
        }

        return false;
    }
    private boolean tryPlaceBlock(List<Block> blockOrder, Board currentBoard, int blockIdx, List<int[]> blockTransformation) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (currentBoard.canPlaceBlock(blockTransformation, x, y)) {
                    Board newBoard = new Board(currentBoard); // Buat salinan papan
                    newBoard.placeBlock(blockTransformation, x, y, blockOrder.get(blockIdx).getAbjad());

                    if (tryCases(blockOrder, newBoard, blockIdx + 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Mencari semua permutasi urutan blok
    private List<List<Block>> getBlockPermutations() {
        List<List<Block>> permutations = new ArrayList<>();
        permutationHelper(blocks, 0, permutations);
        return permutations;
    }

    private void permutationHelper(List<Block> blocks, int level, List<List<Block>> permutations) {
        if (level >= blocks.size()) {
            permutations.add(new ArrayList<>(blocks));
            return;
        }

        for (int i = level; i < blocks.size(); i++) {
            Collections.swap(blocks, level, i);
            permutationHelper(blocks, level + 1, permutations);
            Collections.swap(blocks, level, i);
        }
    }
}