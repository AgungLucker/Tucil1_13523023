import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Files {
    private File file;
    private String[] dimensiDanJumlahAbjad;
    private int N, M, P;
    private String S;
    private char[][] customBoard;
    private Map<Character, List<int[]>> blocks;

    public Files() {
        Scanner scanner = new Scanner(System.in);
        String filename;

        do {
            System.out.print("Masukkan nama file: ");

            filename = scanner.nextLine();
            System.out.println();
            file = new File(filename);

            if (!file.exists() || !file.isFile()) {
                System.out.println("File tidak ditemukan");
                System.out.println("Silahkan coba lagi");
            }
            else {
                readFile();
                if (dimensiDanJumlahAbjad.length < 3) {
                    System.out.println("Konfigurasi dimensi dan Abjad tidak valid");
                    System.out.println("Silahkan coba lagi");
                    file = null;
                }
                else if(N <= 0 || M <= 0 || P <= 0) {
                    System.out.println("Dimensi dan jumlah abjad tidak valid");
                    System.out.println("Silahkan coba lagi");
                    file = null;
                }
                else if (!S.equals("DEFAULT") && !S.equals("CUSTOM")) {
                    System.out.println("Konfigurasi kasus tidak valid.");
                    System.out.println("Silahkan coba lagi");
                    file = null;
                }
                else if(blocks.size() != P) {
                    System.out.println("Jumlah huruf tidak sesuai");
                    System.out.println("Silahkan coba lagi");
                    file = null;
                }
            }
        } while (file == null || !file.exists() || !file.isFile());

        scanner.close();
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
                        customBoard[i][j] = line.charAt(j);
                    }
                }
                
            }
            // Baca blok
            blocks = new HashMap<>();
            int i = 0;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                if (!line.isEmpty()) {
                    for (int j = 0; j < line.length(); j++) {
                        char unit = line.charAt(j);
                        if (unit != ' ') {
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
