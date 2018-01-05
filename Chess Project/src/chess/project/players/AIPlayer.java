package chess.project.players;

import chess.project.Board;
import chess.project.Move;
import chess.project.pieces.*;
import java.util.*;


/**
 *
 * @author Alex Zurad
 */
public class AIPlayer extends Player{
    
    private Piece[][] testBoard;
    private final int difficulty;
    private final List<Move> allValidMoves;

    public AIPlayer(boolean isWhite, Board board, int difficulty) {
        super(isWhite, board);
        this.difficulty = difficulty;
        this.allValidMoves = new LinkedList<>();
    }

    @Override
    public Move getMove() {
        
        System.out.println(COLOUR);
        
        for(int i = 0; i< 8; i++){
            for(int j = 0; j<8; j++){
                if(board.getBoard()[i][j] != null && board.getBoard()[i][j].getColour() == colour){
                    allValidMoves.addAll(board.getBoard()[i][j].getValidMoves());
                }
            }
        }
        
        Random rand = new Random();
        Move m = allValidMoves.get(rand.nextInt(allValidMoves.size()));
        System.out.println(m.getOldPosition() + " -> " + m.getNewPosition());
        
        return m;
    }
    
    private void buildMoveTree(int depth){
        
        
        
    }
    
    public void evaluateBoard(Piece[][] board){
        int score = 0;
        
        
    }

    
}
