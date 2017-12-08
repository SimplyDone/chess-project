
package chess.project;

import java.util.List;

/** Represents a single piece.
 *
 * @author Alex Zurad
 */
public abstract class Piece {
    

    private final boolean colour; // 0-black, 1-white
    private Position pos;
       
    public Piece(boolean col, Position pos){
        colour = col;
         
    }
    
    protected abstract List getValidMoves(Board board);
    
}
