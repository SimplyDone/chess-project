package chess.project;

import chess.project.graphics.ChessboardGraphicHandler;
import chess.project.players.*;
import java.io.*;
import java.util.Arrays;
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

    public GameHandler() {

        initializeGame();
        playGame();

    }

    /**
     * Handles initial setup, number of players, AI difficulty
     *
     */
    private void initializeGame() {

        int loadBoard;
        int numHumans;

        loadBoard = TextInput.getIntChoice(
                "Would you like to load the last save? (1-Yes 2-No 3-Load from test file): ",
                "[1-3]");

        if (loadBoard == 1) {
            board = loadBoardFromFile();
        } else if (loadBoard == 2) {
            board = new Board();
        } else {
            String filename = TextInput.getStringChoice(
                    "Enter the filename to load from: ", ".*");
            board = loadBoardFromTextFile(new File(filename));
        }

        numHumans = TextInput.getIntChoice(
                "Enter the number of human players (0-2): ",
                "[0-2]");

        switch (numHumans) {
            case 0:
                int aiDifficultyW = TextInput.getIntChoice(
                        "Enter the difficulty for the white AI (1-5): ",
                        "[1-5]");
                int aiDifficultyB = TextInput.getIntChoice(
                        "Enter the difficulty for the black AI (1-5): ",
                        "[1-5]");

                white = new AIPlayer(ChessColour.WHITE, board, aiDifficultyW);
                black = new AIPlayer(ChessColour.BLACK, board, aiDifficultyB);
                break;

            case 1:
                boolean colourChoice = TextInput.getBoolChoice(
                        "What colour would you like? (1-White 2-Black): ");
                int aiDifficulty = TextInput.getIntChoice(
                        "Enter the difficulty for the AI (1-5): ",
                        "[1-5]");

                if (colourChoice) {
                    white = new HumanPlayer(ChessColour.WHITE, board);
                    black = new AIPlayer(ChessColour.BLACK, board, aiDifficulty);
                } else {
                    white = new AIPlayer(ChessColour.WHITE, board, aiDifficulty);
                    black = new HumanPlayer(ChessColour.BLACK, board);
                }
                break;
            case 2:
                white = new HumanPlayer(ChessColour.WHITE, board);
                black = new HumanPlayer(ChessColour.BLACK, board);
                break;

        }

    }

    private void playGame() {

        ChessboardGraphicHandler cgh = new ChessboardGraphicHandler(board);

        while (!board.isCheckmate()) {

            board.printBoard();

            if (board.getTurn() == ChessColour.WHITE) {
                board.doMove(white.getMove());
            } else {
                board.doMove(black.getMove());
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

            cgh.updateBoard();
            board.nextTurn();
            board.saveBoard();
        }
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

    private Board loadBoardFromTextFile(File file) {

        Scanner sc;
        String[][] pieces = new String[8][8];
        String[] line;

        String value;

        try {
            sc = new Scanner(file);

            for (int i = 0; i < 8; i++) {
                value = sc.next();
                if (value.matches("[pPrRnNbBqQkKX]{8}?")) {

                    line = value.split("\\W");
                    System.out.println(Arrays.toString(line));
                    if (line.length == 8) {
                        pieces[i] = line;
                        
                    }
                } else {
                    System.out.println("Invalid file format. Starting new game.");
                    return new Board();
                }

            }

        } catch (IOException e) {
            System.out.println("No save file found starting new game.");
            return new Board();
        }

        return new Board(pieces);
    }

}
