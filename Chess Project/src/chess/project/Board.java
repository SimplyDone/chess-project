package chess.project;

import chess.project.pieces.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;

import java.util.List;
import java.util.Scanner;

/**
 * Represents a chessboard.
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public final class Board implements Serializable {

    private final Piece board[][];
    private Piece tempBoard[][];

    private static final long serialVersionUID = 430L;

    private boolean whiteTurn;
    private int turnNumber;

    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;
    private boolean checkmate = false;

    public Board() {

        board = new Piece[8][8];
        whiteTurn = true;
        turnNumber = 0;
        initializeBoard();

    }

    /**
     * Called when a new board instance is created.
     *
     */
    public void initializeBoard() {

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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (board[i][j] != null) {
                    board[i][j].updateValidMoves(this);
                }
            }
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

            List<Move> possibleMoves = p.getValidMoves();

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

    public void doMove(Move move, boolean isHuman) {

        Position oldPos = move.getOldPosition();
        Position newPos = move.getNewPosition();

        Piece p = board[oldPos.getX()][oldPos.getY()];
        if (p != null) {

            if (p instanceof Pawn) {

                //en-passant condition 1
                if (Math.abs(oldPos.getY() - newPos.getY()) == 2) {
                    ((Pawn) p).setEnPassant(turnNumber);
                }

                //en-passant condition 2
                if (oldPos.getX() != newPos.getX()
                        && board[newPos.getX()][newPos.getY()] == null) {
                    board[newPos.getX()][oldPos.getY()] = null;
                }

                //promotion condition
                if (newPos.getY() == 0 || newPos.getY() == 7) {

                    if (isHuman) {
                        p = getHumanSelection(p);
                    } else {
                        p = getHumanSelection(p);
                    }
                }
            } else if (p instanceof Rook) {

            } else if (p instanceof King) {

                //castling condition
                if (newPos.getX() - oldPos.getX() == 2) {
                    Rook r = (Rook) board[7][oldPos.getY()];
                    r.move(new Position(5, oldPos.getY()));
                } else if (newPos.getX() - oldPos.getX() == -2) {
                    Rook r = (Rook) board[7][oldPos.getY()];
                    r.move(new Position(3, oldPos.getY()));
                }

            }

            completeMove(p, oldPos, newPos);

        }
    }

    private void completeMove(Piece p, Position oldPos, Position newPos) {
        p.move(newPos);
        board[oldPos.getX()][oldPos.getY()] = null;
        board[newPos.getX()][newPos.getY()] = p;
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
        turnNumber++;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (board[i][j] != null) {
                    board[i][j].updateValidMoves(this);
                }
            }
        }
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

    public int getTurnNumber() {
        return turnNumber;
    }

    private Piece getHumanSelection(Piece p) {

        String selection = TextInput.getStringChoice(
                "What piece would you like (R,k,B,Q): ",
                "[[Rr][kK][Bb][Qq]]");

        Piece n;

        switch (selection.toLowerCase()) {
            case "r":
                n = new Rook(p.getColour(), p.getPosition());
                break;
            case "k":
                n = new Knight(p.getColour(), p.getPosition());
                break;
            case "b":
                n = new Bishop(p.getColour(), p.getPosition());
                break;
            case "q":
                n = new Queen(p.getColour(), p.getPosition());
                break;
            default:
                throw new IllegalStateException(selection + " was a valid choice for a piece");
        }
        return n;

    }

    private void doTempMove(Move m) {

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(board);
            oos.flush();
            oos.close();
            bos.close();
            byte[] byteData = bos.toByteArray();

            ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
            tempBoard = (Piece[][]) new ObjectInputStream(bais).readObject();
        } catch (IOException | ClassNotFoundException e) {
            return;
        }

        Position oldPos = m.getOldPosition();
        Position newPos = m.getNewPosition();
        Piece p = tempBoard[oldPos.getX()][oldPos.getY()];

        p.move(newPos);
        tempBoard[oldPos.getX()][oldPos.getY()] = null;
        tempBoard[newPos.getX()][newPos.getY()] = p;

    }
}
