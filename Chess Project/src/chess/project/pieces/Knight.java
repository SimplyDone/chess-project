package chess.project.pieces;

import chess.project.Board;
import chess.project.Move;
import chess.project.Position;

/** This class represents a Knight. Its movement consists of a L shaped jump
 * that can go over pieces of either colour. 
 *
 * @author Alex Zurad
 */
public class Knight extends Piece{
    
    public Knight(boolean col, Position pos) {
        super(col, pos);
    }
    
    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();
        
        int rowDelta;
        int colDelta;
        
        for(int i = 0; i < 8; i++){/* optimize later (haardcode?) */
            for(int j = 0; j < 8; j++){
                
                rowDelta = Math.abs(position.getX() - i);
                colDelta = Math.abs(position.getY() - j);
                
                if((rowDelta == 1 && colDelta == 2) || (rowDelta == 2 && colDelta == 1)){
                    
                    Piece p = board.getBoard()[i][j];
                    if(p == null || p.getColour() != this.colour ){
                        
                        Move m = new Move(position, new Position(i,j));
                        if(!board.isChecked(m, colour)){
                            validMoves.add(m);
                        }
                    }
                    
                    
                }  
            }
        }
    }
    
    @Override
    public String toString(){
        return "k";
    }
    
}
