package chess.project;

import chess.project.graphics.ChessboardGraphicHandler;
import chess.project.players.*;
import java.io.*;

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

        boolean loadBoard;
        int numHumans;

        loadBoard = TextInput.getBoolChoice(
                "Would you like to load a previous save? (1-Yes 2-No): ");

        if (loadBoard) {
            board = loadBoardFromFile();
        } else {
            board = new Board();
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

            if (board.getTurn()) {
                board.doMove(white.getMove());
            } else {
                board.doMove(black.getMove());
            }

            try{
            Thread.sleep(500);
            } catch (Exception e){
                
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

}
