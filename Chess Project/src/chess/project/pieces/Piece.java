package chess.project.pieces;
/*
 TODO
 - 
 - 
 */

import chess.project.*;

import java.util.*;
import java.io.Serializable;

/**
 * Represents a single piece.
 *
 * @author Alex Zurad
 */
public abstract class Piece implements Serializable {

    protected final boolean colour; // false-black, true-white
    protected Position position;
    protected List<Move> validMoves;

    public Piece(boolean col, Position pos) {
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

    /**
     * Returns the colour of the piece;
     *
     * @return the colour of the piece
     */
    public String getColourString() {
        return colour ? "white" : "black";
    }

    public boolean getColour() {
        return colour;
    }

    public Position getPosition() {
        return position;
    }

}
