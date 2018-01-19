package chess.project.players;

import chess.project.*;
import chess.project.movement.*;
import chess.project.pieces.*;
import java.util.*;

/**
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public class AIPlayer extends Player {

    private final int searchDepth;

    public AIPlayer(ChessColour colour, Board board, int difficulty) {
        super(colour, board);
        this.searchDepth = difficulty;
    }

    @Override
    public Move getMove() {

        System.out.println(colour);
        int[] v = alphaBeta(
                searchDepth,
                board,
                colour,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE);
        Move m = new Move(new Position(v[1], v[2]), new Position(v[3], v[4]));
        System.out.println(m + " " + v[0]);
        return m;

    }

    private List<Move>[] getValidMoves(Piece[][] tempBoard, ChessColour c) {

        List<Move> validMoves = new LinkedList<>();
        List<Move> badGuyMoves = new LinkedList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tempBoard[i][j] != null) {
                    if (tempBoard[i][j].getColour() == c) {
                        validMoves.addAll(tempBoard[i][j].getValidMoves());
                    } else {
                        badGuyMoves.addAll(tempBoard[i][j].getValidMoves());
                    }
                }
            }
        }
        return new List[]{validMoves, badGuyMoves};
    }

    /**
     * Evaluates the board relative to the current player
     *
     */
    private int evaluateBoard(Board b) {

        int score = 0;
        int modifier;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                Piece p = b.getBoard()[i][j];
                if (p != null) {

                    //if the piece is my colour the modifier is positive
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
                    }

                }
            }
        }

        
        if(b.isGameOver()[0]){
            if(!b.isGameOver()[1]){
                if(colour == ChessColour.WHITE && b.isGameOver()[2]){
                    score += 100000;
                } else if (colour == ChessColour.BLACK && !b.isGameOver()[2]){
                    score += 100000;
                } else {
                    score -= 100000;
                }
            } else {
                if(colour == ChessColour.WHITE && !b.isGameOver()[2]){
                    score -= 10000;
                } else if (colour == ChessColour.BLACK && b.isGameOver()[2]){
                    score -= 10000;
                } else {
                    score += 10000;
                }
            }
        }

        return score;
    }

    private int[] alphaBeta(int depth, Board b,
            ChessColour c, int alpha, int beta) {

        Move bestMove = new Move(new Position(-5, -5), new Position(-5, -5));
        //gets all valid moves for a colour
        List<Move>[] validMoves = getValidMoves(b.getBoard(), c); 
        List<Move> myMoves = validMoves[0];

        int currScore;

        if (myMoves.isEmpty() || depth == 0) {
            currScore = evaluateBoard(b);
            return new int[]{currScore, -10, -10, -10, -10};
        } else {

            // does a random move when multiple moves have the same value
            Collections.shuffle(myMoves); 
            Board tempBoard;

            for (Move m : myMoves) {
                tempBoard = b.clone();//reset board 

                tempBoard.doMove(m, true);
                tempBoard.nextTurn();

                if (c == colour) { // if the colour is mine (max player)
                    currScore = alphaBeta(depth - 1, tempBoard,
                            ChessColour.opposite(c), alpha, beta)[0];
                    if (currScore > alpha) {
                        alpha = currScore;
                        bestMove = m;
                    }
                } else { //colour is not mine (min player)
                    currScore = alphaBeta(depth - 1, tempBoard,
                            ChessColour.opposite(c), alpha, beta)[0];
                    if (currScore < beta) {
                        beta = currScore;
                        bestMove = m;
                    }
                }

                //the cycle is interrupted
                if (alpha >= beta) {
                    break;
                }
            }
        }

        Position o = bestMove.getOldPosition();
        Position n = bestMove.getNewPosition();

        return new int[]{(c == colour) ? alpha : beta,
            o.getX(), o.getY(), n.getX(), n.getY()};
    }

}
