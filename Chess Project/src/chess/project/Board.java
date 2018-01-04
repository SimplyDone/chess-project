package chess.project;

import chess.project.pieces.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;


/**
 * Represents a chessboard.
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public final class Board implements Serializable {

    public final Piece board[][];
    private static final long serialVersionUID = 420L;

    private boolean whiteTurn;
    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;
    private boolean checkmate = false;

    public Board() {
        
        board = new Piece[8][8];
        initializeBoard();

        //TODO think of how we aare going to represent the various 
        //elements (pieces, board, possible moves, rules, the player,
        //the ai, ai logic, graphics, 
    }

    /**
     * Called when a new board instance is created.
     *
     */
    public void initializeBoard() {

        whiteTurn = true;

        //black
        board[0][0] = new Rook(false, new Position(0, 0));
        board[7][0] = new Rook(false, new Position(7, 0));

        board[1][0] = new Knight(false, new Position(1, 0));
        board[6][0] = new Knight(false, new Position(6, 0));

        board[2][0] = new Bishop(false, new Position(2, 0));
        board[5][0] = new Bishop(false, new Position(5, 0));

        board[3][0] = new Queen(false, new Position(3, 0));
        board[4][0] = new King(false, new Position(4, 0));

        for (int i = 0; i < 8; i++) {
            board[i][1] = new Pawn(false, new Position(i, 1));
        }

        //white
        board[0][7] = new Rook(true, new Position(0, 7));
        board[7][7] = new Rook(true, new Position(7, 7));

        board[1][7] = new Knight(true, new Position(1, 7));
        board[6][7] = new Knight(true, new Position(6, 7));

        board[2][7] = new Bishop(true, new Position(2, 7));
        board[5][7] = new Bishop(true, new Position(5, 7));

        board[3][7] = new Queen(true, new Position(3, 7));
        board[4][7] = new King(true, new Position(4, 7));

        for (int i = 0; i < 8; i++) {
            board[i][6] = new Pawn(true, new Position(i, 6));
        }

    }
    

    public void printBoard() {

        System.out.println();

        for (int i = 0; i < 8; i++) {
            System.out.print("|");
            for (int j = 0; j < 8; j++) {

                if (board[j][i] != null) {
                    System.out.print(board[j][i] + "|");
                } else {
                    System.out.print("X|");
                }
            }
            System.out.println();
        }
    }

    public boolean isCheckmate() {
        if (checkmate) {
            if (isWhiteChecked) {
                System.out.println("Black wins");
            } else if (isBlackChecked) {
                System.out.println("White wins");
            } else {
                throw new IllegalStateException("Checkmate is true when it should not be.");
            }

        }
        return checkmate;
    }

    public boolean checkMove(Move move, boolean colour) {

        Position oldPos = move.getOldPosition();

        Piece p = board[oldPos.getX()][oldPos.getY()];

        if (p != null && p.getColour() == colour) {

            List<Move> possibleMoves = p.getValidMoves(this);

            if (possibleMoves.contains(move)) {
                return true;
            } else {
                System.out.println("-----------------------------------------");
                System.out.println("[INVALID MOVE]");
                System.out.println("Valid moves for " + p.getClass().getSimpleName() + ":");
                possibleMoves.stream().forEach((m) -> {
                    System.out.println(m.getOldPosition() + " -> " + m.getNewPosition());
                });
                System.out.println("-----------------------------------------");
                return false;
            }

        } else {
            System.out.println("Invalid selection");
            return false;
        }
    }

    public void doMove(Move move) {

        Position oldPos = move.getOldPosition();
        Position newPos = move.getNewPosition();

        Piece p = board[oldPos.getX()][oldPos.getY()];
        if (p != null) {

            p.move(newPos);
            board[oldPos.getX()][oldPos.getY()] = null;
            board[newPos.getX()][newPos.getY()] = p;
        }
    }

    public Piece[][] getBoard() {
        return board;
    }

    /**
     * Sets the game to the next player
     *
     */
    public void nextTurn() {
        whiteTurn ^= true;
    }

    /**
     * Returns true if it is white's turn false if it is black's turn
     *
     * @return current player's turn
     */
    public boolean getTurn() {
        return whiteTurn;
    }

    public void saveBoard() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Chess.save"))) {
            oos.writeObject(this);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
}
