package chess.project.pieces;
/*
 TODO
 - add a check statment for an event where a move puts a peice in check
 */

import chess.project.*;
import chess.project.movement.*;

/**
 * This represents a pawn.
 *
 * @author Alex Zurad
 */
public class Pawn extends Piece {

    private int enPassantTurn = -1;

    public Pawn(ChessColour col, Position pos) {
        super(col, pos);

    }

    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

        int forward = getForward();

        int i = this.position.getX();
        int j = this.position.getY();

        int dist = 1;
        if (j == 6 || j == 1) {
            dist = 2;
        }

        j += forward;
        int moved = 1;
        while (moved <= dist && (j <= 7 && j >= 0) && null == board.getBoard()[i][j]) {
            Move m = new Move(position, new Position(i, j));
            if (!board.isChecked(m, colour)) {
                validMoves.add(m);
            }
            j += forward;
            moved++;
        }

        // varifying diagonal moves (including EnPassant)
        i = this.position.getX();
        j = this.position.getY();
        for (int iNext = -1; iNext <= 1; iNext += 2) {
            if ((i + iNext) <= 7 && (i + iNext) >= 0) { // vertical component is not checked since pawns will change into another peice i the final row
                if (null != board.getBoard()[i + iNext][j + forward] && colour != board.getBoard()[i + iNext][j + forward].getColour()) {
                    Move m = new Move(position, new Position(i + iNext, j + forward));
                    if (!board.isChecked(m, colour)) {
                        validMoves.add(m);
                    }
                }
                if (null != board.getBoard()[i + iNext][j] && colour != board.getBoard()[i + iNext][j].getColour()) {
                    Piece p = board.getBoard()[i + iNext][j];

                    if (p instanceof Pawn) {
                        if ((board.getTurnNumber() - ((Pawn) p).getEnPassantTurn()) == 1) {
                            Move m = new Move(position, new Position(i + iNext, j + forward));
                            if (!board.isChecked(m, colour)) {
                                validMoves.add(m);
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public String toString() {
        return "P";
    }

    /** This sets the pawn's en-passant turn after it moves two spaces
     * 
     * @param turn The current turn number of the game
     */
    public void setEnPassant(int turn) {
        enPassantTurn = turn;
    }

    /** This returns the pawn's en-passant turn.
     * 
     * @return The en-passant turn for the pawn
     */
    public int getEnPassantTurn() {
        return enPassantTurn;
    }

    /** Returns representing the the direction along the y-axis that 
     *  would be considered the forward direction for the pawn.
     * 
     * @return -1 if piece is white, 1 if it is black
     */
    private int getForward() {
        return colour == ChessColour.WHITE ? -1 : 1;
    }

}
