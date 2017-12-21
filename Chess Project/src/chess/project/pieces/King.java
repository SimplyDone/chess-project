package chess.project.pieces;

import chess.project.Board;
import chess.project.Position;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alex Zurad
 */
public class King extends Piece{

    public King(boolean col, Position pos) {
        super(col, pos);
    }


    @Override
    protected List getValidMoves(Board board) {
        List<Position> validMoves = new LinkedList<>();

        //TODO add actual code
        
        
        return validMoves;
    }
    
    @Override
    public String toString(){
        return "K";
    }
    
}
