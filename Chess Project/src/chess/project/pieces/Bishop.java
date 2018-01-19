package chess.project.pieces;

import chess.project.*;
import chess.project.movement.*;
import java.util.List;

/**
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public class Bishop extends Piece {

    /**
     * A piece defined as a Bishop
     *
     * @param col a colour to distinguish which team it is on
     * @param pos a position on the board
     */
    public Bishop(ChessColour col, Position pos) {
        super(col, pos);
    }

    private Bishop(ChessColour col, Position pos, List<Move> validMoves) {
        super(col, pos);
        this.validMoves = validMoves;
    }

    /**
     * determines all valid moves of this Bishop and saves them in the
     * validMoves List
     *
     * @param board the board the piece that is being evaluated on
     */
    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

        diagonalMovement(board);
        //movement(board);
    }

    /**
     * retrieves a string that may represent the type of piece
     *
     * @return a string "B" to represent this piece is a Bishop
     */
    @Override
    public String toString() {
        return "B";
    }

    @Override
    public Piece clone() {
        return new Bishop(colour, position, validMoves);
    }
}
