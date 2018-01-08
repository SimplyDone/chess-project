package chess.project.pieces;

import chess.project.*;
import chess.project.movement.*;

/**
 *
 * @author Alex Zurad, Robbie McDonnell
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

                i = this.position.getX() + iNext;
                j = this.position.getY() + jNext;

                while (inBounds(i) && inBounds(j) && (null == board.getBoard()[i][j])) {

                    addMove(board, i, j);
                    i += iNext;
                    j += jNext;
                }

                if (inBounds(i) && inBounds(j) && board.getBoard()[i][j].getColour() != colour) {
                    addMove(board, i, j);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "B";
    }
}
