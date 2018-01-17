package chess.project.pieces;

import chess.project.movement.*;
import chess.project.*;
import java.util.List;

/**
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public class Queen extends Piece {

    /**
     * A piece defined as a Queen
     *
     * @param col a colour to distinguish which team it is on
     * @param pos a position on the board
     */
    public Queen(ChessColour col, Position pos) {
        super(col, pos);
    }
    
    private Queen(ChessColour col, Position pos, List<Move> validMoves){
        super(col,pos);
        this.validMoves = validMoves;
    }

    /**
     * determines all valid moves of this Queen and saves them in the validMoves
     * List
     *
     * @param board the board the piece that is being evaluated on
     */
    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

//        movement(board);
        diagonalMovement(board);
        horizontalMovement(board);
        verticalMovement(board);
    }

    /**
     * retrieves a string that may represent the type of piece
     *
     * @return a string "Q" to represent this piece is a Queen
     */
    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public Piece clone() {
        return new Queen(colour, position, validMoves);
    }

}
