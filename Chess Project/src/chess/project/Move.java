package chess.project;

/** This class represents a move between two positions.
 *
 * @author Alex Zurad
 */
public class Move {
    
    private Position oldPos;
    private Position newPos;
    
    public Move(Position oldPos, Position newPos){
        this.oldPos = oldPos;
        this.newPos = newPos;
    }

    public Position getOldPosition() {
        return oldPos;
    }

    public Position getNewPosition() {
        return newPos;
    }
 
}
