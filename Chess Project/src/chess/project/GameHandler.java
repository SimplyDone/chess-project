package chess.project;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Handles the main operations of the game. Handles player moves, ai moves,
 * running
 *
 * @author Alex Zurad
 */
public class GameHandler {

    private Board board;
    private boolean whiteMove;

    public GameHandler() {

        whiteMove = true;
        board = new Board();
        board.displayBoard();

        initializeGame();

    }

    /**
     * Handles initial setup, number of players, AI difficulty
     *
     */
    private void initializeGame() {
        int numPlayers = getNumPlayers();
    }

    private int getNumPlayers() {
        Scanner sc = new Scanner(System.in);
        int numPlayers = -1;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter the number of players (0-2) : ");
            try {
                numPlayers = Integer.parseInt(sc.next("[0-2]"));
                
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice");
                numPlayers = -1;
            }
            
            if(numPlayers != -1){
                validInput = true;
            }

        }

        return numPlayers;

    }

}
