package chess.project.pieces;

import chess.project.*;
import chess.project.movement.*;
import java.util.List;

/**
 * This class represents a Knight. Its movement consists of a L shaped jump that
 * can go over pieces of either colour.
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public class Knight extends Piece {

    /**
     * a piece defined as a Knight
     *
     * @param col a colour to distinguish which team it is on
     * @param pos a position on the board
     */
    public Knight(ChessColour col, Position pos) {
        super(col, pos);
    }

    private Knight(ChessColour col, Position pos, List<Move> validMoves) {
        super(col, pos);
        this.validMoves = validMoves;
    }

    /**
     * determines all valid moves of this Knight and saves them in the
     * validMoves List
     *
     * @param board the board the piece that is being evaluated on
     */
    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

        int rowDelta;
        int colDelta;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                rowDelta = Math.abs(position.getX() - i);
                colDelta = Math.abs(position.getY() - j);

                if ((rowDelta == 1 && colDelta == 2)
                        || (rowDelta == 2 && colDelta == 1)) {

                    Piece p = board.getBoard()[i][j];
                    if (p == null || p.getColour() != colour) {
                        addMove(board, i, j);
                    }
                }
            }
        }
    }

    /**
     * retrieves a string that may represent the type of piece
     *
     * @return a string "N" to represent this piece is a Knight
     */
    @Override
    public String toString() {
        return "N";
    }

    @Override
    public Piece clone() {
        return new Knight(colour, position, validMoves);
    }

}
