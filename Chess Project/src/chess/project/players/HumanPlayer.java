package chess.project.players;

import chess.project.Board;
import chess.project.Move;
import chess.project.Position;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Alex Zurad
 */
public class HumanPlayer extends Player {

    public HumanPlayer(boolean isWhite, Board board) {
        super(isWhite, board);
    }

    @Override
    public Move getMove() {

        while (true) {
            Move move = readMove();

            if (super.board.checkMove(move)) {
                System.out.println("[VALID MOVE]");
                return move;
            }
        }

    }

    private Move readMove() {
        String move = "";
        while (move.equals("")) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your move " + COLOUR + " (0-7, 0-7, 0-7, 0-7): ");
            try {
                move = sc.next();
                
                if(!move.matches("[0-7][0-7][0-7][0-7]")){
                    System.out.println("[Invalid choice]");
                    move = "";
                }
                
            } catch (InputMismatchException e) {
                System.out.println("[Invalid choice]");
                move = "";
            }
        }
        
        String[] split = move.split("");
        
        Position from = new Position(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        Position to = new Position(Integer.parseInt(split[2]), Integer.parseInt(split[3]));

        return new Move(from, to);
    }
}

