
import model.*;
import solver.*;
import utility.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.time.Instant;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.Duration;

public class CLI {

    public static void runCLI(boolean heuristic) {
        
        // IO utility
        Scanner scanner = new Scanner(System.in);

        // Interface
        CustomString.clearTerminal();
        System.out.println("\n[=======[ Welcome To IQ Puzzler ]=======]");
        System.out.println("");
        System.out.println("[Program] Masukkan file txt");
        System.out.print("          >> "); String filepath = scanner.nextLine();
        System.out.println("[Program] Ingin menyimpan solusi Puzzle? (y/n)");
        System.out.print("          >> "); char saveSolution = scanner.nextLine().charAt(0);
        
        // Load from file
        List<Object> inputData = FileHandler.inputFile(filepath);
        int boardRow = (int) inputData.get(0);
        int boardCol = (int) inputData.get(1);
        int numBlock = (int) inputData.get(2);
        Board.typeBoard boardType = (Board.typeBoard) inputData.get(3);
        List<List<String>> blockStringList = (List<List<String>>) inputData.getLast();
        long totalCombination = Progress.countTotalCombination((boardRow*boardCol), 8, 0, numBlock-1);
        
        // Generate Board
        Board board = new Board(boardRow, boardCol, boardType);
        
        // Generate Blocks
        List<List<Block>> blockList = new ArrayList<>();
        for (List<String> blockString : blockStringList) {
            char blockID = FileHandler.getCharOfBlock(blockString.get(0));
            Block blok = new Block(blockID, blockString);
            blockList.add(blok.getAllShape());
        }
        
        // Interface
        System.out.println("");
        System.out.println("[Program] Puzzle successfully generated.");
        System.out.println("[Program] Starting to find solution ...");
        
        // Generate Solution
        Solve solver = new Solve(heuristic);
        Instant timeStart = Instant.now();
        boolean puzzleSolved = solver.checkDefaultSolve(board, blockList, 0, numBlock-1, true, totalCombination);
        Instant timeEnd = Instant.now();
        long timeExecuted = Duration.between(timeStart, timeEnd).toMillis();
        CustomString.clearTerminal();
        
        if (saveSolution=='y' || saveSolution=='Y') {
            System.out.println("");
            System.out.println("[Program] Masukkan nama file penyimpanan"); 
            System.out.print("          >> "); 
            String saveFile = scanner.nextLine();
            try {
                FileHandler.OutputRedirector.setOutputToFile(saveFile);
            } catch (FileNotFoundException e) {
                System.out.println("File tidak ditemukan");
                e.getStackTrace();
            }
        }
        
        // Output
        if (puzzleSolved) {
            if (solver.tryCount < 100000) {
                System.out.println("");
                System.out.printf("   ///////\n");
                System.out.printf("  [[^ w ^]]   I can solve this in %d ms\n", timeExecuted);
                System.out.printf("  /ll---ll\\   with %d attempts of combination.\n", solver.tryCount);
                System.out.printf("  *il###li*\n");
                System.out.println("[======================================================]");
                System.out.println("[=======[       Your Puzzle is Too Easy!       ]=======]");
                System.out.println("[======================================================]");
            } else if (solver.tryCount < 1000000000) {
                System.out.println("");
                System.out.printf("   ///////\n");
                System.out.printf("  [[. _ .]]   I can solve this in %d ms\n", timeExecuted);
                System.out.printf("  /ll---ll\\   with %d attempts of combination.\n", solver.tryCount);
                System.out.printf("  *il###li*\n");
                System.out.println("[======================================================]");
                System.out.println("[=======[   Your Puzzle is Quite Difficult...  ]=======]");
                System.out.println("[======================================================]");
            } else {
                System.out.println("");
                System.out.printf("   ///////\n");
                System.out.printf("  [[> x <]]   I can solve this in %d ms\n", timeExecuted);
                System.out.printf("  /ll---ll\\   with %d attempts of combination.\n", solver.tryCount);
                System.out.printf("  *il###li*\n");
                System.out.println("[======================================================]");
                System.out.println("[=======[    Your Puzzle is EXTREMELY HARD!!!  ]=======]");
                System.out.println("[======================================================]");
            }
            System.out.println("[=======[ This is the Solution of your Puzzle: ]=======]");
            System.out.println("[======================================================]");
            System.out.println("");
            
            // Solution print here
            board.printBoard();
            
        } else {
            System.out.println("");
            System.out.printf("   ///////\n");
            System.out.printf("  [[x , x]]   I have try this in %d ms\n", timeExecuted);
            System.out.printf("  /ll---ll\\   with %d attempts of combination.\n", solver.tryCount);
            System.out.printf("  *il###li*\n");
            System.out.println("[======================================================]");
            System.out.println("[=======[ Have you ever made a Puzzle before?? ]=======]");
            System.out.println("[=======[         Your puzzle is sucks!        ]=======]");
            System.out.println("[=======[      Even Albert Einstein can't      ]=======]");
            System.out.println("[=======[          solve this puzzle.          ]=======]");
            System.out.println("[======================================================]");
            System.out.println("");
        }
        
        // End Program
        FileHandler.OutputRedirector.setOutputToOriginal();
        if (saveSolution!='Y' || saveSolution!='y') {
            System.exit(0);
        }
        scanner.nextLine();
    }
    
}