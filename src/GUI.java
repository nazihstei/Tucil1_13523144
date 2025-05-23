import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import model.*;
import solver.*;
import utility.*;

public class GUI {
    private JFrame frame;
    private JPanel boardPanel;
    private JTable infoTable;
    private JButton inputFileButton, runButton;
    private long executionTime;
    private long trialCount;
    private String additionalText;
    private int boardRow, boardCol, numBlock;
    private Board.typeBoard boardType;
    private File selectedFile;
    private Board board;
    private List<List<Block>> blockList;
    private boolean heuristic;

    public GUI(boolean heuristic) {
        this.heuristic = heuristic;
        frame = new JFrame("IQ Puzzler Pro");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Panel kiri (Tombol & Tabel)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1, 2, 30, 30));
        inputFileButton = new JButton("Input File");
        runButton = new JButton("Run");
        runButton.setEnabled(false); // Run baru aktif setelah file dipilih
        leftPanel.add(inputFileButton);
        leftPanel.add(runButton);

        // Panel kanan (Board)
        JPanel rightPanel = new JPanel(new BorderLayout());
        boardPanel = new JPanel();
        infoTable = new JTable(3, 2);
        infoTable.getColumnModel().getColumn(0).setHeaderValue("Keterangan");
        infoTable.getColumnModel().getColumn(1).setHeaderValue("Nilai");
        infoTable.getTableHeader().repaint(); // Refresh header
        infoTable.setValueAt("Waktu Eksekusi:", 0, 0);
        infoTable.setValueAt("Jumlah Percobaan:", 1, 0);
        infoTable.setValueAt("Info Tambahan:", 2, 0);

        rightPanel.add(boardPanel, BorderLayout.CENTER);
        rightPanel.add(new JScrollPane(infoTable), BorderLayout.WEST);

        // Menambahkan ke frame
        frame.add(rightPanel, BorderLayout.CENTER);
        frame.add(leftPanel, BorderLayout.NORTH);

        // Event Listener
        inputFileButton.addActionListener(e -> loadFile());
        runButton.addActionListener(e -> onRun());

        frame.setVisible(true);
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(frame, "File Loaded: " + selectedFile.getName());

            // Load from file
            List<Object> inputData = FileHandler.inputFile(selectedFile);
            
            onFileLoaded(inputData);
        }
    }
    
    private void onFileLoaded(List<Object> data) {
        this.boardRow = (int) data.get(0);
        this.boardCol = (int) data.get(1);
        this.numBlock = (int) data.get(2);
        this.boardType = (Board.typeBoard) data.get(3);
        List<List<String>> blockStringList = (List<List<String>>) data.getLast();

        // Generate Board
        this.board = new Board(boardRow, boardCol, boardType);
        
        // Generate Blocks
        this.blockList = new ArrayList<>();
        for (List<String> blockString : blockStringList) {
            char blockID = FileHandler.getCharOfBlock(blockString.get(0));
            Block blok = new Block(blockID, blockString);
            this.blockList.add(blok.getAllShape());
        }

        // Mengaktifkan tombol run setelah file dipilih
        runButton.setEnabled(true);
    }
    
    private void onRun() {
        Solve solver = new Solve(this.heuristic);
        long totalCombination = Progress.countTotalCombination((this.boardRow*this.boardCol), 8, 0, this.numBlock-1);
        Instant timeStart = Instant.now();
        boolean puzzleSolved = solver.checkDefaultSolve(this.board, this.blockList, 0, this.numBlock-1, true, totalCombination);
        Instant timeEnd = Instant.now();
        long timeExecuted = Duration.between(timeStart, timeEnd).toMillis();
        
        String resultText;
        if (puzzleSolved) {
            if (timeExecuted < 100000) {
                resultText = "Your Puzzle is Too Easy!";
            } else if (timeExecuted < 10000000) {
                resultText = "Your Puzzle is Quite Difficult...";
            } else {
                resultText = "Your Puzzle is EXTREMELY HARD!!!";
            }    
        } else {
            resultText = "Your Puzzle is SUCKS! No one can solve this.";
        }
        
        setExecutionResult(timeExecuted, solver.tryCount, resultText);
        
        int saveOption = JOptionPane.showConfirmDialog(frame, "Simpan hasil sebagai gambar?", "Simpan", JOptionPane.YES_NO_OPTION);
        if (saveOption == JOptionPane.YES_OPTION) {
            saveImage();
        }
        runButton.setEnabled(false);
    }

    public void setExecutionResult(long timeExecuted, long trialCount, String customizeString) {
        this.executionTime = timeExecuted;
        this.trialCount = trialCount;
        this.additionalText = customizeString;

        updateBoardPanel();
        updateTable();
    }

    private void updateBoardPanel() {
        boardPanel.removeAll();
        boardPanel.setLayout(new GridLayout(this.boardRow, this.boardCol));

        Map<Character, Color> colorMap = new HashMap<>();
        for (char[] row : this.board.map) {
            for (char c : row) {
                if (c != ' ' && !colorMap.containsKey(c)) {
                    colorMap.put(c, new Color((int) (Math.random() * 0xFFFFFF)));
                }
            }
        }

        for (char[] row : this.board.map) {
            for (char c : row) {
                JLabel label = new JLabel(String.valueOf(c), SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setBackground(c == ' ' ? Color.WHITE : colorMap.get(c));
                boardPanel.add(label);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void updateTable() {
        infoTable.setValueAt(executionTime + " ms", 0, 1);
        infoTable.setValueAt(trialCount, 1, 1);
        infoTable.setValueAt(additionalText, 2, 1);
    }

    private void saveImage() {
        BufferedImage image = new BufferedImage(boardPanel.getWidth(), boardPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        boardPanel.paint(g2d);
        g2d.dispose();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
        int option = fileChooser.showSaveDialog(frame);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                ImageIO.write(image, "png", new File(file.getAbsolutePath() + ".png"));
                JOptionPane.showMessageDialog(frame, "Gambar disimpan: " + file.getAbsolutePath() + ".png");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Gagal menyimpan gambar!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void runGUI(boolean heuristic) {
        SwingUtilities.invokeLater(() -> new GUI(heuristic));
    }
}
