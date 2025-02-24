import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenuGUI mainMenu = new MainMenuGUI();
            mainMenu.setVisible(true);
        });
    }
}

// Tampilan Menu awal
class MainMenuGUI extends JFrame {
    public MainMenuGUI() {
       setTitle("IQ Puzzler Solver");
       setSize(300, 200);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       JPanel mainPanel = new JPanel();
       mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

       // Margin sekitar button
       mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50)); 

       JButton startButton = new JButton("Start");
       JButton exitButton = new JButton("Exit");

       Dimension buttonSize = new Dimension(150, 30);
       startButton.setMaximumSize(buttonSize);
       exitButton.setMaximumSize(buttonSize);

       startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
       exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

       startButton.addActionListener(e -> {
           SolverGUI SolverGUI = new SolverGUI(MainMenuGUI.this);
           SolverGUI.setVisible(true);
           setVisible(false);
       });

       exitButton.addActionListener(e -> System.exit(0));

       mainPanel.add(Box.createVerticalGlue()); 
       mainPanel.add(startButton);
       mainPanel.add(Box.createVerticalStrut(10));
       mainPanel.add(exitButton);
       mainPanel.add(Box.createVerticalGlue()); 

       add(mainPanel);
   }

}

// Tampilan Solver
class SolverGUI extends JFrame {
    private JTextField filePathField;
    private JButton solveButton, saveButton, exitButton;
    private JTextPane resultPane;
    private JLabel statusLabel;
    private Files fileReader;
    private MainMenuGUI mainMenu;
    private Board solvedBoard;

