package solver;

import model.*;
import java.util.List;

public class Solve {
    public int tryCount;

    // Constructor
    public Solve() {
        this.tryCount = 0;
    }
    
    // DEFAULT solver
    public boolean checkDefaultSolve(Board board, List<List<Block>> blockList, int idx, int stopIdx) {
        boolean isSuccess = false;
        List<Block> blok = blockList.get(idx);
        
        for (int i=0; i<board.row; i++) {
            for (int j=0; j<board.col; j++) {
                
                for (Block shape : blok) {
                    if (idx == stopIdx) {
                        this.tryCount++;
                    }
                    
                    boolean isFit = board.putBlock(shape, i, j);
                    if (isFit) {
                        if (idx == stopIdx) {
                            return board.isComplete();
                        } else {
                            isSuccess = checkDefaultSolve(board, blockList, idx+1, stopIdx);   
                        }
                        
                        if (isSuccess) {
                            return isSuccess;
                        } else {
                            board.pullOutBlock(shape, i, j);
                        }
                    }

                }
            }
        }
        return isSuccess;
    }

}
