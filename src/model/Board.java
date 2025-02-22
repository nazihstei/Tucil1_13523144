package model;

import java.util.ArrayList;
import java.util.List;

public class Board {
    
    // Attributes
    public int row;
    public int col;
    public typeBoard tipe;
    public char[][] map;

    public static enum typeBoard {
        DEFAULT, 
        CUSTOM,
        PYRAMID
    }

    // Create Board
    public Board(int row, int col, typeBoard tipe) {
        this.row = row;
        this.col = col;
        this.tipe = tipe;
        this.map = createMap(row, col);
    }
    public static char[][] createMap(int row, int col) {
        char[][] map = new char[row][col];
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                map[i][j] = ' ';
            }
        }
        return map;
    }

    // Put Block
    public boolean putBlock(Block blok, int idRow, int idCol) {
        int row = blok.shape.length;
        int col = blok.shape[0].length;
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                if (((i+idRow) >= this.row) || ((j+idCol) >= this.col)) {
                    return false;
                }
                if (blok.shape[i][j] && (this.map[i+idRow][j+idCol] != ' ')) {
                    return false;
                }
            }
        }
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                if (blok.shape[i][j]) {
                    this.map[i+idRow][j+idCol] = blok.id;
                }
            }
        }
        return true;   
    }
    
    // Pull out Block
    public boolean pullOutBlock(Block blok, int idRow, int idCol) {
        int row = blok.shape.length;
        int col = blok.shape[0].length;
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                if (blok.shape[i][j]) {
                    this.map[i+idRow][j+idCol] = ' ';
                }
            }
        }
        return true;
    }

    // Check Board
    public boolean isComplete() {
        for (char[] rowMap : this.map) {
            for (char cc : rowMap) {
                if (cc == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    // Print Board
    public void printBoard() {
        int idxForColor = -1;
            List<Character> charMet = new ArrayList<>();
            for (int i=0; i<this.row; i++) {
                for (int j=0; j<this.col; j++) {
                    if (this.map[i][j] == ' ') {
                        System.out.print(". ");
                    } else {
                        System.out.print(this.map[i][j] + " ");
                    }
                    // Color Print
                    // if (charMet.contains(board.map[i][j])) {
                    //     idxForColor = charMet.indexOf(board.map[i][j]);
                    // } else {
                        //     charMet.add(board.map[i][j]);
                        //     idxForColor = charMet.size()-1;
                        // }
                        // ColorText.colorPrint(String.format("%c", board.map[i][j]), idxForColor);
                    }
                    System.out.print("\n");
                }
                System.out.print("\n");
    }
}
