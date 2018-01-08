package chess.project.pieces;
/*
 TODO
 - OPTIMIZE
 - combine the horizontal and vertical search
 */

import chess.project.movement.*;
import chess.project.*;

/**
 *
 * @author Alex Zurad
 */
public class Rook extends Piece {

    private boolean castlable = true;
    
    public Rook(ChessColour col, Position pos) {
        super(col, pos);
    }

    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

        int i, j;

        for (int next = -1; next <= 1; next+=2) {
            i = this.position.getX();
            j = this.position.getY();
            i += next;
            while (inBounds(i) && (null == board.getBoard()[i][j])) {
                addMove(board, i, j);

                i += next;
            }
            
            if (inBounds(i) && board.getBoard()[i][j].getColour() != colour) {
                addMove(board, i, j);
            }
        }
        //////////////////////////same as above fory axis///////////////////////
        for (int next = -1; next <= 1; next+=2) {
            i = this.position.getX();
            j = this.position.getY();
            j += next;
            while (inBounds(j) && null == board.getBoard()[i][j]) {
                addMove(board, i, j);

                j += next;
            }
            if (inBounds(j) && board.getBoard()[i][j].getColour() != colour) {
                addMove(board, i, j);
            }
        }
        
    }

    /** This method returns True if the Rook can be used for castling.
     * 
     * @return True if this Rook is valid for castling.
     */
    public boolean isCastlable() {
        return castlable;
    }
    
    /** This method flags the rook as unable to be used for castling.
     * 
     */
    public void flagCastle() {
        castlable = false;
    }
 
    @Override
    public String toString() {
        return "R";
    }
    
}
