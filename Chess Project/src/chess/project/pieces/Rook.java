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
            while ((i >= 0 && i <= 7) && (null == board.getBoard()[i][j])) {
                Move m = new Move(position, new Position(i, j));

                if (!board.isChecked(m, colour)) {
                    validMoves.add(m);
                }

                i += next;
            }
            if ((i >= 0 && i <= 7) && board.getBoard()[i][j].getColour() != colour) {
                Move m = new Move(position, new Position(i, j));

                if (!board.isChecked(m, colour)) {
                    validMoves.add(m);
                }
            }
        }
        //////////////////////////same as above fory axis///////////////////////
        for (int next = -1; next <= 1; next+=2) {
            i = this.position.getX();
            j = this.position.getY();
            j += next;
            while ((j >= 0 && j <= 7) && (null == board.getBoard()[i][j])) {
                Move m = new Move(position, new Position(i, j));

                if (!board.isChecked(m, colour)) {
                    validMoves.add(m);
                }

                j += next;
            }
            if ((j >= 0 && j <= 7) && board.getBoard()[i][j].getColour() != colour) {
                
                Move m = new Move(position, new Position(i, j));
                if (!board.checkMove(m, colour)) {
                    validMoves.add(m);
                }
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
