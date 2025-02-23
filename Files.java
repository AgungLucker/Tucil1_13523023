import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Files {
    private File file;
    private String[] dimensiDanJumlahAbjad;
    private int N, M, P;
    private String S;
    private char[][] customBoard;
    private boolean validFileFormat = true;
    private boolean validCustomBoard = true;
     private boolean validBlock = true;
    private Map<Character, List<int[]>> blocks;

    // Konstruktor baru yang menerima File sebagai parameter
    public Files(File file) {
        this.file = file;
        if (!file.getName().endsWith(".txt")) {

            validFileFormat = false;
        }
        else {
            readFile();
        }
    }

    // Metode untuk memeriksa validitas file
    public int isValid() {
        if (dimensiDanJumlahAbjad == null || dimensiDanJumlahAbjad.length < 3) {
            return 1;
        }
        if (N <= 0 || M <= 0 || P <= 0) {
            return 2;
        
        } if (!S.equals("DEFAULT") && !S.equals("CUSTOM")) {
            return 3;
        } if (blocks == null || blocks.size() != P) {
            return 4;
        } if (!validFileFormat) {
            return 5;
        } if (!validCustomBoard) {
            return 6;
        } if (!validBlock) {
            return 7; 
        }
        return 0; 
    }

    // Baca File dan parsing
    private void readFile() {
        try {
            Scanner fileReader = new Scanner(file);
            dimensiDanJumlahAbjad = fileReader.nextLine().split("\\s+");
            
            if (dimensiDanJumlahAbjad.length < 3) {
                return;
            }
            N = Integer.parseInt(dimensiDanJumlahAbjad[0]);
            M = Integer.parseInt(dimensiDanJumlahAbjad[1]);
            P = Integer.parseInt(dimensiDanJumlahAbjad[2]);
            S = fileReader.nextLine().trim();

            if (!S.equals("DEFAULT") && !S.equals("CUSTOM")) {
                return;
            }
            // Baca custom board dari input
            else if (S.equals("CUSTOM")) {
                customBoard = new char[N][M];
                for (int i = 0; i < N; i++) {
                    String line = fileReader.nextLine();
                    for (int j = 0; j < M; j++) {
                        if (line.charAt(j) != '.' && line.charAt(j) != 'X') {
                            validCustomBoard = false;
                            return;
                        }
                        if (line.charAt(j) == 'X') {
                            customBoard[i][j] = '0';
                        }
                        else {
                            customBoard[i][j] = line.charAt(j);
                        }
                    }
                }
            }
            // Baca blok
            blocks = new HashMap<>();
            int i = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                if (!line.isEmpty()) {
                    Set<Character> uniqueChars = new HashSet<>();
                    validBlock = true;
                    for (int j = 0; j < line.length(); j++) {
                        char unit = line.charAt(j);
                        if (unit != ' ') {
                            uniqueChars.add(unit);
                            if (uniqueChars.size() > 1) {
                                validBlock = false;
                                return;
                            }
                            blocks.putIfAbsent(unit, new ArrayList<>());
                            blocks.get(unit).add(new int[]{i, j});
                        }
                    }
                    i++;
                }
            }
            fileReader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan");
            e.printStackTrace();
        }
    }

    // Getter
    public int getN() {
        return N;
    }
    public int getM() {
        return M;
    }
    public int getP() {
        return P;
    }
    public String getS() {
        return S;
    }
    // Untuk kasus Custom
    public char[][] getCustomBoard() {
        return customBoard;
    }
    public Map<Character, List<int[]>> getBlocks() {
        return blocks;
    }

    // Simpan solusi ke file
    public void solutionToFile(char[][] board) {
        try {
            File outputFile = new File("output.txt");
            outputFile.createNewFile();
            Formatter fileWriter = new Formatter(outputFile);
            for (char[] row : board) {
                for (char unit : row) {
                    fileWriter.format("%c", unit);
                }
                fileWriter.format("\n");
            }
            fileWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}