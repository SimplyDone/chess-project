package chess.project.pieces;

import chess.project.movement.Position;
import chess.project.movement.Move;
import chess.project.*;

import java.util.*;
import java.io.Serializable;

/**
 * Represents a single piece
 * 
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public abstract class Piece implements Serializable {

    protected final ChessColour colour;
    protected final List<Move> validMoves;
    
    protected Position position;
    
    /**The representation of a piece on the board
     * 
     * @param col the colour of the piece (ie 'BLACK' or 'WHITE'
     * @param pos the position of the object on the board (ie [6,4] or [3,7])
     */
    public Piece(ChessColour col, Position pos) {
        colour = col;
        position = pos;
        validMoves = new LinkedList<>();
    }

    /**updates the current position of a piece
     * 
     * @param p the new position the piece is going to be moved to 
     */
    public void move(Position p) {
        position = p;
    }

    /**retrieves all valid moves for this piece
     * 
     * @return a List containing all valid moves for this piece
     */
    public List<Move> getValidMoves() {
        return validMoves;
    }

    /**abstract method that updates the list of valid moves based on the board
     * 
     * @param board board may contain either the current board or a temp board
     */
    public abstract void updateValidMoves(Board board);

    /**retrieves the colour of the piece
     * 
     * @return the Chess colour associated with the piece (ie 'BLACK' or 'WHITE')
     */
    public ChessColour getColour() {
        return colour;
    }
    
    /**retrieves the location of the piece on the board
     * 
     * @return a position (ie [0,7]] or [4,5])
     */
    public Position getPosition() {
        return position;
    }

}
