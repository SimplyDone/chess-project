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

                i = this.position.getX();
                j = this.position.getY();

                i += iNext;
                j += jNext;

                while ((i >= 0 && i <= 7) && (j >= 0 && j <= 7) && (null == board.getBoard()[i][j])) {
                    Move m = new Move(position, new Position(i, j));

                    if (!board.isChecked(m, colour)) {
                        validMoves.add(m);
                    }

                    i += iNext;
                    j += jNext;
                }

                if ((i >= 0 && i <= 7) && (j >= 0 && j <= 7) && board.getBoard()[i][j].getColour() != colour) {
                    Move m = new Move(position, new Position(i, j));

                    if (!board.isChecked(m, colour)) {
                        validMoves.add(m);
                    }
                }
            }
        }
//////////////////////////////////ROOK//////////////////////////////////////////

        for (int next = -1; next <= 1; next += 2) {
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
        //////////////////        same as above for y axis      ////////////////
        for (int next = -1; next <= 1; next += 2) {
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

                if (!board.isChecked(m, colour)) {
                    validMoves.add(m);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Q";
    }

}
