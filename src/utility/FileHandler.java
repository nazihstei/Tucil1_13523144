package utility;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    
    // Input File
    public static List<Object> inputFile(String filepath) {
        List<Object> output = new ArrayList<>();
        
        try {
            int row=0, col=0, blockCount=0;
            Board.typeBoard tipe=Board.typeBoard.DEFAULT;
            List<List<String>> blockList = new ArrayList<>();
            
            char prevChar = ' ';
            int lineCount = 1;
            int blockListIndex = -1;

            File file = new File(filepath);
            Scanner stream = new Scanner(file);
            while (stream.hasNextLine()) {
                String line = stream.nextLine();
                switch (lineCount) {
                    case 1 -> {
                        List<String> line1 = Arrays.asList(line.split(" "));
                        row = Integer.parseInt(line1.get(0));
                        col = Integer.parseInt(line1.get(1));
                        blockCount = Integer.parseInt(line1.get(2));
                    }
                    case 2 -> {
                        tipe = Board.typeBoard.valueOf(line);
                    }
                    default -> {
                        char currChar = getCharOfBlock(line);
                        if (currChar != prevChar) {
                            List<String> newBlock = new ArrayList<>(); newBlock.add(line);
                            blockList.add(newBlock);
                            blockListIndex++;
                            prevChar = currChar;
                        } else {
                            blockList.get(blockListIndex).add(line);
                        }
                    }
                }
                lineCount++;
            }
            stream.close();
            output.add(row); output.add(col); output.add(blockCount); 
            output.add(tipe);
            output.add(blockList);
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        return output;
    }
    public static List<Object> inputFile(File file) {
        List<Object> output = new ArrayList<>();
        
        try {
            int row=0, col=0, blockCount=0;
            Board.typeBoard tipe=Board.typeBoard.DEFAULT;
            List<List<String>> blockList = new ArrayList<>();
            
            char prevChar = ' ';
            int lineCount = 1;
            int blockListIndex = -1;

            Scanner stream = new Scanner(file);
            while (stream.hasNextLine()) {
                String line = stream.nextLine();
                switch (lineCount) {
                    case 1 -> {
                        List<String> line1 = Arrays.asList(line.split(" "));
                        row = Integer.parseInt(line1.get(0));
                        col = Integer.parseInt(line1.get(1));
                        blockCount = Integer.parseInt(line1.get(2));
                    }
                    case 2 -> {
                        tipe = Board.typeBoard.valueOf(line);
                    }
                    default -> {
                        char currChar = getCharOfBlock(line);
                        if (currChar != prevChar) {
                            List<String> newBlock = new ArrayList<>(); newBlock.add(line);
                            blockList.add(newBlock);
                            blockListIndex++;
                            prevChar = currChar;
                        } else {
                            blockList.get(blockListIndex).add(line);
                        }
                    }
                }
                lineCount++;
            }
            stream.close();
            output.add(row); output.add(col); output.add(blockCount); 
            output.add(tipe);
            output.add(blockList);
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        return output;
    }
    // Helper untuk mencari char of Block dari stream
    public static char getCharOfBlock(String str) {
        for (char c : str.toCharArray()) {
            if (c != ' ') {
                return c;
            }
        }
        return ' ';
    }
    
    // Output File
    public class OutputRedirector {
        private static PrintStream originalOut = System.out;
        private static PrintStream fileOut;

        public static void setOutputToFile(String filepath) throws FileNotFoundException {
            // File output stream
            FileOutputStream fos = new FileOutputStream(filepath);
            fileOut = new PrintStream(new TeeOutputStream(originalOut, fos), false);

            // Set System.out ke output baru (konsol + file)
            System.setOut(fileOut);
        }

        public static void setOutputToOriginal() {
            System.setOut(originalOut);
            System.out.flush();
            if (fileOut != null) {
                try {
                    fileOut.flush();
                    fileOut.close();
                } catch (Exception e) {
                    System.err.println("Gagal menutup output file: " + e.getMessage());
                }
                fileOut = null;
            }
        }
        
        // Helper class untuk duplikasi output
        private static class TeeOutputStream extends OutputStream {
            private final OutputStream out1, out2;
            
            public TeeOutputStream(OutputStream out1, OutputStream out2) {
                this.out1 = out1;
                this.out2 = out2;
            }
            @Override
            public void write(int b) throws IOException {
                out1.write(b);
                out2.write(b);
            }
            @Override
            public void flush() throws IOException {
                out1.flush();
                out2.flush();
            }
            @Override
            public void close() throws IOException {
                out1.close();
                out2.close();
            }
        }
    }
    
}
