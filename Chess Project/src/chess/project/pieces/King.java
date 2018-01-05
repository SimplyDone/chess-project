package chess.project.pieces;
/*
 TODO
 - add a check statment for an event where a move puts a peice in check 
 - add a variable of some sort to determine if the player is currently in check 
 before selecting their next move (for castling)
 */

import chess.project.*;

/**
 *
 * @author Alex Zurad
 */
public class King extends Piece {

    private boolean canCastle = true;

    public King(boolean col, Position pos) {
        super(col, pos);
    }

    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

        int i = this.position.getX();
        int j = this.position.getY();

        for (int iNext = -1; iNext <= 1; iNext++) {
            for (int jNext = -1; jNext <= 1; jNext++) {

                if (((i + iNext) >= 0 && (i + iNext) <= 7) && ((j + jNext) >= 0 && (j + jNext) <= 7)) {

                    Move m = new Move(position, new Position(i + iNext, j + jNext));

                    if (null == board.getBoard()[i + iNext][j + jNext] || this.getColour() != board.getBoard()[i + iNext][j + jNext].getColour()) {

                        if (true /* add check if move puts you in check */) {
                            validMoves.add(m);
                        }
                    }
                }
            }
        }
        /////// CASTLE ////////////////////
        if (this.canCastle() && true /* add a check if the player is currently in check */) {

            //                LEFT SIDE
            boolean castle = true;
            if (board.getBoard()[0][j] instanceof Rook) {

                if (board.getBoard()[1][j] != null) {
                    castle = false;
                } else {
                    for (int k = 2; k <= 3; k++) {
                        if (board.getBoard()[k][j] != null || true /* add check if move puts you in check */) {
                            castle = false;
                        }
                    }
                }

                if (castle) {
                    Move m = new Move(position, new Position(2, j));
                    validMoves.add(m);
                }
            }

            //                RIGHT SIDE
            castle = true;
            if (board.getBoard()[7][j] instanceof Rook) {
                if (board.getBoard()[7][j] instanceof Rook) {
                    for (int p = 5; p <= 6; p++) {
                        if (board.getBoard()[p][j] != null || true /* add check if move puts you in check */) {
                            castle = false;
                        }
                    }
                    if (castle) {
                        Move m = new Move(position, new Position(6, j));
                        validMoves.add(m);
                    }
                }
            }
        }
    }

    public boolean canCastle() {
        return canCastle;
    }
    
    public void flagCastle(){
        canCastle = false;
    }

    @Override
    public String toString() {
        return "K";
    }

}
