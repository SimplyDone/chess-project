package chess.project.pieces;
/*
 TODO
 - OPTIMIZE
 - combine the horizontal and vertical search
 */

import chess.project.Board;
import chess.project.Move;
import chess.project.Position;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alex Zurad
 */
public class Rook extends Piece {

    private boolean canCastle = true;
    
    public Rook(boolean col, Position pos) {
        super(col, pos);
    }

    @Override
    public List<Move> getValidMoves(Board board) {
        List<Move> validMoves = new LinkedList<>();

        int i, j;

        for (int next = -1; next <= 1; next+=2) {
            i = this.position.getX();
            j = this.position.getY();
            i += next;
            while ((i >= 0 && i <= 7) && (null == board.getBoard()[i][j])) {
                Move m = new Move(position, new Position(i, j));

                if (true /* add check if move puts you in check */) {
                    validMoves.add(m);
                }

                i += next;
            }
            if ((i >= 0 && i <= 7) && board.getBoard()[i][j].getColour() != this.getColour()) {
                Move m = new Move(position, new Position(i, j));

                if (true /* add check if move puts you in check */) {
                    validMoves.add(m);
                }
            }
        }
        //////////////////////////same as above fory axis///////////////////////
        for (int next = -1; next <= 1; next+=2) {
            i = this.position.getX();
            j = this.position.getY();
            j += next;
            while ((j >= 0 && j <= 7) && (null == board.getBoard()[i][j])) {
                Move m = new Move(position, new Position(i, j));

                if (true /* add check if move puts you in check */) {
                    validMoves.add(m);
                }

                j += next;
            }
            if ((j >= 0 && j <= 7) && board.getBoard()[i][j].getColour() != this.getColour()) {
                Move m = new Move(position, new Position(i, j));

                if (true /* add check if move puts you in check */) {
                    validMoves.add(m);
                }
            }
        }
        return validMoves;
    }

    public boolean canCastle() {
        return canCastle;
    }
    
    @Override
    public String toString() {
        return "R";
    }
    
}
