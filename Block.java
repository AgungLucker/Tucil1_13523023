import java.util.ArrayList;
import java.util.List;

public class Block {
    private char abjad;
    private List<int[]> block;

    public Block(char abjad, List<int[]> block) {
        this.abjad = abjad;
        this.block = normalizeBlockCoords(block);
    }

    // Getter
    public char getAbjad() {
        return abjad;
    }

    public List<int[]> getBlock() {
        return block;
    }

    private List<int[]> normalizeBlockCoords(List<int[]> block) {
        int minX = 99999;
        int minY = 99999;

        for (int[] blockCoord : block) {
            minX = Math.min(minX, blockCoord[0]);
            minY = Math.min(minY, blockCoord[1]);
        }

        List<int[]> normalizedBlockCoords = new ArrayList<>();
        for (int[] blockCoord : block) {
            normalizedBlockCoords.add(new int[]{blockCoord[0] - minX, blockCoord[1] - minY});
        }
        return normalizedBlockCoords;
    }
    // Rotasi koordinat blok 90 derajat searah jarum jam
    private List<int[]> rotateBlock90(List<int[]> block) {
        List<int[]> rotatedBlockCoords = new ArrayList<>();
        for (int[] coord : block) {
            rotatedBlockCoords.add(new int[]{coord[1], -coord[0]}); // (x,y) -> (y,-x)
        }
        return normalizeBlockCoords(rotatedBlockCoords);
    }

    private List<int[]> mirrorXBlock(List<int[]> block) {
        List<int[]> mirroredBlockCoords = new ArrayList<>();

        int maxX = -99999;

        for (int[] blockCoord : block) {
            maxX = Math.max(maxX, blockCoord[0]);
        }
        for (int[] coord : block) {
            mirroredBlockCoords.add(new int[]{maxX - coord[0], coord[1]});
        }
        return normalizeBlockCoords(mirroredBlockCoords);
    }

    private List<int[]> mirrorYBlock(List<int[]> block) {
        List<int[]> mirroredBlockCoords = new ArrayList<>();

        int maxY = -99999;

        for (int[] blockCoord : block) {
            maxY = Math.max(maxY, blockCoord[1]);
        }
        for (int[] coord : block) {
            mirroredBlockCoords.add(new int[]{coord[0], maxY - coord[1]});
        }
        return normalizeBlockCoords(mirroredBlockCoords);
    }


    private boolean isBlockPositionSame(List<int[]> block1, List<int[]> block2) {
        if (block1.size() != block2.size()) {
            return false;
        }
        for (int i = 0; i < block1.size(); i++) {
            if (block1.get(i)[0] != block2.get(i)[0] || block1.get(i)[1] != block2.get(i)[1]) {
                return false;
            }
        }
        return true;
    }

    // Cek blok sudah ada di list transformasi block
    private boolean containSameBlock(List<List<int[]>> blockTransformations, List<int[]> newBlock) {
        for (List<int[]> blockTransformation : blockTransformations) {
            if (isBlockPositionSame(blockTransformation, newBlock)) {
                return true;
            }
        }
        return false;
    }

    // Rotasi blok 0, 90, 180, 270 derajat
    private void allBlockRotation(List<List<int[]>> blockTransformations, List<int[]> currentBlock) {
        for (int i = 0; i < 4; i++) { 
            if (!containSameBlock(blockTransformations, currentBlock)) {
                blockTransformations.add(new ArrayList<>(currentBlock));
            }
            currentBlock = rotateBlock90(currentBlock);
        }
        
    }

    public List<List<int[]>> getBlockTransformations() {
        List<List<int[]>> blockTransformations = new ArrayList<>();

        List<int[]> mirrorX = mirrorXBlock(block);
        List<int[]> mirrorY = mirrorYBlock(block);
        List<int[]> mirrorXY = mirrorXBlock(mirrorY);

        allBlockRotation(blockTransformations, block);

        // Transformasi untuk kemungkinan mirror block
        if(!isBlockPositionSame(block, mirrorX)) {
            allBlockRotation(blockTransformations, mirrorX);
        }
        if(!isBlockPositionSame(block, mirrorY)) {
            allBlockRotation(blockTransformations, mirrorY);
        }
        if(!isBlockPositionSame(block, mirrorXY)) {
            allBlockRotation(blockTransformations, mirrorXY);
        }

        return blockTransformations;
    }

}
