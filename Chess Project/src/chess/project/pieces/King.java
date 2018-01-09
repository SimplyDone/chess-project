package chess.project.pieces;

import chess.project.movement.Position;
import chess.project.movement.Move;
import chess.project.*;

/**
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public class King extends Piece {

    private boolean castlable = true;

    /**a piece defined as a King
     * 
     * @param col a colour to distinguish which team it is on
     * @param pos a position on the board
     */
    public King(ChessColour col, Position pos) {
        super(col, pos);
    }

    /**determines all valid moves of this King and saves them in the 
     * validMoves List
     * 
     * @param board the board the piece that is being evaluated on
     */
    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

        int i = this.position.getX();
        int j = this.position.getY();

        // checks all adjacent spaces to the king if they are a valid move
        for (int iNext = -1; iNext <= 1; iNext++) {
            for (int jNext = -1; jNext <= 1; jNext++) {

                if (inBounds(i+iNext) && inBounds(j+jNext)) {
                    if (null == board.getBoard()[i + iNext][j + jNext] || 
                            colour != board.getBoard()[i + iNext][j + jNext].getColour()) {

                        addMove(board, i+iNext, j+jNext);
                    }
                }
            }
        }

//                              Castling
// checks if the king is able to casle by its boolean variable and position on the board
        int y = (ChessColour.WHITE == colour) ? 7 : 0;
        
        if (this.isCastlable() && j == y && i == 4 &&
                !board.isChecked(new Move(position, position), colour)) {
            

            //                LEFT SIDE
            // checks the rook to the left of the king
            // checks if there is a rook in the correct position and if it is 
            //  castleable
            boolean castle = true;
            if (board.getBoard()[0][j] instanceof Rook && 
                    ((Rook) board.getBoard()[0][j]).isCastlable()) {
                
                // checks if there are any peices in the way of the castle and
                // if any of the spaces between the king and its final position
                //  will place it in check
                if (board.getBoard()[1][j] != null) {
                    castle = false;
                } else {
                    for (int k = 2; k <= 3; k++) {

                        if (board.getBoard()[k][j] != null || 
                                board.isChecked(new Move(position, new Position(k, j)), colour)) {
                            

                            castle = false;
                        }
                    }
                }
            } else {
                castle = false;
            }
            // adds this to the valid moves list if castle is true
            if (castle) {
                Move m = new Move(position, new Position(2, j));
                validMoves.add(m);
            }

            //                RIGHT SIDE
            // checks the rook to the Right of the king
            // checks if there is a rook in the correct position and if it is 
            //  castleable
            castle = true;

            if (board.getBoard()[7][j] instanceof Rook && 
                    ((Rook) board.getBoard()[7][j]).isCastlable()) {
                
                // checks if there are any peices in the way of the castle and
                // if any of the spaces between the king and its final position
                //  will place it in check
                for (int p = 5; p <= 6; p++) {

                    if (board.getBoard()[p][j] != null || 
                            board.isChecked(new Move(position, new Position(p, j)), colour)) {
                        
                        castle = false;
                    }
                }
                
            } else { // if there is no rook or a rook is not castleable
                castle = false;
            }
            
            // adds this to the valid moves list if castle is true
            if (castle) {
                Move m = new Move(position, new Position(6, j));
                validMoves.add(m);
            }
        }
    }

    /**this method examines every position on the board that may contain a 
     * threat to the king. If an enemy piece is found, the game determines if 
     * the piece currently has the ability to take the king 
     * 
     * @param b the board that the checks will be based off of
     * @return true once a piece is found that may take the king
     *         false if all threatening spaces have been checked and there are 
     *          no threats to the king
     */
    public boolean isChecked(Board b) {
//                               Diagonal
        //checks all locations diagonal to the king 
        // if the first piece found is an enemy it determines if the piece is 
        // able to put the king in check
        int i, j;

        for (int iNext = -1; iNext <= 1; iNext += 2) {
            for (int jNext = -1; jNext <= 1; jNext += 2) {

                i = this.position.getX();
                j = this.position.getY();

                i += iNext;
                j += jNext;

                while (inBounds(i) && inBounds(j) && b.getBoard()[i][j] ==null){
                    i += iNext;
                    j += jNext;
                }

                if (inBounds(i) && inBounds(j) && 
                        b.getBoard()[i][j].getColour() != colour) {
                    if (b.getBoard()[i][j] instanceof Bishop || 
                            b.getBoard()[i][j] instanceof Queen) {
                        return true;
                    } else if (kingInstance(b, i, j)) {
                        return true;
                    } else if (b.getBoard()[i][j] instanceof Pawn) {
                        if (colour == ChessColour.WHITE) {
                            if ((position.getY() - j) == 1) {
                                return true;
                            }
                        } else {
                            if ((position.getY() - j) == -1) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
//                           Horizontal
        //checks all locations adjacent to the king 
        // if the first piece found is an enemy it determines if the piece is 
        // able to put the king in check
        for (int next = -1; next <= 1; next += 2) {
            i = this.position.getX();
            j = this.position.getY();
            i += next;
            while (inBounds(i) && null == b.getBoard()[i][j]) {
                i += next;
            }
            if (inBounds(i) && b.getBoard()[i][j].getColour() != colour) {
                if (b.getBoard()[i][j] instanceof Rook || 
                        b.getBoard()[i][j] instanceof Queen) {
                    return true;
                } else if (kingInstance(b, i, j)) {
                    return true;
                }
            }
        }
//                         Vertical
        // checks all locations above and below the king 
        // if the first piece found is an enemy it determines if the piece is 
        // able to put the king in check
        for (int next = -1; next <= 1; next += 2) {
            i = position.getX();
            j = position.getY();
            j += next;
            while (inBounds(j) && (null == b.getBoard()[i][j])) {
                j += next;
            }
            if (inBounds(j) && b.getBoard()[i][j].getColour() != colour) {
                if (b.getBoard()[i][j] instanceof Rook || 
                        b.getBoard()[i][j] instanceof Queen) {
                    return true;
                } else if (kingInstance(b, i, j)) {
                    return true;
                }
            }
        }

//                            Knight Movement
        // this is checking if a knight may take a king
        int rowDelta;
        int colDelta;

        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {

                rowDelta = Math.abs(position.getX() - i);
                colDelta = Math.abs(position.getY() - j);

                if ((rowDelta == 1 && colDelta == 2) || 
                        (rowDelta == 2 && colDelta == 1)) {

                    Piece p = b.getBoard()[i][j];
                    if (p != null && p.getColour() != colour) {
                        if (b.getBoard()[i][j] instanceof Knight) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**executes a unique check if the king is found in the isChecked method
     * kings may only move one space so this method method checks if the move 
     * in question is within one space of the enemy king
     * 
     * @param b the board that the move in question will be evaluated on
     * @param i an integer representing the x value of the move in question
     * @param j an integer representing the y value of the move in question
     * @return true if the king is in check
     * @return false if the king is not in check
     */
    private boolean kingInstance(Board b, int i, int j) {
        if (b.getBoard()[i][j] instanceof King) {
            if ((Math.abs(position.getX() - i) == 1) || 
                    (Math.abs(position.getY() - j) == 1)) {
                return true;
            }
        }
        return false;
    }

    /** This method returns if the King may castle or if it has been flagged
     * 
     * @return boolean if this King is valid for castling.
     */
    public boolean isCastlable() {
        return castlable;
    }

    /** This method flags the King as unable to be used for castling.
     * 
     * @param b the boolean value that will replace the current value of 
     *          castlable
     */
    public void setCastle(boolean b) {
        castlable = b;
    }

    /**retrieves a string that may represent the type of piece
     * 
     * @return a string "K" to represent this piece is a King
     */
    @Override
    public String toString() {
        return "K";
    }
}
