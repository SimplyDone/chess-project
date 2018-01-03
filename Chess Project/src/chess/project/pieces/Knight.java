package chess.project.pieces;

import chess.project.Board;
import chess.project.Move;
import chess.project.Position;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alex Zurad
 */
public class Knight extends Piece{

    public Knight(boolean col, Position pos) {
        super(col, pos);
    }

    @Override
    public List getValidMoves(Board board) {
        List<Move> validMoves = new LinkedList<>();
        
        int rowDelta;
        int colDelta;
        
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                
                rowDelta = Math.abs(position.getX() - i);
                colDelta = Math.abs(position.getY() - j);
                
                if((rowDelta == 1 && colDelta == 2) || (rowDelta == 2 && colDelta == 1)){
                    
                    Piece p = board.getBoard()[i][j];
                    if(p == null || p.getColour() != this.getColour() ){
                        
                        Move m = new Move(position, new Position(i,j));
                        if(true /** add check if move puts you in check */){
                            validMoves.add(m);
                        }
                    }
                    
                    
                }  
            }
        }
        
        return validMoves;
    }
    
    @Override
    public String toString(){
        return "k";
    }
    
}
