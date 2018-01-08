package chess.project.pieces;
/*
 TODO
 - add a check statment for an event where a move puts a peice in check 
 - update alongside bishop and rook after optimizing each class
 */

import chess.project.movement.*;
import chess.project.*;

/**
 *
 * @author Alex Zurad
 */
public class Queen extends Piece {

    public Queen(ChessColour col, Position pos) {
        super(col, pos);
    }

    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

/////////////////////////////////BISHOP/////////////////////////////////////////
        int i, j;

        for (int iNext = -1; iNext <= 1; iNext += 2) {

            for (int jNext = -1; jNext <= 1; jNext += 2) {

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
//////////////////////////////////ROOK//////////////////////////////////////////

        for (int next = -1; next <= 1; next += 2) {
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
        //////////////////        same as above for y axis      ////////////////
        for (int next = -1; next <= 1; next += 2) {
            i = this.position.getX();
            j = this.position.getY();
            j += next;
            while (inBounds(j) && (null == board.getBoard()[i][j])) {
                addMove(board, i, j);

                j += next;
            }
            if (inBounds(j) && board.getBoard()[i][j].getColour() != colour) {
                addMove(board, i, j);
            }
        }
    }

    @Override
    public String toString() {
        return "Q";
    }

}
