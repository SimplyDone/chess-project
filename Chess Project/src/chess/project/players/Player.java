package chess.project.players;

import chess.project.*;

/**
 * This class represents a player in chess. It can be AI controlled or human.
 *
 * @author Alex Zurad
 */
public abstract class Player {

    protected final boolean colour; //false-black true-white
    protected final String COLOUR;
    protected final Board board;

    public Player(boolean colour, Board board) {
        this.colour = colour;
        this.COLOUR = colour ? "white" : "black";
        this.board = board;
    }

    public abstract Move getMove();

}
