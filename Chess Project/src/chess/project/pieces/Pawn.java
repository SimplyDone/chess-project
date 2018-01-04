package chess.project.pieces;

import chess.project.Board;
import chess.project.Position;
import java.util.LinkedList;
import java.util.List;

/** This represents a pawn.
 *
 * @author Alex Zurad
 */
public class Pawn extends Piece {
    
    private int enPassantTurn = -1;

    public Pawn(boolean col, Position pos) {
        super(col, pos);
        
    }
    
    @Override
    public List getValidMoves(Board board) {
        List<Position> validMoves = new LinkedList<>();

        if(this.getColour() ){//true = white
            
        }
        //TODO add actual code
        
        
        return validMoves;
    }
    
    public String toString(){
        return "P";
    }
    
    public void setEnPassant(int turn){
        enPassantTurn = turn;
    }

}
