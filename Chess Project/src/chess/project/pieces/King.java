package chess.project.pieces;

import chess.project.movement.Position;
import chess.project.movement.Move;
import chess.project.*;

/**
 *
 * @author Alex Zurad
 */
public class King extends Piece {

    private boolean castlable = true;

    public King(ChessColour col, Position pos) {
        super(col, pos);
    }

    @Override
    public void updateValidMoves(Board board) {
        validMoves.clear();

        int i = this.position.getX();
        int j = this.position.getY();

        for (int iNext = -1; iNext <= 1; iNext++) {
            for (int jNext = -1; jNext <= 1; jNext++) {

                if (inBounds(i+iNext) && inBounds(j+jNext)) {

                    if (null == board.getBoard()[i + iNext][j + jNext] || colour != board.getBoard()[i + iNext][j + jNext].getColour()) {

                        addMove(board, i+iNext, j+jNext);
                    }
                }
            }
        }
        /////// CASTLE ////////////////////

        if (this.isCastlable() && !board.isChecked(new Move(position, position), colour)) {

            //                LEFT SIDE
            boolean castle = true;
            if (board.getBoard()[0][j] instanceof Rook && ((Rook) board.getBoard()[0][j]).isCastlable()) {
                if (board.getBoard()[1][j] != null) {
                    castle = false;
                } else {
                    for (int k = 2; k <= 3; k++) {
                        if (board.getBoard()[k][j] != null || board.isChecked(new Move(position, new Position(k, j)), colour)) {
                            castle = false;
                        }
                    }
                }
            } else {
                castle = false;
            }

            if (castle) {
                Move m = new Move(position, new Position(2, j));
                validMoves.add(m);
            }

            //                RIGHT SIDE
            castle = true;

            if (board.getBoard()[7][j] instanceof Rook && ((Rook) board.getBoard()[7][j]).isCastlable()) {
                for (int p = 5; p <= 6; p++) {
                    if (board.getBoard()[p][j] != null || board.isChecked(new Move(position, new Position(p, j)), colour)) {
                        castle = false;
                    }
                }
            } else {
                castle = false;
            }

            if (castle) {
                Move m = new Move(position, new Position(6, j));
                validMoves.add(m);
            }

        }
    }

    public boolean isChecked(Board b) {

/////////////////////////////////Diagonal///////////////////////////////////////
        int i, j;

        for (int iNext = -1; iNext <= 1; iNext += 2) {

            for (int jNext = -1; jNext <= 1; jNext += 2) {

                i = this.position.getX();
                j = this.position.getY();

                i += iNext;
                j += jNext;

                while (inBounds(i) && inBounds(j) && b.getBoard()[i][j] == null) {
                    i += iNext;
                    j += jNext;
                }

                if (inBounds(i) && inBounds(j) && b.getBoard()[i][j].getColour() != colour) {
                    if (b.getBoard()[i][j] instanceof Bishop || b.getBoard()[i][j] instanceof Queen) {
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
//////////////////////////Horizontal-Vertical///////////////////////////////////

        for (int next = -1; next <= 1; next += 2) {
            i = this.position.getX();
            j = this.position.getY();
            i += next;
            while (inBounds(i) && null == b.getBoard()[i][j]) {
                i += next;
            }
            if (inBounds(i) && b.getBoard()[i][j].getColour() != colour) {
                if (b.getBoard()[i][j] instanceof Rook || b.getBoard()[i][j] instanceof Queen) {
                    return true;
                } else if (kingInstance(b, i, j)) {
                    return true;
                }
            }
        }
        //////////////////        same as above for y axis      ////////////////
        for (int next = -1; next <= 1; next += 2) {
            i = position.getX();
            j = position.getY();
            j += next;
            while (inBounds(j) && (null == b.getBoard()[i][j])) {
                j += next;
            }
            if (inBounds(j) && b.getBoard()[i][j].getColour() != colour) {
                if (b.getBoard()[i][j] instanceof Rook || b.getBoard()[i][j] instanceof Queen) {
                    return true;
                } else if (kingInstance(b, i, j)) {
                    return true;
                }
            }
        }

///////////////////////////////// KNIGHTS///////////////////////////////////////
        int rowDelta;
        int colDelta;

        for (i = 0; i < 8; i++) {/* optimize later (haardcode?) */

            for (j = 0; j < 8; j++) {

                rowDelta = Math.abs(position.getX() - i);
                colDelta = Math.abs(position.getY() - j);

                if ((rowDelta == 1 && colDelta == 2) || (rowDelta == 2 && colDelta == 1)) {

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

    private boolean kingInstance(Board b, int i, int j) {
        if (b.getBoard()[i][j] instanceof King) {
            if ((Math.abs(position.getX() - i) == 1) || (Math.abs(position.getY() - j) == 1)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCastlable() {
        return castlable;
    }

    public void setCastle(boolean b) {
        castlable = b;
    }

    @Override
    public String toString() {
        return "K";
    }
}
