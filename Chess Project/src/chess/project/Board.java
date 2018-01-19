package chess.project;

import chess.project.movement.*;
import chess.project.pieces.*;
import java.io.*;
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

    private boolean isWhiteHuman;
    private boolean isBlackHuman;
    private boolean isWhiteTurn;
    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;

    private int turnNumber;
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
    }

    public void start() {
        updateValidMoves();

    }

    private Board(Piece[][] board, King[] kings, boolean[] gameFlags, int[] gameData) {
        this.board = board;

        this.whiteKing = kings[0];
        this.blackKing = kings[1];

        this.isWhiteHuman = gameFlags[0];
        this.isBlackHuman = gameFlags[1];
        this.isWhiteTurn = gameFlags[2];
        this.isWhiteChecked = gameFlags[3];
        this.isBlackChecked = gameFlags[4];

        this.turnNumber = gameData[0];
        this.whiteMoveCount = gameData[1];
        this.blackMoveCount = gameData[2];

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

    }

    public void setWhiteHuman(boolean isHuman) {
        isWhiteHuman = isHuman;
    }

    public void setBlackHuman(boolean isHuman) {
        isBlackHuman = isHuman;
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

    public void doMove(Move move, boolean forceQueen) {

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

                //promotion condition for human and ai
                switch (newPos.getY()) {

                    //ai queen promotion
                    case -1:
                        newPos = new Position(newPos.getX(), 0);
                        p = new Queen(
                                p.getColour(), p.getPosition());
                        break;
                    case 8:
                        newPos = new Position(newPos.getX(), 7);
                        p = new Queen(
                                p.getColour(), p.getPosition());
                        break;

                    //ai knight promotion
                    case -2:
                        newPos = new Position(newPos.getX(), 0);
                        p = new Knight(
                                p.getColour(), p.getPosition());
                        break;
                    case 9:
                        newPos = new Position(newPos.getX(), 7);
                        p = new Knight(
                                p.getColour(), p.getPosition());
                        break;

                    //human promotion
                    case 0:
                    case 7:
                        if (forceQueen) {
                            p = new Queen(p.getColour(), p.getPosition());
                        } else {
                            p = getHumanSelection(p);
                        }
                        break;
                }

            } else if (p instanceof Rook) {
                ((Rook) p).setCastle(false);

            } else if (p instanceof King) {
                ((King) p).setCastle(false);

                //castling condition
                if (newPos.getX() - oldPos.getX() == 2) { // right side
                    Rook r = (Rook) board[7][oldPos.getY()];

                    completeMove(r, r.getPosition(), new Position(5, oldPos.getY()));
                } else if (newPos.getX() - oldPos.getX() == -2) { // left side
                    Rook r = (Rook) board[0][oldPos.getY()];

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
        whiteMoveCount = 0;
        blackMoveCount = 0;

        if (isWhiteTurn) {
            isWhiteChecked = whiteKing.isChecked(this);
        } else {
            isBlackChecked = blackKing.isChecked(this);
        }

        updateValidMoves();

    }

    private void updateValidMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if (board[i][j] != null) {
                    if (board[i][j].getColour() == ChessColour.WHITE) {

                        board[i][j].updateValidMoves(this, isWhiteHuman);
                        whiteMoveCount += board[i][j].getValidMoves().size();
                    } else {
                        board[i][j].updateValidMoves(this, isBlackHuman);

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

    /**
     * This method is called after every turn to automatically save the board to
     * a file after every turn.
     *
     */
    public void saveBoard() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("Chess.save"))) {
            oos.writeObject(this);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * This method obtains input from a human player to determine what piece
     * they will obtain when promoting a pawn.
     *
     * @param p The piece to operate on
     * @return The new piece that n will become
     */
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

    public Board clone() {

        Piece[][] tempPieces = new Piece[8][8];
        King tempWhiteKing = null;
        King tempBlackKing = null;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    tempPieces[i][j] = (Piece) board[i][j].clone();

                    if (board[i][j] instanceof King) {
                        if (board[i][j].getColour() == ChessColour.WHITE) {
                            tempWhiteKing = (King) tempPieces[i][j];
                        } else {
                            tempBlackKing = (King) tempPieces[i][j];
                        }
                    }
                } else {
                    tempPieces[i][j] = null;
                }
            }
        }

        King[] kings = new King[]{tempWhiteKing, tempBlackKing};
        boolean[] gameFlags = new boolean[]{isWhiteHuman, isBlackHuman, isWhiteTurn, isWhiteChecked, isBlackChecked};
        int[] gameData = new int[]{turnNumber, whiteMoveCount, blackMoveCount};

        return new Board(tempPieces, kings, gameFlags, gameData);
    }

    public boolean checkForCheck(Move m, ChessColour colour) {

        Board tempBoard = this.clone();
        tempBoard.doMove(m, true);

        return colour == ChessColour.WHITE
                ? tempBoard.whiteKing.isChecked(tempBoard)
                : tempBoard.blackKing.isChecked(tempBoard);
    }

    public boolean isChecked(ChessColour c) {
        return c == ChessColour.WHITE ? isWhiteChecked : isBlackChecked;
    }

}
