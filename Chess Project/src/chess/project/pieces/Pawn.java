package chess.project.pieces;

import chess.project.*;
import chess.project.movement.*;
import java.util.List;

/**
 * This represents a pawn.
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public class Pawn extends Piece {

    private int enPassantTurn = -1;

    /**
     * a piece defined as a Pawn
     *
     * @param col a colour to distinguish which team it is on
     * @param pos a position on the board
     */
    public Pawn(ChessColour col, Position pos) {
        super(col, pos);
    }

    private Pawn(ChessColour col, Position pos, List<Move> validMoves, int enPassantTurn) {
        super(col, pos);
        this.validMoves = validMoves;
        this.enPassantTurn = enPassantTurn;
    }

    /**
     * determines all valid moves of this pawn and saves them in the validMoves
     * List
     *
     * @param board the board the piece that is being evaluated on
     * @param isHuman a boolean that tells the method if this is a  human player
     *              or an AI player to determine which moves are valid
     */
    @Override
    public void updateValidMoves(Board board, boolean isHuman) {
        if(isHuman)
            humanValidMoves(board);
        else
            AIValidMoves(board);
    }
    /**This provides a list of valid moves that this pawn may make with AI 
     * specific moves regarding the event of promotion
     * 
     * @param board the board the piece that is being evaluated on
     */
    public void AIValidMoves(Board board) {
        
        int jumpPos, promoPos; 
        
        if (this.colour == ChessColour.WHITE){
            jumpPos = 6;
            promoPos = 1;
        }else{
            jumpPos = 1;
            promoPos = 6;
        }
        
        int forward = getForward();
        int i = this.position.getX();
        int j = this.position.getY();
        

        
        if(j == jumpPos){
            moveForward(board, forward,i,j, 2);
            
        }else if(j == promoPos){
            if(board.getBoard()[i][j+forward] == null){
                // This is where the promotion algorithm goes
            }
            
            
        }else{
            moveForward(board, forward,i,j,1);
            takePiece(board, forward, i,j);
        }
        
    }
    
    /**This provides a list of valid moves that this pawn may make for a human 
     * player
     * 
     * @param board the board the piece that is being evaluated on
     */
    public void humanValidMoves(Board board) {
        validMoves.clear();
        
        int forward = getForward();
        int i = this.position.getX();
        int j = this.position.getY();

        // the max distance a pawn may travel is 1
        int dist = 1;

        // if the pawn has a vertical position of 6 or 1 it may move 2 spaces 
        // given it passes the set restrictions
        if (j == 6 || j == 1) {
            dist = 2;
        }
        
        moveForward(board,i,j,forward,dist);
        takePiece(board,i,j,forward);
        
    }
    /**evaluates a pawn movement forward 'd' number of spaces
     * 
     * @param board
     * @param dist
     * @param i
     * @param j
     * @param forward
     * @param dist
     */
    
    private void moveForward(Board board,int i, int j, int forward, int dist){
        
        j += forward;
        
        int moved = 1;
        
        // checks to see if the new position in question is 
        // out of bounds && if it is empty
        while (moved <= dist && inBounds(j) && board.getBoard()[i][j] == null) {
            addMove(board, i, j);
            j += forward;
            moved++;
        }
    }
    /**
     * 
     * @param board
     * @param i
     * @param j
     * @param forward 
     */
    private void takePiece(Board board,int i, int j, int forward){
        // varifying diagonal moves including EnPassant
        i = this.position.getX();
        j = this.position.getY();
        for (int iNext = -1; iNext <= 1; iNext += 2) {

            if (inBounds(i + iNext)) { // vertical component is not checked since pawns will change into another peice in the final row (promotion)
                // checks if the position contains an enemy piece
                if (null != board.getBoard()[i + iNext][j + forward]
                        && colour != board.getBoard()[i + iNext][j + forward].getColour()) {
                    addMove(board, i + iNext, j + forward);
                
                // enpassent
                // compares a turn counter and which turn a pawn moved 
                // forward two spaces
                }else if (null != board.getBoard()[i + iNext][j]
                        && colour != board.getBoard()[i + iNext][j].getColour()) {
                    Piece p = board.getBoard()[i + iNext][j];

                    if (p instanceof Pawn) {
                        if ((board.getTurnNumber() - ((Pawn) p).getEnPassantTurn()) == 1) {
                            addMove(board, i + iNext, j + forward);
                        }
                    }
                }
            }
        }
    }

    /**
     * This sets the pawn's en-passant turn after it moves two spaces
     *
     * @param turn The current turn number of the game
     */
    public void setEnPassant(int turn) {
        enPassantTurn = turn;
    }

    /**
     * This returns the pawn's en-passant turn.
     *
     * @return The en-passant turn for the pawn
     */
    public int getEnPassantTurn() {
        return enPassantTurn;
    }

    /**
     * Returns representing the the direction along the y-axis that would be
     * considered the forward direction for the pawn.
     *
     * @return -1 if piece is white, 1 if it is black
     */
    private int getForward() {
        return colour == ChessColour.WHITE ? -1 : 1;
    }

    /**
     * retrieves a string that may represent the type of piece
     *
     * @return a string "P" to represent this piece is a Pawn
     */
    @Override
    public String toString() {
        return "P";
    }

    @Override
    public Piece clone() {
        return new Pawn(colour, position, validMoves, enPassantTurn);
    }

}
