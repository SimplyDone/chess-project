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
    /**adds a move to the validMoves list if it will not put it's allied King in check
     * 
     * @param board an array containing all pieces and information regarding turns
     * @param x the horizontal position of the moves final location
     * @param y the vertical position of the moves final location
     */
    protected void addMove(Board board, int x, int y){
        Move m = new Move(position, new Position(x, y));
        if (!board.isChecked(m, colour)) { 
            validMoves.add(m);
        }
    }
    
    /**checks if an arbitrary x or y position is within the bounds of the board
     * 
     * @param p the position of the piece
     * @return true if the piece is within the bounds
     */
    protected boolean inBounds(int p){
        return (0 <= p && p <= 7);
    }

}
