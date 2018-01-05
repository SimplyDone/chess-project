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
    
    private final int difficulty;

    public AIPlayer(boolean isWhite, Board board, int difficulty) {
        super(isWhite, board);
        this.difficulty = difficulty;
    }

    @Override
    public Move getMove() {
        
        List<Move> moves = new ArrayList(); 
        System.out.println(COLOUR);
        
        for(int i = 0; i< 8; i++){
            for(int j = 0; j<8; j++){
                if(board.getBoard()[i][j] != null && board.getBoard()[i][j].getColour() == colour){
                    moves.addAll(board.getBoard()[i][j].getValidMoves(board));
                }
            }
        }
        
        Random rand = new Random();
        Move m = moves.get(rand.nextInt(moves.size()));
        System.out.println(m.getOldPosition() + " -> " + m.getNewPosition());
        
        return m;
    }
    


    
}
