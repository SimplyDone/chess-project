package chess.project.pieces;
/*
TODO
- add a check statment for an event where a move puts a peice in check
*/
import chess.project.Board;
import chess.project.Move;
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
    public List<Move> getValidMoves(Board board) {
        List<Move> validMoves = new LinkedList<>();
        
// varifying straight forward moves 
        int jNext = 1;      // if black
        if(this.getColour()){  // if white
            jNext = -1;
        }        
        int i = this.position.getX();
        int j = this.position.getY();
        
        int dist = 1;
        if(j == 6 || j == 1){
            dist = 2;
        }
        j+=jNext;
        int moved = 1;
        while(moved<=dist && (j<=7 && j>=0) && null == board.getBoard()[i][j]){
            Move m = new Move(position, new Position(i,j));
            if(true /* add check if move puts you in check */){
                validMoves.add(m);
            }
            j+=jNext;
            moved++;
        }
        
// varifying diagonal moves
        i = this.position.getX();
        j = this.position.getY();
        for(int iNext = -1;iNext <=1;iNext+=2){
            if((i+iNext) <= 7 && (i+iNext) >= 0){
                if(null != board.getBoard()[i+iNext][j+jNext] && this.getColour() != board.getBoard()[i+iNext][j+jNext].getColour()){
                    Move m = new Move(position, new Position(i+iNext,j+jNext));
                    if(true /* add check if move puts you in check */){
                        validMoves.add(m);
                    }
                }
            }
        }
        
        
        return validMoves;
    }
    
    public String toString(){
        return "P";
    }
    
    public void setEnPassant(int turn){
        enPassantTurn = turn;
    }

}
