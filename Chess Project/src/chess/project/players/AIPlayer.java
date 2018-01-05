package chess.project.players;

import chess.project.Board;
import chess.project.Move;
import chess.project.pieces.*;
import java.util.*;

/**
 *
 * @author Alex Zurad
 */
public class AIPlayer extends Player {

    private Piece[][] testBoard;
    private final int difficulty;
    private final List<Move> allValidMoves;

    public AIPlayer(boolean isWhite, Board board, int difficulty) {
        super(isWhite, board);
        this.difficulty = difficulty;
        this.allValidMoves = new LinkedList<>();
    }

    @Override
    public Move getMove() {

        System.out.println(COLOUR);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j] != null && board.getBoard()[i][j].getColour() == colour) {
                    allValidMoves.addAll(board.getBoard()[i][j].getValidMoves());
                }
            }
        }

        Random rand = new Random();
        Move m = allValidMoves.get(rand.nextInt(allValidMoves.size()));
        System.out.println(m.getOldPosition() + " -> " + m.getNewPosition());

        return m;
    }

    private void buildMoveTree(int depth) {

    }

    public void evaluateBoard(Piece[][] board) {
        int score = 0;

        int modifier = 1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Piece p = board[i][j];
                if (p != null) {

                    if (p.getColour() == colour) {
                        modifier = 1;
                    } else {
                        modifier = -1;
                    }

                    if (p instanceof King) {
                        score += 200 * modifier;
                    } else if (p instanceof Queen) {
                        score += 9 * modifier;
                    } else if (p instanceof Rook) {
                        score += 5 * modifier;
                    } else if (p instanceof Bishop || p instanceof Knight) {
                        score += 3 * modifier;
                    } else if (p instanceof Pawn) {
                        score += 1 * modifier;
                    }

                }
            }
        }

    }

}
