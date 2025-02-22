
import model.*;
import solver.*;
import utility.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.time.Instant;
import java.io.FileNotFoundException;
import java.time.Duration;

public class Main {

    public static void main(String[] args) {
        
        // IO utility
        Scanner scanner = new Scanner(System.in);

        // Interface
        System.out.println("\n[=======[ Welcome To IQ Puzzler ]=======]");
        System.out.println("");
        System.out.print("[Program] Masukkan file txt >> "); String filepath = scanner.nextLine();
        System.out.println("[Program] Ingin menyimpan solusi Puzzle?");
        System.out.println("          1. Ya");
        System.out.println("          2. Tidak");
        System.out.print(">> "); int saveSolution = scanner.nextInt();
        System.out.println("[Program] Masukkan nama file penyimpanan >> "); String saveFile = scanner.nextLine();
        
        // Load from file
        List<Object> inputData = FileHandler.inputFile(filepath);
        int boardRow = (int) inputData.get(0);
        int boardCol = (int) inputData.get(1);
        int numBlock = (int) inputData.get(2);
        Board.typeBoard boardType = (Board.typeBoard) inputData.get(3);
        List<List<String>> blockStringList = (List<List<String>>) inputData.getLast();

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
        Solve solver = new Solve();
        if (saveSolution == 1) {
            try {
                FileHandler.OutputRedirector.setOutputToFile(saveFile);
            } catch (FileNotFoundException e) {
                System.out.println("File tidak ditemukan");
                e.getStackTrace();
            }
        }
        Instant timeStart = Instant.now();
        boolean puzzleSolved = solver.checkDefaultSolve(board, blockList, 0, numBlock-1);
        Instant timeEnd = Instant.now();
        long timeExecuted = Duration.between(timeStart, timeEnd).toMillis();

        // Output
        if (puzzleSolved) {
            if (solver.tryCount < 1000) {
                System.out.println("");
                System.out.println("[======================================================]");
                System.out.println("[=======[       Your Puzzle is Too Easy!       ]=======]");
                System.out.println("[======================================================]");
                System.out.println("");
                System.out.printf(" ///////\n");
                System.out.printf("[[^ w ^]]   I can solve this in %d ms\n", timeExecuted);
                System.out.printf("/ll---ll\\   with %d attempts of combination.", solver.tryCount);
                System.out.printf("*il###li*\n");
            } else if (solver.tryCount < 10000) {
                System.out.println("");
                System.out.println("[======================================================]");
                System.out.println("[=======[   Your Puzzle is Quite Difficult...  ]=======]");
                System.out.println("[======================================================]");
                System.out.println("");
                System.out.printf(" ///////\n");
                System.out.printf("[[. _ .]]   I can solve this in %d ms\n", timeExecuted);
                System.out.printf("/ll---ll\\   with %d attempts of combination.", solver.tryCount);
                System.out.printf("*il###li*\n");
            } else {
                System.out.println("");
                System.out.println("[======================================================]");
                System.out.println("[=======[    Your Puzzle is EXTREMELY HARD!!!  ]=======]");
                System.out.println("[======================================================]");
                System.out.println("");
                System.out.printf(" ///////\n");
                System.out.printf("[[> x <]]   I can solve this in %d ms\n", timeExecuted);
                System.out.printf("/ll---ll\\   with %d attempts of combination.", solver.tryCount);
                System.out.printf("*il###li*\n");
            }
            System.out.println("");
            System.out.println("[=======[ This is the Solution of your Puzzle: ]=======]");
            System.out.println("");

            // Solution print here
            int idxForColor = -1;
            List<Character> charMet = new ArrayList<>();
            for (int i=0; i<board.row; i++) {
                for (int j=0; j<board.col; j++) {
                    if (charMet.contains(board.map[i][j])) {
                        idxForColor = charMet.indexOf(board.map[i][j]);
                    } else {
                        charMet.add(board.map[i][j]);
                        idxForColor = charMet.size()-1;
                    }
                    ColorText.colorPrint(String.format("%c", board.map[i][j]), idxForColor);
                }
            }
            
        } else {
            System.out.println("");
            System.out.println("                         ///////                        ");
            System.out.println("                        [[x , x]]                       ");
            System.out.println("                        /ll---ll\\                      ");
            System.out.println("                        *il###li*                       ");
            System.out.println("[======================================================]");
            System.out.println("[=======[ Have you ever made a Puzzle before?? ]=======]");
            System.out.println("[=======[         Your puzzle is sucks!        ]=======]");
            System.out.println("[=======[      Even Albert Einstein can't      ]=======]");
            System.out.println("[=======[          solve this puzzle.          ]=======]");
            System.out.println("[======================================================]");
            System.err.println("");
        }
        
        // End Program
        FileHandler.OutputRedirector.setOutputToOriginal();
        scanner.close();
    }
    
}