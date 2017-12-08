package chess.project;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alex Zurad
 */
public class Queen extends Piece{

    public Queen(boolean col, Position pos) {
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
        return "Q";
    }
    
}