    public SolverGUI(MainMenuGUI mainMenu) {
        this.mainMenu = mainMenu;
        setTitle("IQ Puzzler Solver");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        filePathField = new JTextField(30);

        JButton browseButton = new JButton("Browse File");
        browseButton.addActionListener(new inputFile());
        inputPanel.add(new JLabel("File Path:"));
        inputPanel.add(filePathField);
        inputPanel.add(browseButton);

        // Panel untuk hasil solusi
        JPanel solvePanel = new JPanel(new FlowLayout());
        solveButton = new JButton("Solve");
        solveButton.setEnabled(false);
        solveButton.addActionListener(new SolverAndResultDisplay());
        solvePanel.add(solveButton);

        saveButton = new JButton("Save to File");
        saveButton.setEnabled(false);
        saveButton.addActionListener(e -> saveSolutionToFile());
        solvePanel.add(saveButton);
        
        exitButton = new JButton("Exit");
        exitButton.setEnabled(false);
        exitButton.addActionListener(e -> {
            mainMenu.setVisible(true);
            dispose();
        });
        solvePanel.add(exitButton);

        resultPane = new JTextPane();
        resultPane.setEditable(false);
        resultPane.setFont(new Font("Monospaced", Font.BOLD, 17));
        JScrollPane scrollPane = new JScrollPane(resultPane);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(solvePanel, BorderLayout.NORTH);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel("Choose file");

        add(inputPanel, BorderLayout.NORTH);
        add(outputPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }
    // Mekanisme untuk input file
    private class inputFile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile.getAbsolutePath());
                fileReader = new Files(selectedFile);
                if (fileReader.isValid() == 0) {
                    solveButton.setEnabled(true);
                    statusLabel.setText("File valid. Klik solve untuk memulai solver");
                } else {
                    solveButton.setEnabled(false);
                    if (fileReader.isValid() == 1) {
                        statusLabel.setText("File tidak valid. Konfigurasi tidak valid.");
                    } else if (fileReader.isValid() == 2) {
                        statusLabel.setText("File tidak valid. Dimensi dan banyak huruf blok harus positif.");
                    } else if (fileReader.isValid() == 3) {
                        statusLabel.setText("File tidak valid. Jenis kasus harus DEFAULT atau CUSTOM.");
                    } else if (fileReader.isValid() == 4) {
                        statusLabel.setText("File tidak valid. Jumlah blok tidak sesuai.");
                    } else if (fileReader.isValid() == 5) {
                        statusLabel.setText("File tidak valid. Format file tidak sesuai.");
                    } else if (fileReader.isValid() == 6) {
                        statusLabel.setText("File tidak valid. Papan Custom tidak valid.");
                    } else if (fileReader.isValid() == 7) {
                        statusLabel.setText("File tidak valid. Blok tidak valid.");
                    }
                    filePathField.setText("");
                }
            }
        }
    }

    // Mekanisme dari button solve
    private class SolverAndResultDisplay implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int N = fileReader.getN();
            int M = fileReader.getM();
            String S = fileReader.getS();
            Map<Character, List<int[]>> mapBlocks = fileReader.getBlocks();
            Board board;
            if (S.equals("CUSTOM")) {   
                board = new Board(fileReader.getCustomBoard());
            }
            else {
                board = new Board(N, M);
            }
            Solver solver = new Solver(board.getBoard(), mapBlocks);
            long startTime = System.currentTimeMillis(); 
            boolean solvable = solver.bruteForceSolve();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            if (solvable) {
                solvedBoard = solver.getSolvedBoard();
                displayResult(solvedBoard, duration, solver.getCasesCount());
                statusLabel.setText("Solusi berhasil ditemukan.");
                saveButton.setEnabled(true);
                exitButton.setEnabled(true);
            } else {
                resultPane.setText("Tidak ada solusi.");
                statusLabel.setText("Tidak ada solusi yang ditemukan.");
            }
        }
    }

    private void saveSolutionToFile() {
        String fileName = JOptionPane.showInputDialog(this, "Masukkan nama file:", "Simpan File", JOptionPane.PLAIN_MESSAGE);
        fileName = fileName.trim();

        if (!fileName.trim().isEmpty() && fileName.endsWith(".txt")) {        
            fileReader.solutionToFile(solvedBoard.getBoard(), fileName);
            statusLabel.setText("Solusi disimpan ke " + fileName);
            JOptionPane.showMessageDialog(this, "Solusi berhasil disimpan sebagai: " + fileName);
        } else {
            statusLabel.setText("Penyimpanan dibatalkan.");
        }
    
    }

    private void displayResult(Board solvedBoard, long duration, int casesCount) {
        StyledDocument doc = resultPane.getStyledDocument();
        SimpleAttributeSet[] colors = new SimpleAttributeSet[26]; 
        Color[] colorValues = {
            Color.YELLOW, Color.GREEN, Color.BLUE, Color.ORANGE, Color.MAGENTA, Color.CYAN, Color.PINK,
            Color.RED, Color.GRAY, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.BLACK, new Color(145, 99, 99),
            new Color(128, 0, 0), new Color(0, 128, 0), new Color(0, 0, 128), new Color(128, 128, 0),
            new Color(128, 0, 128), new Color(0, 128, 128), new Color(255, 165, 0),
            new Color(75, 0, 130), new Color(139, 69, 19), new Color(255, 20, 147),
            new Color(0, 255, 127), new Color(70, 130, 180), new Color(255, 215, 0)
        };

        // Buat set warna untuk tiap huruf
        for (int i = 0; i < colors.length; i++) {
            colors[i] = new SimpleAttributeSet();
            StyleConstants.setForeground(colors[i], colorValues[i]);
            StyleConstants.setBold(colors[i], true);
        }

        try {
            doc.remove(0, doc.getLength());
            char[][] board = solvedBoard.getBoard();
            for (char[] row : board) {
                for (char unit : row) {
                    if (unit == '.') {
                        doc.insertString(doc.getLength(), ". ", null);
                    } else {
                        int colorIndex = (unit - 'A') % colors.length;
                        doc.insertString(doc.getLength(), unit + " ", colors[colorIndex]);
                    }
                }
                doc.insertString(doc.getLength(), "\n", null);
            }

            doc.insertString(doc.getLength(), "\nWaktu eksekusi: " + duration + " ms\n", null);
            doc.insertString(doc.getLength(), "\nJumlah kasus yang diuji: " + casesCount, null);

        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

}
