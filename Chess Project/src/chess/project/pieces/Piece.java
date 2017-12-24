
package chess.project.pieces;

import chess.project.Board;
import chess.project.Position;
import java.util.List;

/** Represents a single piece.
 *
 * @author Alex Zurad
 */
public abstract class Piece {
    

    private final boolean colour; // false-black, true-white
    private Position position;
       
    public Piece(boolean col, Position pos){
        colour = col;
        this.position = pos;
    }
    
    public void move(Position p){
        position = p;
   
    }
    
    public abstract List getValidMoves(Board board);
    
    /** Returns the colour of the piece;
     * 
     * @return the colour of the piece
     */
    public String getColourString(){
        return colour ? "white" : "black";
    }
    
    public boolean isWhite(){
        return colour;
    }
    
}
