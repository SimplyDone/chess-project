package chess.project.pieces;

import chess.project.movement.Position;
import chess.project.movement.Move;
import chess.project.*;

import java.util.*;
import java.io.Serializable;

/**
 * Represents a single piece.
 *
 * @author Alex Zurad
 */
public abstract class Piece implements Serializable {

    protected final ChessColour colour;
    protected final List<Move> validMoves;
    
    protected Position position;

    public Piece(ChessColour col, Position pos) {
        colour = col;
        position = pos;
        validMoves = new LinkedList<>();
    }

    public void move(Position p) {
        position = p;
    }

    public List<Move> getValidMoves() {
        return validMoves;
    }

    public abstract void updateValidMoves(Board board);


    public ChessColour getColour() {
        return colour;
    }

    public Position getPosition() {
        return position;
    }

}
