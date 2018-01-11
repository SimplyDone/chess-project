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
        int[] v = miniMax(difficulty, board.clone(), Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        Move m = new Move(new Position(v[1], v[2]), new Position(v[3], v[4]));
        System.out.println(m + " " + v[0]);
        return m;

    }

    private int[] miniMax(int depth, Board b, int alpha, int beta, boolean isMaxPlayer) {

        ChessColour c = b.getTurn(); //gets the current player's colour
        Move bestMove = new Move(new Position(-5, -5), new Position(-5, -5));

        int score;

        List<Move>[] validMoves = getValidMoves(b.getBoard(), c); //gets all valid moves for a colour
        List<Move> myMoves = validMoves[0];
        List<Move> theirMoves = validMoves[1];

        if (myMoves.isEmpty() || depth == 0) {
            
                score = evaluateBoard(b, myMoves, theirMoves);    
            return new int[]{score, -10, -10, -10, -10};
        } else {

            Collections.shuffle(myMoves); // does a random move when multiple moves have the same value
            Board tempBoard;

            for (Move m : myMoves) {
                tempBoard = b.clone();//reset board 

                tempBoard.doMove(m);
                tempBoard.nextTurn();
                
                if (isMaxPlayer) {
                    score = miniMax(depth - 1, tempBoard, alpha, beta, !isMaxPlayer)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestMove = m;
                    }
                } else { //not max player
                    score = miniMax(depth - 1, tempBoard, alpha, beta, !isMaxPlayer)[0];
                    if (score < beta) {
                        beta = score;
                        bestMove = m;
                    }
                }
                


                if (alpha >= beta) {
                    break;
                }
            }
        }

        Position o = bestMove.getOldPosition();
        Position n = bestMove.getNewPosition();

        return new int[]{isMaxPlayer ? alpha : beta, o.getX(), o.getY(), n.getX(), n.getY()};
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

    /** Evaluates the board relative to the current player
     * 
     */
    private int evaluateBoard(Board b, List<Move> myMoves, List<Move> theirMoves) {

        int score = 0;
        int modifier = 1;

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
                        
//                        if(c == ChessColour.WHITE){
//                            score += 1 * Math.abs(p.getPosition().getY() - 7) * modifier;
//                        } else {
//                            score += 1 * p.getPosition().getY() * modifier;
//                        }
                        
                        score += 100 * modifier;

                        // score -= 0.5 * doubled, blocked, isolated
                    }

                }
            }
        }

        score += 1 * myMoves.size();
        score -= 1 * theirMoves.size();
        
        if(myMoves.isEmpty()){
            score += 10000 * modifier;
        }

        if (b.isChecked(ChessColour.opposite(colour)) ) {
            score += 30 * modifier;
        }
        
        
        return score;
    }

}
