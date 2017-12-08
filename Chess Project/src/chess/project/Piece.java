
package chess.project;

import java.util.List;

/** Represents a single piece.
 *
 * @author Alex Zurad
 */
public abstract class Piece {
    

    private final boolean colour; // false-black, true-white
    private Position pos;
       
    public Piece(boolean col, Position pos){
        colour = col;  
    }
    
    protected abstract List getValidMoves(Board board);
    
    /** Returns the colour of the piece;
     * 
     * @return the colour of the piece
     */
    public String getColour(){
        return colour ? "white" : "black";
    }
    
}
