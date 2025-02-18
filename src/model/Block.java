package model;

import java.util.List;
import java.util.ArrayList;

public class Block {
    // Attrubutes
    public char id;
    public boolean[][] shape;

    // Create Block
    public Block(char id, boolean[][] shape) {
        this.id = id;
        this.shape = shape;
    }
    public Block(char id, List<String> stringShape) {
        this.id = id;
        this.shape = createShape(id, stringShape);
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

    // Get All Shape Possibility
    public List<Block> getAllShape() {
        List<Block> shapeList = new ArrayList<>();
        Block normalBlock = new Block(this.id, this.shape);
        Block flippedBlock = this.flip();
        for (int i=0; i<4; i++) {
            shapeList.add(normalBlock);
            shapeList.add(flippedBlock);
            normalBlock = normalBlock.rotate();
            flippedBlock = flippedBlock.rotate();
        }
        return shapeList;
    }
    // Rotation 90 deg counter-clockwise
    public Block rotate() {
        int row = this.shape.length;
        int col = this.shape[0].length;
        boolean[][] newShape = new boolean[col][row];
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                newShape[col-j-1][row-i-1] = this.shape[i][j];
            }
        }
        return new Block(this.id, newShape);
    }
    // Flip horizontally
    public Block flip() {
        int row = this.shape.length;
        int col = this.shape[0].length;
        boolean[][] newShape = new boolean[row][col];
        for (int i=0; i<row; i++) {
            for (int j=0; j<col; j++) {
                newShape[i][col-j-1] = this.shape[i][j];
            }
        }
        return new Block(this.id, newShape);
    }
}
