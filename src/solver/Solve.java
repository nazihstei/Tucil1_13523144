package solver;

import model.*;
import utility.Progress;

import java.util.List;

public class Solve {
    public boolean heuristic;
    public long tryCount;
    private long progress;

    // Constructor
    public Solve(boolean heuristic) {
        this.heuristic = heuristic;
        this.tryCount = 0;
        this.progress = 0;
    }
    
    // DEFAULT solver
    public boolean checkDefaultSolve(Board board, List<List<Block>> blockList, int idx, int stopIdx, boolean debugProgress, long totalComb) {
        
        if (this.heuristic) {
            int total = 0;
            for (List<Block> block : blockList) {
                total += block.get(0).tileCount;
            }
            if (!(board.row*board.col == total)) {
                return false;
            }
        }
        
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
                        this.progress++;
                        if (idx == stopIdx) {
                            boolean isFull = board.isComplete();
                            if (isFull) {
                                return true;
                            } else {
                                board.pullOutBlock(shape, i, j);
                                return false;
                            }
                        } else {
                            isSuccess = checkDefaultSolve(board, blockList, idx+1, stopIdx, debugProgress, totalComb);   
                        }
                        
                        if (isSuccess) {
                            return isSuccess;
                        } else {
                            board.pullOutBlock(shape, i, j);
                        }
                        
                    } else {
                        this.progress += Progress.countTotalCombination((board.row*board.col), 8, idx, stopIdx-1);
                    }

                    if (debugProgress) {
                        Progress.displayProgress(this.progress, totalComb);
                    }
                    
                }
            }
        }
        return isSuccess;
    }

}
