package model;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class Block {

    // Attrubutes
    public char id;
    public boolean[][] shape;
    public int tileCount;

    // Create Block
    public Block(char id, boolean[][] shape) {
        this.id = id;
        this.shape = shape;
        this.tileCount = countActiveTiles(shape);
    }
    public Block(char id, List<String> stringShape) {
        this.id = id;
        this.shape = createShape(id, stringShape);
        this.tileCount = countActiveTiles(this.shape);
    }
    public static boolean[][] createShape(char id, List<String> stringBlock) {
        int row = stringBlock.size();
        int col = 0;
        for (String line : stringBlock) {
            col = Math.max(col, line.length());
        }    
        boolean[][] matrix = new boolean[row][col];
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                matrix[i][j] = false;
            }
        }
        for (int i=0; i<stringBlock.size(); i++) {
            for (int j=0; j<stringBlock.get(i).length(); j++) {
                matrix[i][j] = (id == stringBlock.get(i).charAt(j));
            }
        }
        return matrix;
    }

    public List<Block> getAllShape() {
        Set<Block> shapeSet = new HashSet<>();
        Block normalBlock = new Block(this.id, this.shape);
        Block flippedBlock = this.flip();
        
        for (int i=0; i<4; i++) {
            shapeSet.add(new Block(normalBlock.id, normalBlock.shape));
            shapeSet.add(new Block(flippedBlock.id, flippedBlock.shape));
            normalBlock = normalBlock.rotate();
            flippedBlock = flippedBlock.rotate();
        }
        
        return new ArrayList<>(shapeSet);
    }

    // Rotation 90 deg counter-clockwise
    public Block rotate() {
        int row = this.shape.length;
        int col = this.shape[0].length;
        boolean[][] newShape = new boolean[col][row];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                newShape[j][row - i - 1] = this.shape[i][j];
            }
        }
        return new Block(this.id, newShape);
    }

    // Flip horizontally
    public Block flip() {
        int row = this.shape.length;
        int col = this.shape[0].length;
        boolean[][] newShape = new boolean[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                newShape[i][col - j - 1] = this.shape[i][j];
            }
        }
        return new Block(this.id, newShape);
    }

    // Count how many Tiles
    public static int countActiveTiles(boolean[][] shape) {
        int count = 0;
        for (int i=0; i<shape.length; i++) {
            for (int j=0; j<shape[0].length; j++) {
                if (shape[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    // Print Block
    public void printBlock() {
        for (int i=0; i<this.shape.length; i++) {
            for (int j=0; j<this.shape[0].length; j++) {
                if (this.shape[i][j]) {
                    System.out.print(this.id);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
}
