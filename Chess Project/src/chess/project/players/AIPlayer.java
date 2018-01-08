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

    public AIPlayer(ChessColour colour, Board board, int difficulty) {
        super(colour, board);
        this.difficulty = difficulty;
    }

    @Override
    public Move getMove() {

        System.out.println(colour);
        int[] v = miniMax(difficulty, board, true);
        Move m = new Move(new Position(v[1], v[2]), new Position(v[3], v[4]));
        System.out.println(m + " " + v[0]);
        return m;

//        System.out.println(colour);
//        System.out.println(miniMax(2, board, true));
//        
//        
//        Move bestMove = null;
//        double currentScore = -10000;
//        double bestScore = -10000;
//
//        for (Move m : allValidMoves) {
//
//            if (bestMove == null) {
//                bestMove = m;
//            } else {
//                testBoard.doMove(m);
//                currentScore = evaluateBoard(testBoard.getBoard());
//                if (bestScore < currentScore) {
//                    bestMove = m;
//                    bestScore = currentScore;
//
//                } else if (bestScore == currentScore && Math.random() < 0.5) {
//                    bestMove = m;
//                    bestScore = currentScore;
//                }
//            }
//            testBoard = board.clone();
//        }
//
//        System.out.println(bestMove + " " + bestScore);
//        return bestMove;
    }

    private int[] miniMax(int depth, Board b, boolean isMaxPlayer) {

        Board tempBoard;
        ChessColour c;
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

        if (validMoves[0].isEmpty() || depth == 0) {
            bestScore = evaluateBoard(b, validMoves);
        } else {
            if (isMaxPlayer) {
                for (Move m : validMoves[0]) {
                    tempBoard = b.clone();

                    tempBoard.doMove(m);
                    currScore = miniMax(depth - 1, tempBoard, !isMaxPlayer)[0];

                    if ((currScore > bestScore) || (currScore == bestScore && Math.random() < 0.05)) {
                        bestScore = currScore;
                        bestMove = m;
                    }

                }
            } else {
                for (Move m : validMoves[0]) {
                    tempBoard = b.clone();

                    tempBoard.doMove(m);
                    currScore = miniMax(depth - 1, tempBoard, !isMaxPlayer)[0];

                    if ((currScore < bestScore) || (currScore == bestScore && Math.random() < 0.05)) {
                        bestScore = currScore;
                        bestMove = m;
                    }

                }

            }

        }
        if (bestMove != null) {

            Position oldPos = bestMove.getOldPosition();
            Position newPos = bestMove.getNewPosition();

            return new int[]{bestScore, oldPos.getX(), oldPos.getY(), newPos.getX(), newPos.getY()};
        } else {
            return new int[]{bestScore, -1, -1, -1, -1};
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
        return new List[] { validMoves, enemyMoves };
    }

    public int evaluateBoard(Board b, List<Move>[] validMoves) {
        int score = 0;

        int modifier = 1;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Piece p = board.getBoard()[i][j];
                if (p != null) {

                    if (p.getColour() == colour) {
                        modifier = 1;
                    } else {
                        modifier = -1;
                    }

                    if (p instanceof King) {
                        score += 2000 * modifier;
                    } else if (p instanceof Queen) {
                        score += 90 * modifier;
                    } else if (p instanceof Rook) {
                        score += 50 * modifier;
                    } else if (p instanceof Bishop || p instanceof Knight) {
                        score += 30 * modifier;
                    } else if (p instanceof Pawn) {
                        score += 10 * modifier;

                        // score -= 0.5 * doubled, blocked, isolated
                    }

                }
            }
        }
        score += 1 * validMoves[0].size();
        score -= 1 * validMoves[1].size();
        return score;
    }

}
