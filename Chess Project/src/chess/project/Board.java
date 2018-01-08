package chess.project;

import chess.project.movement.*;
import chess.project.pieces.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.List;

/**
 * Represents a chessboard
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public final class Board implements Serializable {

    private final Piece board[][];
    private King whiteKing;
    private King blackKing;

    private static final long serialVersionUID = 430L;

    private boolean isWhiteTurn;
    private int turnNumber;

    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;
    private boolean checkmate = false;

    /**creates a standard 8x8 array that contains pieces and nulls to represent
     * the chess board
     * this also keeps track of the turn number and which players turn it is
     */
    public Board() {

        board = new Piece[8][8];
        isWhiteTurn = true;
        turnNumber = 0;
        initializeBoard();

    }
    
    /**
     * Called when a new board instance is created
     * places pieces in a standard chess layout
     */
    public void initializeBoard() {

        //black
        board[0][0] = new Rook(ChessColour.BLACK, new Position(0, 0));
        board[7][0] = new Rook(ChessColour.BLACK, new Position(7, 0));

        board[1][0] = new Knight(ChessColour.BLACK, new Position(1, 0));
        board[6][0] = new Knight(ChessColour.BLACK, new Position(6, 0));

        board[2][0] = new Bishop(ChessColour.BLACK, new Position(2, 0));
        board[5][0] = new Bishop(ChessColour.BLACK, new Position(5, 0));

        board[3][0] = new Queen(ChessColour.BLACK, new Position(3, 0));
        board[4][0] = new King(ChessColour.BLACK, new Position(4, 0));
        blackKing = (King) board[4][0];

        for (int i = 0; i < 8; i++) {
            board[i][1] = new Pawn(ChessColour.BLACK, new Position(i, 1));
        }

        //white
        board[0][7] = new Rook(ChessColour.WHITE, new Position(0, 7));
        board[7][7] = new Rook(ChessColour.WHITE, new Position(7, 7));

        board[1][7] = new Knight(ChessColour.WHITE, new Position(1, 7));
        board[6][7] = new Knight(ChessColour.WHITE, new Position(6, 7));

        board[2][7] = new Bishop(ChessColour.WHITE, new Position(2, 7));
        board[5][7] = new Bishop(ChessColour.WHITE, new Position(5, 7));

        board[3][7] = new Queen(ChessColour.WHITE, new Position(3, 7));
        board[4][7] = new King(ChessColour.WHITE, new Position(4, 7));
        whiteKing = (King) board[4][7];

        for (int i = 0; i < 8; i++) {
            board[i][6] = new Pawn(ChessColour.WHITE, new Position(i, 6));
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (board[i][j] != null) {
                    board[i][j].updateValidMoves(this);
                }
            }
        }

    }

    /**prints the current board state
     * 
     */
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

    /**Checks if checkmate is true
     * 
     * @return 
     */
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

    public String checkPosition(Position pos) {
        Piece p = board[pos.getX()][pos.getY()];
        String msg = "";

        if (p != null) {

            List<Move> possibleMoves = p.getValidMoves();

            msg += "Valid moves for " + p.getClass().getSimpleName() + ":\n";

            int format = 0;
            for (Move m : possibleMoves) {
                if (format % 2 == 0) {
                    msg += "\n";
                }
                msg += m + "    ";
                format++;
            }

        } else {
            return "Invalid selection";

        }
        return msg;
    }

    public boolean checkMove(Move move, ChessColour colour) {

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
                    System.out.println(m);
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

            if (p instanceof Pawn) {

                //en-passant condition - pawn moved two spaces
                if (Math.abs(oldPos.getY() - newPos.getY()) == 2) {
                    ((Pawn) p).setEnPassant(turnNumber);
                }

                //en-passant condition - pawn moved diagonally over a pawn
                if (oldPos.getX() != newPos.getX()
                        && board[newPos.getX()][newPos.getY()] == null) {
                    board[newPos.getX()][oldPos.getY()] = null;
                }

                //TODO fix promotion bug
                //promotion condition for human player
                if (newPos.getY() == 0 || newPos.getY() == 7) {
                    p = getHumanSelection(p);

                    //promotion conditions for ai 
                } else if (newPos.getY() == -1 || newPos.getY() == 8) {

                }

            } else if (p instanceof Rook) {
                ((Rook) p).flagCastle();

            } else if (p instanceof King) {
                ((King) p).flagCastle();

                //castling condition
                if (newPos.getX() - oldPos.getX() == 2) { // right side
                    Piece r = board[7][oldPos.getY()];
                    completeMove(r, r.getPosition(), new Position(5, oldPos.getY()));
                } else if (newPos.getX() - oldPos.getX() == -2) { // left side
                    Piece r = board[0][oldPos.getY()];
                    completeMove(r, r.getPosition(), new Position(3, oldPos.getY()));
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
     * Passes the game to the next player, updates the turn number, and
     * recalculates the valid moves for all pieces.
     */
    public void nextTurn() {
        isWhiteTurn ^= true;
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
        return isWhiteTurn;
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

//        String selection = TextInput.getStringChoice(
//                "What piece would you like (R,k,B,Q): ",
//                "[[Rr][kK][Bb][Qq]]");
//
//        Piece n;
//
//        switch (selection.toLowerCase()) {
//            case "r":
//                n = new Rook(p.getColour(), p.getPosition());
//                break;
//            case "k":
//                n = new Knight(p.getColour(), p.getPosition());
//                break;
//            case "b":
//                n = new Bishop(p.getColour(), p.getPosition());
//                break;
//            case "q":
//                n = new Queen(p.getColour(), p.getPosition());
//                break;
//            default:
//                throw new IllegalStateException(selection + " was a valid choice for a piece");
//        }
//        return n;
        
        return new Queen(p.getColour(), p.getPosition());

    }

    @Override
    public Board clone() {

        Board tempBoard = null;

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            bos.close();

            byte[] byteData = bos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
            tempBoard = (Board) new ObjectInputStream(bais).readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.printf("CRITIAL ERROR");
            System.exit(1);
        }

        return tempBoard;
    }

    public boolean isChecked(Move m, ChessColour colour) {

        Board tempBoard = this.clone();
        tempBoard.doMove(m);

        return colour == ChessColour.WHITE
                ? tempBoard.whiteKing.isChecked(tempBoard) : tempBoard.blackKing.isChecked(tempBoard);
    }
}
