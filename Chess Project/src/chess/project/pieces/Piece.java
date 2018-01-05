
package chess.project.pieces;
/*
TODO
- 
- 
*/
import chess.project.Board;
import chess.project.Move;
import chess.project.Position;
import java.io.Serializable;
import java.util.List;

/** Represents a single piece.
 *
 * @author Alex Zurad
 */
public abstract class Piece implements Serializable{
    
    private final boolean colour; // false-black, true-white
    protected Position position;
       
    public Piece(boolean col, Position pos){
        colour = col;
        position = pos;
    }
    
    public void move(Position p){
        position = p;
   
    }
    
    public abstract List<Move> getValidMoves(Board board);
    
    /** Returns the colour of the piece;
     * 
     * @return the colour of the piece
     */
    public String getColourString(){
        return colour ? "white" : "black";
    }
    
    public boolean getColour(){
        return colour;
    }
    
    public Position getPosition(){
        return position;
    }
    
}
