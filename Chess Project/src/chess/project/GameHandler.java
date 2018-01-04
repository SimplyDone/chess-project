package chess.project;

import chess.project.graphics.ChessboardGraphicHandler;
import chess.project.players.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Handles the main operations of the game. Handles player moves, ai moves,
 * running
 *
 * @author Alex Zurad
 */
public class GameHandler {

    private Player white;
    private Player black;

    private Board board;
    private boolean whiteMove;

    public GameHandler() {

        initializeGame();
        playGame();

    }

    /**
     * Handles initial setup, number of players, AI difficulty
     *
     */
    private void initializeGame() {

        if (getLoadBoard()) {
            board = loadBoardFromFile();
        } else {
            board = new Board();
        }

        int numHumans = getNumPlayers();
        int aiDifficulty = getAIDifficulty();

        white = new HumanPlayer(true, board);
        black = new HumanPlayer(false, board);

    }

    private void playGame() {

        ChessboardGraphicHandler cgh = new ChessboardGraphicHandler(board);

        while (!board.isCheckmate()) {

            board.printBoard();

            if (board.getTurn()) {
                board.doMove(white.getMove());
            } else {
                board.doMove(black.getMove());
            }

            cgh.updateBoard();
            board.nextTurn();
            board.saveBoard();
        }
    }

    // can combine the two methods
    private int getNumPlayers() {

        int numHumans = -1;

        while (numHumans == -1) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the number of human players (0-2): ");
            try {
                numHumans = Integer.parseInt(sc.next("[0-2]"));

            } catch (InputMismatchException e) {
                System.out.println("[Invalid choice]");
                numHumans = -1;
            }
        }

        return numHumans;
    }

    private int getAIDifficulty() {

        int aiDifficulty = -1;

        while (aiDifficulty == -1) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the difficulty for the AI (1-5): ");
            try {
                aiDifficulty = Integer.parseInt(sc.next("[1-5]"));

            } catch (InputMismatchException e) {
                System.out.println("[Invalid choice]");
                aiDifficulty = -1;
            }
        }

        return aiDifficulty;
    }
    // end of potential combine       

    private boolean getLoadBoard() {

        int loadBoard = -1;

        while (loadBoard == -1) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Would you like to load a previous save? (1-yes 2-no): ");
            try {
                loadBoard = Integer.parseInt(sc.next("[1-2]"));

            } catch (InputMismatchException e) {
                System.out.println("[Invalid choice]");
                loadBoard = -1;
            }
        }

        return loadBoard == 1;
    }

    private Board loadBoardFromFile() {
        try {
            ObjectInputStream ois
                    = new ObjectInputStream(new FileInputStream("Chess.save"));
            return (Board) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No save file found starting new game.");
            return new Board();
        }
    }

}
