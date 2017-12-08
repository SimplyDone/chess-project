package chess.project;

import java.util.LinkedList;
import java.util.List;

/** This represents a pawn.
 *
 * @author Alex Zurad
 */
public class Pawn extends Piece {

    public Pawn(boolean col, Position pos) {
        super(col, pos);
    }
    
    @Override
    protected List getValidMoves(Board board) {
        List<Position> validMoves = new LinkedList<>();

        //TODO add actual code
        
        
        return validMoves;
    }

}
