
package chess.project;

import java.util.List;

/** Represents a single piece.
 *
 * @author Alex Zurad
 */
public abstract class Piece {
    
    private final boolean colour;
    private int xPos;
    private int yPos;
    
    
    public Piece(boolean col){
        colour = col;
         
    }
    
    protected abstract void setInitalPosition();
    
    protected abstract List getValidMoves(Board board);
       
    
}
