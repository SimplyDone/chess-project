package chess.project.players;

import chess.project.movement.Move;
import chess.project.*;

/**
 * This class represents a player in chess. It can be AI controlled or human.
 *
 * @author Alex Zurad
 */
public abstract class Player {

    protected final ChessColour colour; //false-black true-white

    protected final Board board;

    public Player(ChessColour colour, Board board) {
        this.colour = colour;
        this.board = board;
    }

    public abstract Move getMove();

}
