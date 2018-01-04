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

/**
 *
 * @author Alex Zurad
 */
public class Bishop extends Piece{

    public Bishop(boolean col, Position pos) {
        super(col, pos);
    }

    @Override
    public List<Move> getValidMoves(Board board) {
        List<Move> validMoves = new LinkedList<>();
        
        int i,j;
        int iNext,jNext;
        
        for(int x = 0; x<=1;x++){
            iNext = direction(x);
            
            for(int y = 0; y<=1;y++){
                jNext = direction(y);
                
                i = this.position.getX();
                j = this.position.getY();
                
                i += iNext;
                j += jNext;
                
                
                while((i >= 0 && i <= 7)&&(j >= 0 && j <= 7)&&(null == board.getBoard()[i][j])){
                    Move m = new Move(position, new Position(i,j));
                    
                    if(true /* add check if move puts you in check */){
                        validMoves.add(m);
                    }
                    
                    i += iNext;
                    j += jNext;
                }   
                
                if((i >= 0 && i <= 7)&&(j >= 0 && j <= 7) && board.getBoard()[i][j].getColour() != this.getColour()){
                    Move m = new Move(position, new Position(i,j));
                    
                    if(true /* add check if move puts you in check */){
                        validMoves.add(m);
                    }
                }
            }
        }
        return validMoves;
    }
    
    @Override
    public String toString(){
        return "B";
    }
    
    private int direction(int d){
        if(d == 0){
            return -1;
        }
        return d;
    }
}
