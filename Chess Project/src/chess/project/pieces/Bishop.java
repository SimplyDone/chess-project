package chess.project.pieces;

import chess.project.*;
import chess.project.movement.*;

/**
 *
 * @author Alex Zurad, 
 */
public class Bishop extends Piece {

    public Bishop(ChessColour col, Position pos) {
        super(col, pos);
    }

    
    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

        int i, j;

        for (int iNext = -1; iNext <= 1; iNext+=2) {
            for (int jNext = -1; jNext <= 1; jNext+=2) {

                i = this.position.getX();
                j = this.position.getY();

                i += iNext;
                j += jNext;

                while ((i >= 0 && i <= 7) && (j >= 0 && j <= 7) && (null == board.getBoard()[i][j])) {
                    Move m = new Move(position, new Position(i, j));

                    if (!board.isChecked(m, this.colour)) {
                        validMoves.add(m);
                    }

                    i += iNext;
                    j += jNext;
                }

                if ((i >= 0 && i <= 7) && (j >= 0 && j <= 7) && board.getBoard()[i][j].getColour() != this.colour) {
                    Move m = new Move(position, new Position(i, j));

                    if (!board.isChecked(m, colour)) {
                        validMoves.add(m);
                    }
                }
            }
        }

    }

    @Override
    public String toString() {
        return "B";
    }
}
