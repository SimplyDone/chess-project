package chess.project.pieces;

import chess.project.movement.*;
import chess.project.*;
import java.util.List;

/**
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public class Rook extends Piece {

    private boolean castlable = true;

    /**
     * A piece defined as a Rook
     *
     * @param col a colour to distinguish which team it is on
     * @param pos a position on the board
     */
    public Rook(ChessColour col, Position pos) {
        super(col, pos);
    }

    private Rook(ChessColour col, Position pos, List<Move> validMoves, boolean castlable) {
        super(col, pos);
        this.castlable = castlable;
        this.validMoves = validMoves;
    }

    /**
     * determines all valid moves of this Rook and saves them in the validMoves
     * List
     *
     * @param board the board the piece that is being evaluated on
     * @param isHuman differentiates between a human player and an AI player
     */
    @Override
    public void updateValidMoves(Board board, boolean isHuman) {

        validMoves.clear();

        //movement(board);
        horizontalMovement(board);
        verticalMovement(board);
    }

    /**
     * This method returns if the rook may castle or if it has been flagged
     *
     * @return boolean if this Rook is valid for castling.
     */
    public boolean isCastlable() {
        return castlable;
    }

    /**
     * This method flags the rook as unable to be used for castling.
     *
     */
    public void setCastle(boolean b) {
        castlable = b;
    }

    /**
     * retrieves a string that may represent the type of piece
     *
     * @return a string "R" to represent this piece is a Rook
     */
    @Override
    public String toString() {
        return "R";
    }

    @Override
    public Piece clone() {
        return new Rook(colour, position, validMoves, castlable);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.castlable ? 1 : 0);
        return hash;
    }    

}
