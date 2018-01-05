package chess.project.players;

import chess.project.Board;
import chess.project.Move;
import chess.project.Position;
import chess.project.TextInput;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Alex Zurad
 */
public class HumanPlayer extends Player {

    public HumanPlayer(boolean isWhite, Board board) {
        super(isWhite, board);
    }

    /**
     * 
     * @return 
     */
    @Override
    public Move getMove() {

        while (true) {
            Move move = readMove();

            if (board.checkMove(move, colour)) {
                System.out.println("[VALID MOVE]");
                return move;
            }
        }

    }

    private Move readMove() {
        
        String move = TextInput.getStringChoice(
                "Enter your move " + COLOUR + " (a-h)(1-8)(a-h)(1-8): ",
                "[a-hA-h][1-8][a-hA-H][1-8]");
        
        String[] split = move.split("");
        
        Position from = new Position(
                Character.toLowerCase(split[0].charAt(0)) - 97,
                8 - Integer.parseInt(split[1]));
        Position to = new Position(
                Character.toLowerCase(split[2].charAt(0)) - 97,
                8 - Integer.parseInt(split[3]));

        return new Move(from, to);
    }
}

