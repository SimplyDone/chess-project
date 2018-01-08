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

    private final int difficulty;

    public AIPlayer(ChessColour colour, Board board, int difficulty) {
        super(colour, board);
        this.difficulty = difficulty;
    }

    @Override
    public Move getMove() {

        System.out.println(colour);
        int[] v = miniMax(difficulty, board, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        Move m = new Move(new Position(v[1], v[2]), new Position(v[3], v[4]));
        System.out.println(m + " " + v[0]);
        return m;

    }

    private int[] miniMax(int depth, Board b, boolean isMaxPlayer, int alpha, int beta) {

        Board tempBoard;
        ChessColour c;
        
        //might need to be changed
        if (isMaxPlayer) {
            c = colour;
        } else {
            if (colour == ChessColour.WHITE) {
                c = ChessColour.BLACK;
            } else {
                c = ChessColour.WHITE;
            }
        }

        List<Move>[] validMoves = getValidMoves(b.getBoard(), c); //generate move list here

        int bestScore = isMaxPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currScore;
        Move bestMove = null;

        //exit
        if (validMoves[0].isEmpty() || depth == 0) {
            return new int[]{bestScore, -1, -1, -1, -1};
        }
        
        if (isMaxPlayer) {
            for (Move m : validMoves[0]) {
                tempBoard = b.clone();

                tempBoard.doMove(m);
                currScore = miniMax(depth - 1, tempBoard, !isMaxPlayer, alpha, beta)[0];

                if ((currScore > bestScore) || (currScore == bestScore && Math.random() < 0.05)) {
                    bestScore = currScore;
                    bestMove = m;
                }
                
                alpha = Math.max(alpha, bestScore);
                
                if(beta <= alpha){
                    break;
                }

            }
        } else {
            for (Move m : validMoves[0]) {
                tempBoard = b.clone();

                tempBoard.doMove(m);
                currScore = miniMax(depth - 1, tempBoard, !isMaxPlayer, alpha, beta)[0];

                if ((currScore < bestScore) || (currScore == bestScore && Math.random() < 0.05)) {
                    bestScore = currScore;
                    bestMove = m;
                }
                
                beta = Math.min(beta, bestScore);
                
                if(beta <= alpha){
                    break;
                }

            }

        }
        if (bestMove != null) {

            Position oldPos = bestMove.getOldPosition();
            Position newPos = bestMove.getNewPosition();

            return new int[]{bestScore, oldPos.getX(), oldPos.getY(), newPos.getX(), newPos.getY()};
        } else {
            return new int[]{bestScore, 0, 0, 0, 0};
        }
    }

    private List<Move>[] getValidMoves(Piece[][] tempBoard, ChessColour c) {

        List<Move> validMoves = new LinkedList<>();
        List<Move> enemyMoves = new LinkedList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tempBoard[i][j] != null) {
                    if (tempBoard[i][j].getColour() == c) {
                        validMoves.addAll(tempBoard[i][j].getValidMoves());
                    } else {
                        enemyMoves.addAll(tempBoard[i][j].getValidMoves());
                    }
                }
            }
        }
        return new List[]{validMoves, enemyMoves};
    }

    public int evaluateBoard(Board b, List<Move>[] validMoves) {

        int score = 0;
        int modifier;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Piece p = b.getBoard()[i][j];
                if (p != null) {

                    if (p.getColour() == colour) {
                        modifier = 1;
                    } else {
                        modifier = -1;
                    }

                    if (p instanceof King) {
                        score += 20000 * modifier;
                    } else if (p instanceof Queen) {
                        score += 900 * modifier;
                    } else if (p instanceof Rook) {
                        score += 500 * modifier;
                    } else if (p instanceof Bishop || p instanceof Knight) {
                        score += 300 * modifier;
                    } else if (p instanceof Pawn) {
                        score += 100 * modifier;

                        // score -= 0.5 * doubled, blocked, isolated
                    }

                }
            }
        }

        score += 1 * validMoves[0].size();
        score -= 1 * validMoves[1].size();

//        if (validMoves[0].isEmpty()) {
//            score = Integer.MIN_VALUE;
//        }
//
//        if (validMoves[1].isEmpty()) {
//            score = Integer.MAX_VALUE;
//        }

        return score;
    }

}
