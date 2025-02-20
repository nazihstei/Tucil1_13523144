package utility;

import model.*;
import java.io.File;
import java.io.FileNotFoundException;
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
                        Board.typeBoard.valueOf(line);
                    }
                    default -> {
                        char currChar = getCharOfBlock(line);
                        if (currChar != prevChar) {
                            blockListIndex++;
                            prevChar = currChar;
                        }
                        blockList.get(blockListIndex).add(line);
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


    // Helper
    public static char getCharOfBlock(String str) {
        for (char c : str.toCharArray()) {
            if (c != ' ') {
                return c;
            }
        }
        return ' ';
    }
    
}
