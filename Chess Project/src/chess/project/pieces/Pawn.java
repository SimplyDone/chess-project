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
 * @author Alex Zurad, Robbie McDonnell
 */


public class Pawn extends Piece {

    private int enPassantTurn = -1;

    /**an object representing a pawn
     * 
     * @param col the colour of the pawn
     * @param pos the current position of the pawn
     */
    public Pawn(ChessColour col, Position pos) {
        super(col, pos);

    }

    /**determines all valid moves of this pawn and saves them in the validMoves List
     * 
     * @param board an array of pieces 
     * 
     * @param i a placeholder for the horizontal position of the pawn
     * @param j a placeholder for the vertical position of the pawn
     *      both i and j may increment to simulate different moves to determine validity
     */
    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

        int forward = getForward();

        int i = this.position.getX();
        int j = this.position.getY();
        
        // the max distance a pawn may travel is 1
        int dist = 1;
        
        // if the pawn has a vertical position of 6 or 1 it may move 2 spaces given it passes the set restrictions
        if (j == 6 || j == 1) {
            dist = 2;
        }
        
        // verifying vertical moves
        j += forward;
        int moved = 1;
        // checks to see if the new position in question is out of bounds && if it is empty
        while (moved <= dist && inBounds(j) && null == board.getBoard()[i][j]) {
            
            addMove(board, i, j);
            j += forward;
            moved++;
        }

        // varifying diagonal moves including EnPassant
        i = this.position.getX();
        j = this.position.getY();
        for (int iNext = -1; iNext <= 1; iNext += 2) {
            
            if (inBounds(i+iNext)) { // vertical component is not checked since pawns will change into another peice in the final row (promotion)
                // checks if the position contains an enemy piece
                if (null != board.getBoard()[i + iNext][j + forward] && colour != board.getBoard()[i + iNext][j + forward].getColour()) {
                    addMove(board, i+iNext, j+forward);
                }
                // enpassent
                // compares a turn counter and which turn a pawn moved forward two spaces
                if (null != board.getBoard()[i + iNext][j] && colour != board.getBoard()[i + iNext][j].getColour()) {
                    Piece p = board.getBoard()[i + iNext][j];

                    if (p instanceof Pawn) {
                        if ((board.getTurnNumber() - ((Pawn) p).getEnPassantTurn()) == 1) {
                            addMove(board, i+iNext, j+forward);
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
