package model;

public class Board {
    int row;
    int col;
    typeBoard tipe;
    char[][] map;

    public enum typeBoard {
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
        for (int i=idRow; i<row; i++) {
            for (int j=idCol; j<col; j++) {
                if (blok.shape[i][j] && (this.map[i+idRow][j+idCol] != ' ')) {
                    return false;
                }
            }
        }
        for (int i=idRow; i<row; i++) {
            for (int j=idCol; j<col; j++) {
                this.map[i+idRow][j+idCol] = blok.id;
            }
        }
        return true;   
    }
}
