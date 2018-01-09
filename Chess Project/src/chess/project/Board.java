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
import java.util.Stack;

/**
 * Represents a chessboard
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public final class Board implements Serializable {

    private Stack<Boolean> undoFlagStack;
    private Stack<Piece> undoPieceStack;
    private Stack<Move> undoMoveStack;

    private final Piece board[][];
    private King whiteKing;
    private King blackKing;

    private static final long serialVersionUID = 430L;

    private boolean isWhiteHuman;
    private boolean isBlackHuman;

    private boolean isWhiteTurn;
    private int turnNumber;

    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;

    private int whiteMoveCount = 0;
    private int blackMoveCount = 0;

    /**
     * creates a standard 8x8 array that contains pieces and nulls to represent
     * the chess board this also keeps track of the turn number and which
     * players turn it is
     */
    public Board() {

        board = new Piece[8][8];
        initializeSettings();
        initializePieces();
        updateValidMoves();
    }

    public Board(String[][] pieces) {
        board = new Piece[8][8];

        int wKingCount = 0, bKingCount = 0;

        // read the pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[j][i] = getPiece(pieces[i][j], new Position(j, i));
                if (board[j][i] instanceof King) {
                    if (board[j][i].getColour() == ChessColour.WHITE) {
                        whiteKing = (King) board[j][i];
                        wKingCount++;
                    } else {
                        blackKing = (King) board[j][i];
                        bKingCount++;
                    }
                }
            }
        }

        if (bKingCount != 1 || wKingCount != 1) {
            System.out.println("Invalid number of kings. Default settings used.");
            initializePieces();
        }

        initializeSettings();
        updateValidMoves();
    }

    private Piece getPiece(String p, Position pos) {

        ChessColour c = ChessColour.BLACK;

        switch (p) {
            case "r":
                c = ChessColour.WHITE;
            case "R":
                return new Rook(c, pos);
            case "p":
                c = ChessColour.WHITE;
            case "P":
                return new Pawn(c, pos);
            case "b":
                c = ChessColour.WHITE;
            case "B":
                return new Bishop(c, pos);
            case "n":
                c = ChessColour.WHITE;
            case "N":
                return new Knight(c, pos);
            case "k":
                c = ChessColour.WHITE;
            case "K":
                return new King(c, pos);
            case "q":
                c = ChessColour.WHITE;
            case "Q":
                return new Queen(c, pos);
            default:
                return null;

        }
    }

    /**
     * Called when a new board instance is created places pieces in a standard
     * chess layout
     */
    private void initializePieces() {

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

    }

    private void initializeSettings() {

        isWhiteTurn = true;
        turnNumber = 0;
        whiteMoveCount = 0;
        blackMoveCount = 0;

        //undo stacks
        undoFlagStack = new Stack();
        undoPieceStack = new Stack();
        undoMoveStack = new Stack();

    }

    /**
     * prints the current board state
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
                    System.out.print("0|");
                }
            }
            System.out.println();
        }
    }

    /**
     * Checks if checkmate is true
     *
     * @return
     */
    public boolean[] isGameOver() {

        // boolean output stucture
        // { isGameOver, isStalemate, isWhiteWinner }
        if (isWhiteTurn) {
            if (isWhiteChecked && whiteMoveCount <= 0) {
                return new boolean[]{true, false, false};

            } else if (whiteMoveCount <= 0) {
                return new boolean[]{true, true, false};
            }
        } else {
            if (isBlackChecked && blackMoveCount <= 0) {
                return new boolean[]{true, false, true};
            } else if (blackMoveCount <= 0) {
                return new boolean[]{true, true, true};
            }
        }
        return new boolean[]{false, false, false};
    }

    public boolean isChecked(ChessColour c) {
        return c == ChessColour.WHITE ? whiteKing.isChecked(this) : blackKing.isChecked(this);
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
            return "Invalid selection " + pos;

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

        //System.out.println("testing for " + getTurn() + " " + move);
        undoMoveStack.add(move);
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
                ((Rook) p).setCastle(false);
                undoFlagStack.add(true);

            } else if (p instanceof King) {
                ((King) p).setCastle(false);
                undoFlagStack.add(true);

                //castling condition
                if (newPos.getX() - oldPos.getX() == 2) { // right side
                    Rook r = (Rook) board[7][oldPos.getY()];
                    //System.out.println(r);

                    undoPieceStack.add(null);
                    completeMove(r, r.getPosition(), new Position(5, oldPos.getY()));
                } else if (newPos.getX() - oldPos.getX() == -2) { // left side
                    Rook r = (Rook) board[0][oldPos.getY()];

//                    System.out.println();
//                    for (int i = 0; i < 8; i++) {
//                        for (int j = 0; j < 8; j++) {
//                            System.out.print(board[j][i] + " ");
//                        }
//                        System.out.println();
//                    }
//
                    undoPieceStack.add(null);
                    completeMove(r, r.getPosition(), new Position(3, oldPos.getY()));
                }
            }

            undoPieceStack.add(board[newPos.getX()][newPos.getY()]);
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
        whiteMoveCount = 0;
        blackMoveCount = 0;
        updateValidMoves();
    }

    private void updateValidMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (board[i][j] != null) {
                    board[i][j].updateValidMoves(this);
                    if (board[i][j].getColour() == ChessColour.WHITE) {
                        isWhiteChecked = isChecked(ChessColour.WHITE);
                        whiteMoveCount += board[i][j].getValidMoves().size();
                    } else {
                        isBlackChecked = isChecked(ChessColour.BLACK);
                        blackMoveCount += board[i][j].getValidMoves().size();
                    }

                }
            }
        }

    }

    /**
     * Returns the Colour of who's turn it is
     *
     * @return current player's turn
     */
    public ChessColour getTurn() {
        return isWhiteTurn ? ChessColour.WHITE : ChessColour.BLACK;
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

    public void undo() {

        printBoard();

        Move lastMove = undoMoveStack.pop();
        Piece lastTaken = undoPieceStack.pop();
        boolean lastFlag = undoFlagStack.pop();

        Position to = lastMove.getNewPosition();
        Position from = lastMove.getOldPosition();

        Piece lastMoved = board[to.getX()][to.getY()];

        if (lastMoved instanceof King) {

            if (lastFlag) {
                ((King) lastMoved).setCastle(true);
            }

            if (from.getX() - to.getX() == 2) { // right side

                Rook r = (Rook) board[5][from.getY()];
                completeMove(r, r.getPosition(), new Position(7, from.getY()));
            } else if (from.getX() - to.getX() == -2) { // left side

                Rook r = (Rook) board[2][from.getY()];
                completeMove(r, r.getPosition(), new Position(0, from.getY()));
            }

        } else if (lastMoved instanceof Rook) {
            if (lastFlag) {
                ((Rook) lastMoved).setCastle(true);
            }
        } else if (lastMoved instanceof Pawn) {

        }

        completeMove(lastMoved, to, from);
        board[to.getX()][to.getY()] = lastTaken;

        printBoard();

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

    public boolean checkForCheck(Move m, ChessColour colour) {

        Board tempBoard = this.clone();
        tempBoard.doMove(m /*ai*/);

        return colour == ChessColour.WHITE
                ? tempBoard.whiteKing.isChecked(tempBoard) : tempBoard.blackKing.isChecked(tempBoard);

    }
}
