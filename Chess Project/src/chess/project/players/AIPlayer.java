package chess.project.players;

import chess.project.*;
import chess.project.movement.*;
import chess.project.pieces.*;
import java.util.*;

/**
 *
 * @author Alex Zurad
 */
public class AIPlayer extends Player {

    private Board testBoard;
    private final int difficulty;
    private final List<Move> allValidMoves;
    private final List<Move> allEnemyMoves;

    public AIPlayer(ChessColour colour, Board board, int difficulty) {
        super(colour, board);
        this.difficulty = difficulty;
        this.allValidMoves = new LinkedList<>();
        this.allEnemyMoves = new LinkedList<>();
    }

    @Override
    public Move getMove() {

        allValidMoves.clear();
        allEnemyMoves.clear();

        System.out.println(colour);

        testBoard = board.clone();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (testBoard.getBoard()[i][j] != null) {
                    if (testBoard.getBoard()[i][j].getColour() == colour) {
                        allValidMoves.addAll(board.getBoard()[i][j].getValidMoves());
                    } else {
                        allEnemyMoves.addAll(board.getBoard()[i][j].getValidMoves());
                    }
                }
            }
        }

        Move bestMove = null;
        double currentScore = -10000;
        double bestScore = -10000;

        for (Move m : allValidMoves) {

            if (bestMove == null) {
                bestMove = m;
            } else {
                testBoard.doMove(m);
                currentScore = evaluateBoard(testBoard.getBoard());
                if (bestScore < currentScore) {
                    bestMove = m;
                    bestScore = currentScore;

                }
            }
            testBoard = board.clone();
        }

        System.out.println(bestMove + " " + bestScore);
        return bestMove;

    }
    
    private Move miniMax(int depth, Board b, boolean isMaxPlayer){
        
        if (depth ==  0){
            // do something
        }
        
        if(isMaxPlayer){
            
        }
        return null;
    }

    public double evaluateBoard(Piece[][] board) {
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

                        // score -= 0.5 * doubled, blocked, isolated
                    }

                }
            }
        }
        score += 0.1 * allValidMoves.size();
        score -= 0.1 * allEnemyMoves.size();
        return score;
    }

}
