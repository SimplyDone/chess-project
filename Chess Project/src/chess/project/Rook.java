package chess.project;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alex Zurad
 */
public class Rook extends Piece{

    public Rook(boolean col, Position pos) {
        super(col, pos);
    }

    @Override
    protected List getValidMoves(Board board) {
        List<Position> validMoves = new LinkedList<>();

        //TODO add actual code
        
        
        return validMoves;
    }
    
}
