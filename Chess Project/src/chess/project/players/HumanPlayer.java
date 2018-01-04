package chess.project.players;

import chess.project.Board;
import chess.project.Move;
import chess.project.Position;
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

            if (super.board.checkMove(move, colour)) {
                System.out.println("[VALID MOVE]");
                return move;
            }
        }

    }

    private Move readMove() {
        String move = "";
        while (move.equals("")) {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your move " + COLOUR + " (a-h)(1-8)(a-h)(1-8): ");
            try {
                move = sc.next();
                
                if(!move.matches("[a-hA-h][1-8][a-hA-H][1-8]")){
                    System.out.println("[Invalid choice]");
                    move = "";
                }
                
            } catch (InputMismatchException e) {
                System.out.println("[Invalid choice]");
                move = "";
            }
        }
        
        String[] split = move.split("");
        
        Position from = new Position(Character.toLowerCase(split[0].charAt(0)) - 97, 8 - Integer.parseInt(split[1]));
        Position to = new Position(Character.toLowerCase(split[2].charAt(0)) - 97, 8 - Integer.parseInt(split[3]));

        return new Move(from, to);
    }
}

