package chess.project.pieces;

import chess.project.movement.Position;
import chess.project.movement.Move;
import chess.project.*;

import java.util.*;
import java.io.Serializable;

/**Represents a single piece
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public abstract class Piece implements Serializable {

    protected final ChessColour colour;
    protected List<Move> validMoves;
    
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
     * @param isHuman True if the piece is controlled by a human player
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
        if (!board.checkForCheck(m, colour)) { 
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
    
    /**Evaluates all positions on the board that are diagonal to this piece.
     * If the position in question is within the bounds of the  board, not an
     *allied piece and will not put the allied King in check, the move 
     * will be added to a list of valid moves
     * 
     * @param board the board the piece will be evaluated on
     */
    protected void diagonalMovement(Board board){
        int i, j;

        // which direction the peice is moving in 
        for (int iNext = -1; iNext <= 1; iNext+=2) {
            for (int jNext = -1; jNext <= 1; jNext+=2) {

                i = this.position.getX() + iNext;
                j = this.position.getY() + jNext;

                // adds a valid move to the list when the move is in bounds and
                // on an empty space
                while (inBounds(i) && inBounds(j) && (null == board.getBoard()[i][j])) {

                    addMove(board, i, j);
                    i += iNext;
                    j += jNext;
                }
                // evaluates the final position if it was on an enemy peice
                if (inBounds(i) && inBounds(j) && 
                        board.getBoard()[i][j].getColour() != colour) {
                    addMove(board, i, j);
                }
            }
        }
    }
    
    /*
    protected void movement(Board board){
        
        int i, j;
        int c = 1; //
        boolean isKing = false;
        if(this instanceof Rook){
            verticalMovement(board);
            horizontalMovement(board);
            
        }else{
            
            if(this instanceof Bishop){
                c = 2;
            }else if(this instanceof King){
                isKing = true;
            }
            // which direction the peice is moving in 
            for (int iNext = -1; iNext <= 1; iNext+=c) {
                for (int jNext = -1; jNext <= 1; jNext+=c) {

                    i = this.position.getX() + iNext;
                    j = this.position.getY() + jNext;

                    // adds a valid move to the list when the move is in bounds and
                    // on an empty space
                    while (!isKing && inBounds(i) && inBounds(j) && 
                            (null == board.getBoard()[i][j])) {

                        addMove(board, i, j);
                        i += iNext;
                        j += jNext;
                    }
                    // evaluates the final position if it was on an enemy peice
                    if (inBounds(i) && inBounds(j) && null != board.getBoard()[i][j]
                            && board.getBoard()[i][j].getColour() != colour) {
                        addMove(board, i, j);
                    }
                }
            }
        }
    }
    */
    
    /**Evaluates all positions on the board that are directly above and below 
     * this piece. If the position in question is within the bounds of the 
     * board, not an allied piece and will not put the allied King in check, the
     * move will be added to a list of valid moves
     * 
     * @param board the board the piece will be evaluated on
     */
    protected void verticalMovement(Board board){
        int i,j;
        i = this.position.getX(); // x component is constant
        
       //next is the change that will take place in the position being evaluated
        for (int next = -1; next <= 1; next += 2) {
            j = this.position.getY() + next;
            
            // adds a valid move to the list when the move is in bounds and
            // on an empty space
            while (inBounds(j) && (null == board.getBoard()[i][j])) {
                addMove(board, i, j);

                j += next;
            }
            // evaluates the final position if it was on an enemy peice
            if (inBounds(j) && board.getBoard()[i][j].getColour() != colour) {
                addMove(board, i, j);
            }
        }
    }
    
    /**Evaluates all positions on the board that are adjacent to this piece 
     * If the position in question is within the bounds of the board, not
     * an allied piece and will not put the allied King in check, the
     * move will be added to a list of valid moves
     * 
     * 
     * @param board the board the piece will be evaluated on
     */
    protected void horizontalMovement(Board board){
        int i,j;
        j = this.position.getY(); // y component is constant
        
        //next is the change that will take place in the position being evaluated
        for (int next = -1; next <= 1; next += 2) {
            i = this.position.getX()+ next;
            
            // adds a valid move to the list when the move is in bounds and
            // on an empty space
            while (inBounds(i) && (null == board.getBoard()[i][j])) {
                addMove(board, i, j);

                i += next;
            }
            // evaluates the final position if it was on an enemy peice
            if (inBounds(i) && board.getBoard()[i][j].getColour() != colour) {
                addMove(board, i, j);
            }
        }
    }
    
    @Override
    public abstract Piece clone();
    
    
    
}
