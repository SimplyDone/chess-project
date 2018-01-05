package chess.project.pieces;
/*
 TODO
 - OPTIMIZE
 - combine the horizontal and vertical search
 */

import chess.project.*;

/**
 *
 * @author Alex Zurad
 */
public class Rook extends Piece {

    private boolean canCastle = true;
    
    public Rook(boolean col, Position pos) {
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
            if ((i >= 0 && i <= 7) && board.getBoard()[i][j].getColour() != this.getColour()) {
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
            if ((j >= 0 && j <= 7) && board.getBoard()[i][j].getColour() != this.getColour()) {
                
                Move m = new Move(position, new Position(i, j));
                if (!board.checkMove(m, colour)) {
                    validMoves.add(m);
                }
            }
        }
        
    }

    public boolean canCastle() {
        return canCastle;
    }
    
    public void flagCastle() {
        canCastle = false;
    }

    
    @Override
    public String toString() {
        return "R";
    }
    
}
