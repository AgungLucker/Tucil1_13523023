import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Files {
    private File file;
    private int N, M, P;
    private String S;
    private Map<Character, List<int[]>> blocks;

    public Files() {
        Scanner scanner = new Scanner(System.in);
        String filename;

        do {
            System.out.print("Masukkan nama file: ");
            filename = scanner.nextLine();
            file = new File(filename);

            if (!file.exists() || !file.isFile()) {
                System.out.println("File tidak ditemukan");
                System.out.println("Silakan coba lagi");
            }
        } while (!file.exists() || !file.isFile());

        scanner.close();
        readFile();
    }


    // Baca File dan parsing
    private void readFile() {
        try {
            Scanner fileReader = new Scanner(file);
            String[] dimensiDanJumlahHuruf = fileReader.nextLine().split("\\s+");
            N = Integer.parseInt(dimensiDanJumlahHuruf[0]);
            M = Integer.parseInt(dimensiDanJumlahHuruf[1]);
            P = Integer.parseInt(dimensiDanJumlahHuruf[2]);
            S = fileReader.nextLine();
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
    public Map<Character, List<int[]>> getBlocks() {
        return blocks;
    }



}
